package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.application.albaranes.form.AsignacionStockAssembler;
import es.zarca.covellog.domain.model.albaran.exception.AlbaranLineaNotExistException;
import es.zarca.covellog.domain.model.albaran.exception.AsignarDestinoEnEstadoNoBorradorException;
import es.zarca.covellog.domain.model.albaran.exception.AsignarOrigenEnEstadoNoBorradorException;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.exception.DomainExceptionHandler;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.movimientos.AsignacionStock;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.domain.model.stock.reservas.ReservaTipo;
import es.zarca.covellog.domain.model.transporte.Transporte;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "albaran")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Albaran.findAll", query = "SELECT a FROM Albaran a"),
    @NamedQuery(name = "Albaran.findById", query = "SELECT a FROM Albaran a WHERE a.id = :id")
})
public class Albaran implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @Column(name = "tipo_id")
    private AlbaranTipo tipo;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Empresa cliente;
    
    @Column(name = "cliente_friendly_id")
    private String clienteFriendlyId;
    
    @Column(name = "cliente_cif")
    private String clienteCif;
    
    @Column(name = "cliente_nombre")
    private String clienteNombre;
    
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    protected Contrato contrato;
    
    @Column(name = "contrato_friendly_id")
    private String contratoFriendlyId;
    
    @Column(name = "contrato_codigo_pedido")
    private String contratoCodigoPedido;
    
    @NotNull
    @Column(name = "estado_id")
    private AlbaranEstado estado;
    
    @Basic(optional = false)
    @Column(name = "estado_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFecha;
    
    @Column(name = "booking")
    private String booking;
    
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    @JoinColumn(name = "transporte_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Transporte transporte;
    
    @Embedded
    private InfoTransporte infoTransporte;
    
    @JoinColumn(name = "origen_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Ubicacion origen;
    
   /* @OneToOne(optional = false, mappedBy = "albaran", cascade = CascadeType.ALL, orphanRemoval = true)
    private UbicacionOrigen origenCopia;*/
    
    @JoinColumn(name = "destino_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Ubicacion destino;
    
    /*@OneToOne(optional = false, mappedBy = "albaran", cascade = CascadeType.ALL, orphanRemoval = true)
    private UbicacionDestino destinoCopia;*/
    
    @Column(name = "texto_aviso")
    private String textoAviso;
    
    @Embedded
    private DobleObservacion observaciones;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albaran", orphanRemoval = true)
    @OrderBy("orden ASC")
    final protected List<AlbaranLinea> lineas;
    
    public Albaran() {
        lineas = new ArrayList();
        cambiarEstado(AlbaranEstado.BORRADOR);
    }

    public Albaran(AlbaranTipo tipo) {
        this.tipo = tipo;
        lineas = new ArrayList();
        cambiarEstado(AlbaranEstado.BORRADOR);
    }

    public Integer getId() {
        return id;
    }

    public String getFriendlyId() {
        if (id == null) {
            return "ALBARAN EN CREACION";
        }
        return "AL" + StringUtil.padLeftZeros(getId().toString(), 6);
    }

    public AlbaranTipo getTipo() {
        return tipo;
    }

    public AlbaranEstado getEstado() {
        return estado;    
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public Empresa getCliente() {
        return cliente;
    }
    
    private boolean useCachedData() {
        return ((!getEstado().equals(AlbaranEstado.BORRADOR)) && (!getEstado().equals(AlbaranEstado.ANULADO)));
    }

    public String getClienteFriendlyId() {
        if (!useCachedData()) {
            return cliente.getFriendlyId();
        }
        return clienteFriendlyId;
    }

    public String getClienteCif() {
        if (!useCachedData()) {
            return cliente.getFriendlyId();
        }
        return clienteCif;
    }

    public String getClienteNombre() {
        if (!useCachedData()) {
            return cliente.getAliasNombre();
        }
        return clienteNombre;
    }
    
    public Contrato getContrato() {
        return contrato;
    }

    public String getContratoFriendlyId() {
        if (!useCachedData()) {
            contrato.getFriendlyId();
        }
        return contratoFriendlyId;
    }

    public String getContratoCodigoPedido() {
        if (!useCachedData()) {
            contrato.getCodigoPedidoCliente();
        }
        return contratoCodigoPedido;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public String getBooking() {
        return booking;
    }

    public InfoTransporte getInfoTransporte() {
        if (transporte == null) {
            return new InfoTransporte("Sus medios");
        }
        if (!useCachedData()) {          
            return new InfoTransporte(
                transporte.getProveedor().getNombre(),
                transporte.getNombre(),
                transporte.getCapacidad(),
                transporte.getObservaciones()
            );
        }
        return infoTransporte;
    }
    
    public Empresa getTransportista() {
        if (transporte == null) {
            return null;
        }
        return transporte.getProveedor();
    }

    public Ubicacion getOrigen() {
        return origen;
        /*if (!useCachedData()) {
            System.err.println("COJONES: Como es BORRADOR te doy origen");
            return origen;
        }
        System.err.println("COJONES: Como NOOOOO es BORRADOR te doy origenCopia");
        return origenCopia;*/
    }

    public Ubicacion getDestino() {
        return destino;
        /*if (!useCachedData()) {
            return destino;
        }
        return destinoCopia;*/
    }

    public String getTextoAviso() {
        return textoAviso;
    }

    public DobleObservacion getObservaciones() {
        return observaciones;
    }
    
    public void asignarCliente(Empresa cliente) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede cambiar el cliente de albaranes en estado BORRADOR.");
        this.cliente = cliente;
        clienteFriendlyId = cliente.getFriendlyId();
        clienteCif = cliente.getCif().toString();
        clienteNombre = cliente.getNombre();
    }
    
    public void asignarContrato(Contrato contrato) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede asignar contrato a albaranes en estado BORRADOR.");
        this.contrato = contrato;
        contratoFriendlyId = contrato.getFriendlyId();
        contratoCodigoPedido = contrato.getCodigoPedidoCliente();
        booking = contrato.getFriendlyId();
        asignarCliente(contrato.getCliente());
    }
    
    public void modificarBooking(String booking) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede cambiar el booking de albaranes en estado BORRADOR.");
        this.booking = booking;
    }
    
    public void modificarFecha(Date fecha) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede cambiar la fecha de albaranes en estado BORRADOR.");
        this.fecha = fecha;
    }
    
    public void modificarTextoAviso(String textoAviso) {
        this.textoAviso = textoAviso;
    }
    
    public void modificarObservaciones(DobleObservacion observaciones) {
        ArgumentValidator.isNotNull(observaciones, "Las observaciones es obligatorias.");
        this.observaciones = observaciones;
    }
    
    public void cambiarTransporte(Transporte transporte) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede cambiar el transporte de albaranes en estado BORRADOR.");
        this.transporte = transporte;
        if (transporte == null) {
            infoTransporte = new InfoTransporte("Sus Medios");
        } else {
            infoTransporte = new InfoTransporte(
                transporte.getProveedor().getNombre(),
                transporte.getNombre(),
                transporte.getCapacidad(),
                transporte.getObservaciones()
            );
        }
    }
    
    public void asignarOrigen(Ubicacion origen){
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede cambiar el origen de albaranes en estado BORRADOR.");
        if (!Objects.equals(this.origen, origen)) {
            ArgumentValidator.isNotNull(origen, "El origen es obligatorio.");
            if (!getEstado().equals(AlbaranEstado.BORRADOR)) {
                throw new AsignarOrigenEnEstadoNoBorradorException(this);
            }
            List<AlbaranLinea> eliminar = new ArrayList();
            for (AlbaranLinea linea : lineas) {
                if ((linea.getStock() != null) && (linea.getStock().getUbicacion() != null) && (!linea.getStock().getUbicacion().equals(this.origen))) {
                    eliminar.add(linea);
                }
            }
            for (AlbaranLinea item : eliminar) {
                if (contrato != null) {
                    contrato.onAlbaranEntregaLineaEliminada(item.getBooking());
                }
                lineas.remove(item);
            }
            this.origen = origen;
        }
    }
    
    /*private void copiarOrigen() {
        if (origenCopia == null) {
            origenCopia = new UbicacionOrigen(this);
        }
        origenCopia.copyFrom(origen);
    }
    
    private void eliminarCopiaOrigen() {
        origenCopia = null;
    }*/
    
    public void asignarDestino(Ubicacion destino) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede cambiar el destino de albaranes en estado BORRADOR.");
        ArgumentValidator.isNotNull(destino, "El destino es obligatorio.");
        if (!getEstado().equals(AlbaranEstado.BORRADOR)) {
            throw new AsignarDestinoEnEstadoNoBorradorException(this);
        }
        this.destino = destino;
    }
    
   /* private void copiarDestino() {
        if (destinoCopia == null) {
            destinoCopia = new UbicacionDestino();
            destinoCopia.setAlbaran(this);
        }
        destinoCopia.copyFrom(destino);
    }
    
    private void eliminarCopiaDestino() {
        destinoCopia = null;
    }*/
    
    //control estados
    private void cambiarEstado(AlbaranEstado estado) {
        //Si es BORRADOR y se cambia por otro fija las valores para que no se puedan modificar.
        if (AlbaranEstado.BORRADOR.equals(getEstado()) && (!AlbaranEstado.BORRADOR.equals(estado))) {
            clienteFriendlyId = getClienteFriendlyId();
            clienteCif = getClienteCif();
            clienteNombre = getClienteNombre();
            contratoFriendlyId = getContratoFriendlyId();
            contratoCodigoPedido = getContratoCodigoPedido();
        }
        this.estado = estado;
        estadoFecha = new Date();
    }
    
    public Boolean canAnular() {
        return (AlbaranEstado.BORRADOR.equals(getEstado()));
    }
    
    public void anular() {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo puede anular albaranes en estado BORRADOR");
        cambiarEstado(AlbaranEstado.ANULADO);
        for (AlbaranLinea linea : lineas) {
            linea.anularReserva();
        }
    }
    
    public Boolean canReabrir() {
        return (getEstado().equals(AlbaranEstado.ANULADO)) ||
                (getEstado().equals(AlbaranEstado.ACTIVO));
    }
    
    public void reabrir() {
        cambiarEstado(AlbaranEstado.BORRADOR);
      //  eliminarCopiaOrigen();
      //  eliminarCopiaDestino();
    }
    
    public Boolean canActivar() {
        return (AlbaranEstado.BORRADOR.equals(getEstado())) &&
                (origen != null) &&
                (destino != null) &&
                (!lineas.isEmpty());
    }
    
    public void activar() {
        ArgumentValidator.isNotNull(fecha, "Para activar un albaran tiene que asignarle una fecha.");
        ArgumentValidator.isNotNull(origen, "Para activar un albaran tiene que asignarle un origen.");
        ArgumentValidator.isNotNull(destino, "Para activar un albaran tiene que asignarle un destino.");
        cambiarEstado(AlbaranEstado.ACTIVO);
        //copiarOrigen();
        //copiarDestino();
    }
    
    public Boolean canModificar() {
        return (getEstado().equals(AlbaranEstado.BORRADOR));
    }
    
    public Boolean canFinalizar() {
        return false;
        //return (getEstado().equals(AlbaranEstado.ACTIVO));
    }
    
    public void finalizar(Date fecha) {
       /* ArgumentValidator.isTrue(getEstado().equals(AlbaranEstado.ACTIVO), "Solo puede finalizar albaranes lanzados.");
        for (AlbaranLinea linea : lineas) {
            if (linea.getFechaSalida() == null) {
                linea.marcarSalida(fecha);
            }
            if (linea.getFechaLlegada() == null) {
                linea.marcarLlegada(fecha);
            }
        }
        cambiarEstado(AlbaranEstado.FINALIZADO);*/
    }
    
    
    //Control global Salida/Llegada
    public void marcarSalida(Date fecha) {
        lineas.forEach(linea -> {
            linea.marcarSalida(fecha);
        });
    }
    
    public void desmarcarSalida() {
        lineas.forEach(linea -> {
            linea.desmarcarSalida();
        });
    }
    
    public void marcarLlegada(Date fecha) {
        lineas.forEach(linea -> {
            linea.marcarLlegada(fecha);
        });
    }
    
    public void desmarcarLlegada() {
        lineas.forEach(linea -> {
            linea.desmarcarLlegada();
        });
    }
    
    @XmlTransient
    public List<IAlbaranLinea> getLineas() {
        return Collections.unmodifiableList(lineas);
    }
    
    private int getIndexLinea(String booking) {
        int i = 0;
        for (AlbaranLinea linea : lineas) {
            if (linea.getBooking().equals(booking)) {
                return i;
            }
            i++;
        }
        throw new AlbaranLineaNotExistException(this, booking);
    }
    
    private AlbaranLinea getLineaImp(String booking) {
        for (AlbaranLinea linea : lineas) {
            if (linea.getBooking().equals(booking)) {
                return linea;
            }
        }
        for (AlbaranLinea linea : lineas) {
            if ((getBooking() + "-" + linea.getBooking()).equals(booking)) {
                return linea;
            }
        }
        ArgumentValidator.fail("No se encuenra la linea con booking \"" + booking + "\" en el albaran + \"" + getFriendlyId() + "\".");
        return null;
    }
    
    public IAlbaranLinea getLinea(String booking) {
        return getLineaImp(booking);
    }
    
    public IAlbaranLinea getLinea(Stock stock) {
        for (AlbaranLinea linea : lineas) {
            if (linea.getStock().equals(stock)) {
                return linea;
            }
        }
        return null;
    }
    
    public Boolean lineasContiene(String booking) {
        return lineas.stream().anyMatch(linea -> (linea.getBooking().equals(booking)));
    }
    
    public Boolean lineasContiene(Stock stock) {
        return lineas.stream().anyMatch(linea -> (linea.getStock().equals(stock)));
    }
    
    public void lineaAsignarProducto(String booking, Stock producto) {
        AlbaranLinea linea = getLineaImp(booking);
        linea.asignarStock(producto);
        
    }

 /*   public void lineaAsignarProducto(String booking, TipoProducto tipoProducto, Ubicacion ubicacion, String lote) {
        AlbaranLinea linea = getLineaImp(booking);
        linea.asignarStock(producto);
    }
    
    public void lineaAsignarProducto(String booking, TipoProducto tipoProducto, Ubicacion ubicacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    
    public void lineaAnadir(String booking, Stock stock, String descripcion, List<String> checkList) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede anadir lineas a albaranes en estado BORRADOR.");
        ArgumentValidator.isNotNull(stock, "No puede anadir una linea preasignada al albaran si no hay preasignacion.");
        ArgumentValidator.isFalse(Albaran.this.lineasContiene(booking), "Ya existe una linea con el booking \"" + booking + "\".");        
        ArgumentValidator.isTrue(origen.equals(stock.getUbicacion()), 
            "No puede anadir el producto \"" + stock.getNumeroSerie() + 
            "\" al albaran \"" + getFriendlyId() + 
            "\" porque el albaran es para el origen \"" + origen.getNombre() + 
            "\" y el producto esta en \"" + stock.getUbicacion().getNombre() + "\".");
        if (origen == null) {
            origen = stock.getUbicacion();
        }
        lineas.add(new AlbaranLinea(this, lineas.size() + 1, booking, stock, descripcion, checkList));
    }
    
    public void lineaAnadir(String booking, TipoProducto tipoProducto, String lote, String descripcion, List<String> checkList) {
        ArgumentValidator.isFalse(Albaran.this.lineasContiene(booking), "Ya existe una linea con el booking \"" + booking + "\".");
        lineas.add(new AlbaranLinea(this, lineas.size() + 1, booking, tipoProducto, lote, descripcion, checkList));
    }
    
    public void lineaModificar(String booking, Stock stock, String descripcion, List<String> checkList) {        
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            log.info("Asignacion directa");
            AlbaranLinea linea = getLineaImp(booking);
            ArgumentValidator.isNull(linea.getFechaSalida(), "No se puede modificar una linea de albaran que ya a sido entregada.");
            ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede modificar lineas de albaranes en estado BORRADOR.");
            linea.asignarStock(stock);
            linea.setDescripcion(descripcion);
            linea.setCheckList(checkList);
            if (linea.getReserva() != null) {
                linea.getReserva().lineaModificar(booking, stock, checkList);
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public void lineaModificar(String booking, TipoProducto tipoProducto, String lote, String descripcion, List<String> checkList) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            log.info("Asignacion indirecta");
            AlbaranLinea linea = getLineaImp(booking);
            ArgumentValidator.isNull(linea.getFechaSalida(), "No se puede modificar una linea de albaran que ya a sido entregada.");
            ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado), "Solo se puede modificar lineas de albaranes en estado BORRADOR.");
            log.info(linea.getStock() == null ? "STOCK NULO" : linea.getStock().getNumeroSerie());
            linea.asignarStock(null);
            log.info(linea.getStock() == null ? "STOCK NULO" : linea.getStock().getNumeroSerie());
            linea.setTipoProducto(tipoProducto);
            linea.setLote(lote);
            linea.setDescripcion(descripcion);
            linea.setCheckList(checkList);
            if (linea.getReserva() != null) {
                linea.getReserva().lineaModificar(booking, tipoProducto, lote, checkList);
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    private void lineaEliminar(int index) {
        ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(estado) || AlbaranEstado.ANULADO.equals(estado), "Solo se puede eliminar lineas de albaranes en estado BORRADOR. Estado Actual: " + estado.getNombre());
        if (lineas.get(index).getReserva() != null) {
            lineas.get(index).getReserva().cancelarReserva(lineas.get(index).getBooking());
        }
        
        for(int i = index + 1;i < lineas.size();i++) {
            AlbaranLinea linea = lineas.get(i);
            linea.setOrden(linea.getOrden() - 1);
        }        
        if (contrato != null) {
            if (AlbaranTipo.ENTREGA.equals(tipo)) {
                contrato.onAlbaranEntregaLineaEliminada(lineas.get(index).getBooking());
            } else {
                contrato.onAlbaranRecogidaLineaEliminada(lineas.get(index).getBooking());
            }
        }
        lineas.remove(index);
    }

    public void lineaEliminar(String booking) {    
        lineaEliminar(getIndexLinea(booking));
    }
    
    public void lineaBajar(String booking) {
        Integer pos = getIndexLinea(booking);
        if (pos < lineas.size() - 1) {
            AlbaranLinea pasiva = lineas.get(pos + 1);
            AlbaranLinea activa = lineas.get(pos);
            lineas.set(pos, pasiva);
            pasiva.setOrden(pos + 1);            
            lineas.set(pos + 1, activa);
            activa.setOrden(pos + 2);
        }
    }
    
    public void lineasBajar(List<String> bookings) {
        for(int i = lineas.size() - 1; i >= 0; i--) {
            String booking = lineas.get(i).getBooking();
            if (bookings.contains(booking)) {
                lineaBajar(booking);
            }
        }
    }
    
    public void lineaSubir(String booking) {
        Integer pos = getIndexLinea(booking);
        if (pos > 0) {
            AlbaranLinea pasiva = lineas.get(pos - 1);
            AlbaranLinea activa = lineas.get(pos);
            lineas.set(pos, pasiva);
            pasiva.setOrden(pos + 1);
            lineas.set(pos - 1, activa);
            activa.setOrden(pos);
        }
    }
    
    public void lineasSubir(List<String> bookings) {
        for(int i = 0; i < lineas.size(); i++) {
            String booking = lineas.get(i).getBooking();
            if (bookings.contains(booking)) {
                lineaSubir(booking);
            }
        }
    }
    
    public void lineaMarcarLlegada(String booking, Date fecha) {
        getLineaImp(booking).marcarLlegada(fecha);
    }
    
    public void lineaDesmarcarLlegada(String booking) {
        getLineaImp(booking).desmarcarLlegada();
    }
    
    public void lineaMarcarSalida(String booking, Date fecha) {
        getLineaImp(booking).marcarSalida(fecha);
    }
    
    public void lineaDesmarcarSalida(String booking) {
        getLineaImp(booking).desmarcarSalida();
    }
    
    public Boolean canCrearReserva() {
        return AlbaranEstado.BORRADOR.equals(estado) || AlbaranEstado.ACTIVO.equals(estado);
    }
    
    private Reserva reservar(
        Date fecha,
        List<AsignacionStock> asignacionesStock, 
        Empresa transportista, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        ArgumentValidator.isNotNull(getOrigen(), "Asigne primero un origen al albaran.");
        ArgumentValidator.isNotNull(getCliente(), "Asigne primero un cliente al albaran.");
        Reserva reserva = new Reserva(
            AlbaranTipo.ENTREGA.equals(getTipo()) ? ReservaTipo.SALIDA : ReservaTipo.ENTRADA,
            fecha == null ? getFecha() : fecha,
            AlbaranTipo.ENTREGA.equals(getTipo()) ? getOrigen() : getDestino(),
            getBooking(),
            getCliente(), 
            transportista,
            transportistaNombre,
            chofer, 
            matricula, 
            observaciones
        );
        reserva.setAlbaran(this);
        for (AsignacionStock asignacionStock : asignacionesStock) {
            AlbaranLinea linea = getLineaImp(asignacionStock.getBooking());
            if (asignacionStock.getStock() != null) {
               reserva.lineaAnadir(
                    asignacionStock.getBooking(),
                    asignacionStock.getStock(),
                    linea.getCheckList()
                ); 
            } else {
                reserva.lineaAnadir(
                    asignacionStock.getBooking(),
                    linea.getTipoProducto(),
                    linea.getLote(),
                    linea.getCheckList()
                );
            }
            
            linea.asignarReserva(reserva);
        }
        return reserva;
    }
    
    public Reserva crearEntreguese(
        Date fecha,
        List<AsignacionStock> asignacionesStock, 
        Empresa transportista, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        return reservar(
            fecha, 
            asignacionesStock, 
            transportista, 
            transportistaNombre, 
            chofer, 
            matricula, 
            observaciones
        );
    }
    
    public Reserva crearAdmitase(
        Date fecha,
        List<AsignacionStock> asignacionesStock, 
        Empresa transportista, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        return reservar(
            fecha, 
            asignacionesStock, 
            transportista, 
            transportistaNombre, 
            chofer, 
            matricula, 
            observaciones
        );
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Albaran)) {
            return false;
        }
        Albaran other = (Albaran) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.albaran.Albaran[ id=" + id + " ]";
    }

    private Boolean estaTodoEntregado() {
        for (AlbaranLinea linea : lineas) {
            if (linea.getFechaSalida() == null) {
                return false;
            } 
        }
        return true;
    }
    
    private void lineaEntregar(AlbaranLinea linea, Stock stockEntregado, Date fecha) {
        linea.asignarStock(stockEntregado);
        linea.entregar(fecha);  
        boolean notificarFinalizacion = false;
        if (estaTodoEntregado()) {
            cambiarEstado(AlbaranEstado.FINALIZADO);
            notificarFinalizacion = true;
        }
        if (tipo.equals(AlbaranTipo.ENTREGA)) {
            contrato.onMovimientoEntregaRealizado(linea.getBooking(), stockEntregado, fecha); 
        } else {
            contrato.onMovimientoRecogidaRealizado(linea.getBooking(), fecha); 
        }
        if (notificarFinalizacion) {
            contrato.onAlbaranFinalizado(this);
        }
        
    }

    public void onMovimientoRealizado(String booking, Stock stockEntregado, Date fecha) {
        AlbaranLinea linea = getLineaImp(booking);
        if (linea != null) {
            lineaEntregar(linea, stockEntregado, fecha);
        }
    }
    
    public void onMovimientoAnulado(String booking) {
        AlbaranLinea linea = getLineaImp(booking);
        if (linea != null) {
            linea.anularMovimiento();
            cambiarEstado(AlbaranEstado.ACTIVO);
        }
    }



    public void onReservaAnulada(String booking) {
        AlbaranLinea linea = getLineaImp(booking);
        if (linea != null) {
            linea.asignarReserva(null);
        }
    }

    

    public void onReservaFinalizada(Reserva reserva) {
        /*for (AlbaranLinea linea : lineas) {
            if (linea.getReserva() != null) {
                if (reserva.equals(linea.getReserva())) {
                    lineaEntregar(linea, reserva.getStockMovimiento(linea.getBooking()), reserva.getFecha());                                       
                }
            } 
        }*/
    }

    public Reserva generarMovimiento(
        Date fecha, 
        List<AsignacionStock> asignacionesStock, 
        Empresa transportista, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        Reserva reserva = reservar(
            fecha, 
            asignacionesStock, 
            transportista, 
            transportistaNombre, 
            chofer, 
            matricula, 
            observaciones
        );
        reserva.activar();
        reserva.generarMovimientos(fecha, asignacionesStock);
        return reserva;
    }
    
}
