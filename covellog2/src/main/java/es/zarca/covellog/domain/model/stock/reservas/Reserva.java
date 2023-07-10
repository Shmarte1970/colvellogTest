package es.zarca.covellog.domain.model.stock.reservas;

import es.zarca.covellog.application.exception.AppExceptionHandler;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.movimientos.AsignacionStock;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoLinea;
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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findById", query = "SELECT r FROM Reserva r WHERE r.id = :id"),
    @NamedQuery(name = "Reserva.findByFecha", query = "SELECT r FROM Reserva r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Reserva.findByTransportistaNombre", query = "SELECT r FROM Reserva r WHERE r.transportistaNombre = :transportistaNombre"),
    @NamedQuery(name = "Reserva.findByChofer", query = "SELECT r FROM Reserva r WHERE r.chofer = :chofer"),
    @NamedQuery(name = "Reserva.findByMatricula", query = "SELECT r FROM Reserva r WHERE r.matricula = :matricula")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_id")
    private ReservaTipo tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_id")
    private ReservaEstado estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFecha;
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "booking")
    private String booking;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa cliente;
    @JoinColumn(name = "transportista_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa transportista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "transportista_nombre")
    private String transportistaNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "chofer")
    private String chofer;
    @Size(max = 20)
    @Column(name = "matricula")
    private String matricula;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reserva", orphanRemoval = true)
    private List<ReservaLinea> lineas;
    @JoinColumn(name = "albaran_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Albaran albaran;
    
    public Reserva() {
    }

    public Reserva(
        ReservaTipo tipo, 
        Date fecha, 
        Ubicacion almacen, 
        String booking,
        Empresa cliente, 
        Empresa transportista, 
        String transportistaNombre, 
        String chofer, 
        String matricula, 
        String observaciones
    ) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.ubicacion = almacen;
        this.booking = booking;
        this.cliente = cliente;
        this.transportista = transportista;
        this.transportistaNombre = (transportista == null ? transportistaNombre : transportista.getNombre());
        this.chofer = chofer;
        this.matricula = matricula;
        this.observaciones = observaciones;
        this.estado = ReservaEstado.BORRADOR;
        this.estadoFecha = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendlyId() {
        if (id == null) {
            return "RESERVA EN CREACION";
        }
        return "RES" + StringUtil.padLeftZeros(getId().toString(), 6);
    }
    
    public ReservaTipo getTipo() {
        return tipo;
    }

    public void setTipo(ReservaTipo tipo) {
        this.tipo = tipo;
    }

    public ReservaEstado getEstado() {
        return estado;
    }

    private void setEstado(ReservaEstado estado) {
        if (!Objects.equals(estado, this.estado)) {
            this.estado = estado;
            estadoFecha = new Date();
        }
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }
    
    public Empresa getCliente() {
        return cliente;
    }

    public void setCliente(Empresa cliente) {
        this.cliente = cliente;
    }

    public Empresa getTransportista() {
        return transportista;
    }

    public void setTransportista(Empresa transportista) {
        this.transportista = transportista;
    }

    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void lineaAnadir(String booking, Stock stock, List<String> checkList) {
        ArgumentValidator.isNotNull(booking, "El booking de linea no puede ser null");
        if (lineas == null) {
            lineas = new ArrayList();
        }
        lineas.add(new ReservaLinea(this, booking, stock, checkList));
    }
    
    public void lineaAnadir(String booking, TipoProducto tipoProducto, String lote, List<String> checkList) {
        ArgumentValidator.isNotNull(tipoProducto, "El tipo de producto no puede ser null");
        ArgumentValidator.isNotNull(booking, "El booking de linea no puede ser null");
        if (lineas == null) {
            lineas = new ArrayList();
        }
        lineas.add(new ReservaLinea(this, booking, tipoProducto, lote, checkList));
    }
    
    public void lineaModificar(String booking, Stock stock, List<String> checkList) {
        ArgumentValidator.isNotNull(booking, "El booking de linea no puede ser null");
        if (lineas == null) {
            lineas = new ArrayList();
        }
        ReservaLinea linea = getLineaByBooking(booking);
        linea.modificar(stock, checkList);
    }
    
    public void lineaModificar(String booking, TipoProducto tipoProducto, String lote, List<String> checkList) {
        ArgumentValidator.isNotNull(tipoProducto, "El tipo de producto no puede ser null");
        ArgumentValidator.isNotNull(booking, "El booking de linea no puede ser null");
        if (lineas == null) {
            lineas = new ArrayList();
        }
        ReservaLinea linea = getLineaByBooking(booking);
        linea.modificar(tipoProducto, lote, checkList);
    }
    
    @XmlTransient
    public List<ReservaLineaRO> getLineas() {
        return Collections.unmodifiableList(lineas);
    }

    public Albaran getAlbaran() {
        return albaran;
    }

    public void setAlbaran(Albaran albaran) {
        this.albaran = albaran;
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
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.reservas.Reserva[ id=" + id + " ]";
    }

    public void generarMovimientos(Date fecha, List<AsignacionStock> asignaciones) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (ReservaTipo.ENTRADA.equals(tipo)) {
                if (transportista != null) {
                    ubicacion.entrada(fecha, booking, cliente, transportista, chofer, matricula, observaciones, asignaciones, this);
                } else {
                    ubicacion.entrada(fecha, booking, cliente, transportistaNombre, chofer, matricula, observaciones, asignaciones, this);
                }
            } else {
                if (transportista != null) {
                    ubicacion.salida(fecha, booking, cliente, transportista, chofer, matricula, observaciones, asignaciones, this);
                } else {
                    ubicacion.salida(fecha, booking, cliente, transportistaNombre, chofer, matricula, observaciones, asignaciones, this);
                }
            }
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void generarMovimientos(Date fecha, List<AsignacionStock> asignaciones, Empresa transportista, String transportistaNombre, String chofer, String matricula, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (ReservaTipo.ENTRADA.equals(tipo)) {
                if (transportista != null) {
                    ubicacion.entrada(fecha, booking, cliente, transportista, chofer, matricula, observaciones, asignaciones, this);
                } else {
                    ubicacion.entrada(fecha, booking, cliente, transportistaNombre, chofer, matricula, observaciones, asignaciones, this);
                }
            } else {
                if (transportista != null) {
                    ubicacion.salida(fecha, booking, cliente, transportista, chofer, matricula, observaciones, asignaciones, this);
                } else {
                    ubicacion.salida(fecha, booking, cliente, transportistaNombre, chofer, matricula, observaciones, asignaciones, this);
                }
            }
        } catch (Exception ex) {
            AppExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }       
    
    public boolean getCanActivar() {
        return (estado == ReservaEstado.BORRADOR);
    }

    public boolean getCanReabrir() {
        return ((estado == ReservaEstado.ACTIVA) || (estado == ReservaEstado.ANULADO));
    }

    public boolean getCanAnular() {
        return ((estado == ReservaEstado.BORRADOR) || (estado == ReservaEstado.ACTIVA));
    }

    public void anular() {
        ArgumentValidator.isTrue(getCanAnular(), "No se puede anular la reserva.");        
        setEstado(ReservaEstado.ANULADO);
        for (ReservaLinea linea : lineas) {
            albaran.onReservaAnulada(linea.getBooking());
        }
    }

    public void activar() {
        ArgumentValidator.isTrue(getCanActivar(), "No se puede activar la reserva.");
        setEstado(ReservaEstado.ACTIVA);
    }

    public void reabrir() {
        ArgumentValidator.isTrue(getCanReabrir(), "No se puede reabrir la reserva.");
        setEstado(ReservaEstado.BORRADOR);
    }

    public boolean getCanModificar() {
        return (estado == ReservaEstado.BORRADOR);
    }

    public ReservaLineaRO getLinea(String booking) {
        for (ReservaLinea linea : lineas) {
            System.err.println("COJONES: " + linea.getBooking() +" = " + booking);
            if (linea.getBooking().equals(booking)) {
                return linea;
            }
        }
        return null;
    }
    
    private ReservaLinea getLineaByBooking(String booking) {
        ArgumentValidator.isNotEmpty(booking, "Buscar una linea por booking requiere especificar el booking.");
        for (ReservaLinea linea : lineas) {
            System.err.println("getLineaByBooking: " + linea.getBooking() + " = " + booking);
            if (booking.equals(linea.getBooking())) {
                return linea;
            }
            if (booking.equals(getBooking() + "-" + linea.getBooking())) {
                return linea;
            }
        }
        return null;
    }
    
    private ReservaLinea getLineaByBookingOrFail(String booking) {
        ReservaLinea linea = getLineaByBooking(booking);
        ArgumentValidator.isNotNull(linea, "La reserva \"" + getFriendlyId() + "\" no tiene ninguna linea con booking \"" + this.booking + "-" + booking + "\".");
        return linea;
    }

    public Stock getStockMovimiento(String booking) {
        ReservaLineaRO linea = getLinea(booking);
        ArgumentValidator.isNotNull(linea, "La reserva \"" + getFriendlyId() + "\" no tiene ninguna linea con booking \"" + this.booking + "-" + booking + "\".");
        if (linea != null) {
            Movimiento movimiento = linea.getMovimiento();
            //ArgumentValidator.isNotNull(movimiento, "La reserva \"" + getFriendlyId() + "\" con booking \"" + this.booking + "-" + linea.getBooking() + "\" no tiene movimiento.");
            if (movimiento != null) {
                //MovimientoLinea movimientoLinea = movimiento.getLinea(booking);
                //ArgumentValidator.isNotNull(movimientoLinea, "La reserva \"" + getFriendlyId() + "\" con booking \"" + this.booking + "-" + linea.getBooking() + "\" no tiene la linea de movimiento correcta.");
                //ArgumentValidator.isNotNull(movimientoLinea.getStock(), "La reserva \"" + getFriendlyId() + "\" con booking \"" + this.booking + "-" + linea.getBooking() + "\" tiene un movmimiento sin stock.");
                return movimiento.getStock(booking);
            }
        }
        return null;
    }

    public void anularMovimiento(Movimiento movimiento) {
        for (ReservaLinea linea : lineas) {
            if ((linea.getMovimiento() != null) && (linea.getMovimiento().equals(movimiento))) {
                linea.setMovimiento(null);
                if (albaran != null) {
                    System.err.println("COJONES BAYYYYYYY 00000000000000 " + booking);
                    albaran.onMovimientoAnulado(linea.getBooking());
                }
            }
        }
        setEstado(ReservaEstado.ACTIVA);
        
    }

    public void cancelarReserva(String booking) {
        anular();
    }
        
    public void onMovimientoAnulado(String booking) {
        ReservaLinea linea = getLineaByBooking(booking);
        ArgumentValidator.isNotNull(linea, "La reserva \"" + getFriendlyId() + "\" no tiene ninguna linea con booking \"" + this.booking + "-" + booking + "\".");
        if (linea != null) {
            albaran.onMovimientoAnulado(linea.getBooking());
            System.err.println("COJONES: reserva.anularmovimiento: set null " + linea.getMovimiento().getId());
            linea.setMovimiento(null);
            System.err.println("COJONES: reserva.anularmovimiento: set null " + booking);
            setEstado(ReservaEstado.ACTIVA);
        }        
    }

    public Date getFechaEntrega(String booking) {
        return getLineaByBookingOrFail(booking).getFechaEntrega();        
    }
  /*  public void onMovimientoRealizado(Movimiento movimiento) {
        
        if (booking.equals(movimiento.getBooking())) {
            Boolean finalizar = false;
            for (MovimientoLinea movimientoLinea : movimiento.getLineas()) {
                albaran.onMovimientoRealizado(booking, movimientoLinea.getStock(), fecha);
                ReservaLinea linea = getLineaByBooking(movimientoLinea.getBooking());
                if (linea != null) {
                    linea.setMovimiento(movimiento);
                    //linea.setStock(movimientoLinea.getStock());
                    finalizar = true;
                }
            }
            if (finalizar) {
                setEstadoFecha(movimiento.getFecha());
                setEstado(ReservaEstado.FINALIZADO);
                getAlbaran().onReservaFinalizada(this);
            }
        }
        log.finish();
    }*/
    
    public void onMovimientoRealizado(String booking, Date fecha, Stock stock, Movimiento movimiento) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        ArgumentValidator.isNotNull(movimiento, "No se puede notificar un movimiento a una reserva sin especificar el movimiento.");
        ArgumentValidator.isNotNull(stock, "No se puede notificar un movimiento a una reserva sin especificar el stock.");
        ReservaLinea linea = getLineaByBookingOrFail(booking);
        //if (linea != null) {
            linea.setMovimiento(movimiento);
            if (albaran != null) {
                albaran.onMovimientoRealizado(booking, stock, fecha);
            }
            if (estaTodoEntregado()) {
                setEstado(ReservaEstado.FINALIZADO);
                getAlbaran().onReservaFinalizada(this);
            }
        //}
    }

    private boolean estaTodoEntregado() {
        for (ReservaLinea linea : lineas) {
            if (linea.getMovimiento() == null) {
                return false;
            }
        }
        return true;
    }

}
