package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.albaran.AlbaranEntrega;
import es.zarca.covellog.domain.model.albaran.AlbaranEstado;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.domain.model.contrato.exception.ContratoLineaVentaNoSePuedeRecogerException;
import es.zarca.covellog.domain.model.contrato.exception.EntregaPosteriorRecogidaPrevistaException;
import es.zarca.covellog.domain.model.contrato.exception.LineaComplementoNotExistException;
import es.zarca.covellog.domain.model.contrato.exception.LineaGastoAdicionalNotExistException;
import es.zarca.covellog.domain.model.contrato.exception.PeriodoConEntregaPosteriorRecogidaException;
import es.zarca.covellog.domain.model.contrato.exception.PeriodoConRecogidaSinEntregaException;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.exception.DomainExceptionHandler;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.transporte.Transporte;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "contrato_linea")
@XmlRootElement
public class ContratoLinea implements ContratoLineaRO, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_linea")
    private Integer numLinea;
    @NotNull
    @Column(name = "tipo_operacion_id")
    private ContratoTipoOperacion tipoOperacion;
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Ubicacion ubicacion;
    @Size(min = 0, max = 20)
    @Column(name = "lote")
    private String lote;
    @Size(min = 0, max = 20)
    @Column(name = "num_serie")
    private String numeroSerie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "concepto")
    private String concepto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "importe")
    private BigDecimal importe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega_prevista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntregaPrevista;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrega;
    @Column(name = "fecha_devolucion_prevista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevolucionPrevista;
    @Column(name = "fecha_devolucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDevolucion;
    private DobleObservacion observaciones;
    @JoinColumn(name = "entrega_transporte_id", referencedColumnName = "id")
    @ManyToOne
    private Transporte entregaTransporte;
    @Column(name = "entrega_importe")
    private BigDecimal entregaImporte;
    @JoinColumn(name = "recogida_transporte_id", referencedColumnName = "id")
    @ManyToOne
    private Transporte recogidaTransporte;
    @Column(name = "recogida_importe")
    private BigDecimal recogidaImporte;
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contrato contrato;
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stock;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "linea", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<ContratoLineaComplemento> complementos;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "linea", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<ContratoLineaGastoAdicional> gastosAdicionales;
    @JoinColumn(name = "entrega_albaran_id", referencedColumnName = "id")
    @ManyToOne
    private AlbaranEntrega albaranEntrega;
    @JoinColumn(name = "recogida_albaran_id", referencedColumnName = "id")
    @ManyToOne
    private AlbaranRecogida albaranRecogida;
    
    
    public ContratoLinea() {
    }
    
    public ContratoLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        this.tipoOperacion = tipoOperacion;
        this.fechaEntregaPrevista = fechaEntregaPrevista;
        this.concepto = concepto;
        this.importe = importe;
        if (entrega != null) {
            this.entregaTransporte = entrega.getTransporte();
            this.entregaImporte = entrega.getImporte();
        } else {
            this.entregaTransporte = null;
            this.entregaImporte = null;
        }
        if (recogida != null) {
            this.recogidaTransporte = recogida.getTransporte();
            this.recogidaImporte = recogida.getImporte();
        } else {
            this.recogidaTransporte = null;
            this.recogidaImporte = null;
        }
        this.observaciones = observaciones;
        int i=1;
        this.complementos = new ArrayList();
        for (ContratoLineaComplementoVO complemento : complementos) {
            this.complementos.add(
                new ContratoLineaComplemento(
                    this, 
                    complemento.getTipoProducto(), 
                    complemento.getConcepto(),
                    complemento.getCantidad(), 
                    complemento.getImporte(), 
                    i
                )
            );
            i++;
        }
        i=1;
        this.gastosAdicionales = new ArrayList();
        for (ContratoLineaGastoAdicionalVO gastoAdicional :  gastosAdicionales) {
            this.gastosAdicionales.add(
                new ContratoLineaGastoAdicional(
                    this, 
                    gastoAdicional.getTipoProducto(), 
                    gastoAdicional.getConcepto(),
                    gastoAdicional.getCantidad(), 
                    gastoAdicional.getImporte(), 
                    i
                )
            );
            i++;
        }
        
    }

    public ContratoLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        Stock stock,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        this(tipoOperacion, fechaEntregaPrevista, concepto, importe, entrega, recogida, complementos, gastosAdicionales, observaciones);
        this.stock = stock;
        tipoProducto = stock.getTipoProducto();
        ubicacion = stock.getUbicacion();
        lote = stock.getLote();
        numeroSerie = stock.getNumeroSerie();
        reservarStockComercial();
    }
    
    
    
    public ContratoLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        TipoProducto tipoProducto,
        Ubicacion ubicacion,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        this(tipoOperacion, fechaEntregaPrevista, concepto, importe, entrega, recogida, complementos, gastosAdicionales, observaciones);
        this.tipoProducto = tipoProducto;
        this.ubicacion = ubicacion;
        lote = null;
        stock = null;
        numeroSerie = null;
    }
    
    public ContratoLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        TipoProducto tipoProducto,
        Ubicacion ubicacion,
        String lote,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        this(tipoOperacion, fechaEntregaPrevista, concepto, importe, entrega, recogida, complementos, gastosAdicionales, observaciones);
        this.tipoProducto = tipoProducto;
        this.ubicacion = ubicacion;
        this.lote = lote;
        stock = null;
        numeroSerie = null;
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
    @Override
    public Integer getNumLinea() {
        return numLinea;
    }
    
    public void setNumLinea(Integer numLinea) {
        this.numLinea = numLinea;
    }

    @Override
    public String getBooking() {
        return contrato.getFriendlyId() + "-" + id.toString();
    }
    
    @Override
    public ContratoTipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void cambiarTipoOperacion(ContratoTipoOperacion tipoOperacion) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!this.tipoOperacion.equals(this.tipoOperacion)) {
                cancelarReservaStockComercialSiLaHay();
                if (tipoOperacion.equals(ContratoTipoOperacion.VENTA)) {
                    System.err.println("COJONES PONGO A NULL");
                    recogidaTransporte = null;
                    recogidaImporte = null;
                    fechaDevolucion = null;
                    fechaDevolucionPrevista = null;
                }
                this.tipoOperacion = tipoOperacion;

            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }               
    }

    @Override
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    @Override
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    @Override
    public String getLote() {
        return lote;
    }

    @Override
    public String getConcepto() {
        return concepto;
    }

    public void modificarConcepto(String concepto) {
        this.concepto = concepto;
    }

    @Override
    public BigDecimal getImporte() {
        return importe;
    }

    public void modificarImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
    @Override
    public BigDecimal getImporteTotal() {
            return importe.
                add(getComplementosImporte()
            );


    }
    
    public BigDecimal getImporteTotalBase() {
        return importe;
    }
    
    public BigDecimal getImporteTransportes() {
        BigDecimal result = BigDecimal.ZERO;
        if (entregaImporte != null) {
            result = result.add(entregaImporte);
        }
        if (recogidaImporte != null) {
            result = result.add(recogidaImporte);
        }
        return result;
    }
    
    private void assertFechasCorrectas() {
        if (fechaEntrega == null) {
            if (fechaDevolucion != null) {
                throw new PeriodoConRecogidaSinEntregaException();
            }
        } else {
            if ((fechaDevolucion != null) && (fechaEntrega.after(fechaDevolucion))) {
                throw new PeriodoConEntregaPosteriorRecogidaException(fechaEntrega, fechaDevolucion);
            }
            if ((fechaDevolucionPrevista != null) && (fechaDevolucionPrevista.before(fechaEntrega))) {
                throw new EntregaPosteriorRecogidaPrevistaException();
            }
        }
    }

    private void assertNoesVenta() {
        if (getTipoOperacion().equals(ContratoTipoOperacion.VENTA)) {
            if (stock != null) {
                throw new ContratoLineaVentaNoSePuedeRecogerException(contrato.getId(), stock);
            } else {
                throw new ContratoLineaVentaNoSePuedeRecogerException(contrato.getId(), id);
            }
        }
    }
    
    @Override
    public Date getFechaEntregaPrevista() {
        return fechaEntregaPrevista;
    }  

    public void modificarFechaEntregaPrevista(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNotNull(fecha, "La fecha prevista de entrega es obligatoria.");
            //no esta alquiladoArgumentValidator.isNull(this.fechaEntrega, "No puede cambiar la fecha de entrega prevista del producto " + stock.getNumSerie() + " porque ya esta entregado.");
            if (!fechaEntregaPrevista.equals(fecha)) {
                fechaEntregaPrevista = fecha;
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void entregar(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNotNull(stock, "No puede entregar lineas sin asignar.");
            ArgumentValidator.isNull(this.fechaEntrega, "El producto " + stock.getNumeroSerie() + " ya estaba entregado.");               
            this.fechaEntrega = fecha;
            Ubicacion ubicacionDestino;
            if ((albaranEntrega != null) && (albaranEntrega.getDestino() != null)) {
                ubicacionDestino = albaranEntrega.getDestino();
            } else {
                ubicacionDestino = contrato.getDireccionEnvio();
            } 
            ArgumentValidator.isNotNull(ubicacionDestino, "Asigne primero una direccion de envio.");
            if (tipoOperacion.equals(ContratoTipoOperacion.ALQUILER)) {
                stock.entregaAlquiler(contrato, ubicacionDestino, "ENTREGA");
            } else {
                stock.entregaVenta(contrato, ubicacionDestino, "ENTREGA");
            }       
            assertFechasCorrectas(); 
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }        
    }
    
    public void modificarFechaEntrega(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNotNull(this.fechaEntrega, "El producto " + stock.getNumeroSerie() + " no estaba entregado.");
            this.fechaEntrega = fecha;
            assertFechasCorrectas(); 
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelarEntrega() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            System.err.println("COJONES BAYYYYYYY     333333333333333333333333333");
            ArgumentValidator.isNotNull(this.fechaEntrega, "El producto " + stock.getNumeroSerie() + " no esta entregado.");
            ArgumentValidator.isNull(this.fechaDevolucion, "El producto " + stock.getNumeroSerie() + " ya esta devuelto.");
            this.fechaEntrega = null;
            System.err.println("COJONES WIN: " + tipoOperacion.getId());
            if (tipoOperacion.equals(ContratoTipoOperacion.ALQUILER)) {
                stock.cancelarEntregaAlquiler(contrato, "ENTREGA CANCELADA");
            } else {
                stock.cancelarEntregaVenta(contrato, "ENTREGA CANCELADA");
            }
            assertFechasCorrectas(); 
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public Date getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void solicitarRecogida(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            assertNoesVenta();
            ArgumentValidator.isNotNull(this.fechaEntrega, "El producto " + stock.getNumeroSerie() + " no estaba entregado.");
            this.fechaDevolucionPrevista = fecha;
            assertFechasCorrectas();
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelarSolicitudRecogida() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            assertNoesVenta();
            ArgumentValidator.isNotNull(this.fechaDevolucionPrevista, "El producto " + stock.getNumeroSerie() + " no tiene solicitud de recogida.");
            this.fechaDevolucionPrevista = null;
            assertFechasCorrectas();
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void recoger(Date fecha, Ubicacion ubicacionDestino) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            assertNoesVenta();        
            this.fechaDevolucion = fecha;
            stock.recogidaAlquiler(contrato, ubicacionDestino, "RECOGIDA");
            assertFechasCorrectas();
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    void modificarFechaRecogida(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            assertNoesVenta();
            ArgumentValidator.isNotNull(this.fechaDevolucion, "El producto " + stock.getNumeroSerie() + " no estaba entregado.");
            this.fechaDevolucion = fecha;
            assertFechasCorrectas(); 
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
     
    public void cancelarRecogida() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            assertNoesVenta();
            this.fechaDevolucion = null;
            assertFechasCorrectas();
            stock.cancelarRecogidaAlquiler(contrato, "RECOGIDA");
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void modificarObservaciones(DobleObservacion observaciones) {
        this.observaciones = observaciones;
    }
    
    @Override
    public TransporteConPrecio getTransporteEntregaConPrecio() {
        System.err.println("COJONES getTransporteEntregaConPrecio: " + entregaTransporte);
        return new TransporteConPrecio(entregaTransporte, entregaImporte);
    }
    
    public void modificarTransporteEntregaConPrecio(TransporteConPrecio transporteConPrecio) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (transporteConPrecio != null) {
                entregaTransporte = transporteConPrecio.getTransporte();
                entregaImporte = transporteConPrecio.getImporte();
            } else {
                entregaTransporte = null;
                entregaImporte = null;
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public TransporteConPrecio getTransporteRecogidaConPrecio() {
        return new TransporteConPrecio(recogidaTransporte, recogidaImporte);
    }
    
    public void modificarTransporteRecogidaConPrecio(TransporteConPrecio transporteConPrecio) {  
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (((transporteConPrecio != null) || (recogidaTransporte != null) || (recogidaImporte != null)) &&
                (!Objects.equals(transporteConPrecio, new TransporteConPrecio(recogidaTransporte, recogidaImporte)))) {
                assertNoesVenta();
                if (transporteConPrecio != null) {
                    recogidaTransporte = transporteConPrecio.getTransporte();
                    recogidaImporte = transporteConPrecio.getImporte();
                } else {
                    recogidaTransporte = null;
                    recogidaImporte = null;
                }
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public String getNumSerie() {
        return numeroSerie;
    }
    
    @Override
    public Stock getProducto() {
        return stock;
    }
    
    private void reservarStockComercial() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (stock != null) {
                if (tipoOperacion.equals(ContratoTipoOperacion.ALQUILER)) {
                    stock.reservarParaAlquiler(getContrato(), "ASIGNACION PRODUCTO");
                } else {
                    stock.reservarParaVenta(getContrato(), "ASIGNACION PRODUCTO");
                }
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void cancelarReservaStockComercialSiLaHay() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (this.stock != null) {
                this.stock.cancelarReservaComercialSiTiene(getContrato(), "DESASIGNACION PRODUCTO");
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void asignarProducto(Stock producto) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!Objects.equals(producto, stock)) {
                ArgumentValidator.isNotNull(producto, "El producto es obligatorio.");  
                ArgumentValidator.isNull(fechaEntrega, "Solo puede asignar un producto a lineas sin entregar."); 
                cancelarReservaStockComercialSiLaHay();          
                tipoProducto = producto.getTipoProducto();
                ubicacion = producto.getUbicacion();
                lote = producto.getLote();
                numeroSerie = producto.getNumeroSerie();
                stock = producto;
                reservarStockComercial();
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    void asignarProducto(TipoProducto tipoProducto, Ubicacion ubicacion, String lote) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if ((stock != null) || 
                (!Objects.equals(this.tipoProducto, tipoProducto)) || 
                (!Objects.equals(this.ubicacion, ubicacion)) || 
                (!Objects.equals(this.lote, lote))) {
                ArgumentValidator.isNull(fechaEntrega, "Solo puede asignar un producto a una lineas sin entregar."); 
                ArgumentValidator.isNotNull(tipoProducto, "El tipo de producto es obligatorio.");
                ArgumentValidator.isNotNull(ubicacion, "El ubicacion es obligatorio.");
                ArgumentValidator.isNotNull(lote, "El lote es obligatorio.");
                cancelarReservaStockComercialSiLaHay();
                stock = null;
                this.tipoProducto = tipoProducto;
                this.ubicacion = ubicacion;
                this.lote = lote;
                numeroSerie = null;
                reservarStockComercial();
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    /*void asignarProducto(TipoProducto tipoProducto, Ubicacion ubicacion) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if ((!Objects.equals(this.tipoProducto, tipoProducto)) || (!Objects.equals(this.ubicacion, ubicacion))) {
                ArgumentValidator.isNull(fechaEntrega, "Solo puede asignar un producto a una lineas sin entregar.");
                ArgumentValidator.isNotNull(tipoProducto, "El tipo de producto es obligatorio.");
                ArgumentValidator.isNotNull(ubicacion, "El ubicacion es obligatorio.");
                cancelarReservaStockComercialSiLaHay();
                stock = null;
                this.tipoProducto = tipoProducto;
                this.ubicacion = ubicacion;
                lote = null;
                numeroSerie = null;
                reservarStockComercial();
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    /*
    public int getNumComplementos() {
        return complementos.size();
    }
    */
    
    private ContratoLineaComplemento getComplementoById(Integer complementoId) {
        for (ContratoLineaComplemento complemento : complementos) {
            if (Objects.equals(complementoId, complemento.getId())) {
                return complemento;
            }
        }
        return null;
    }
    
    @Override
    public List<ContratoLineaComplementoRO> getComplementos() {
        return Collections.unmodifiableList(complementos);
    }
    
    public void anadirComplemento(TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        complementos.add(new ContratoLineaComplemento(this, tipoProducto, concepto, cantidad, importe, complementos.size() + 1));
    }
    
    public void modificarComplemento(Integer complementoId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        ContratoLineaComplemento lineaComplemento = getComplementoById(complementoId);
        if (lineaComplemento == null) {
            throw new LineaComplementoNotExistException(contrato, this, complementoId);
        }
        lineaComplemento.setTipoProducto(tipoProducto);
        lineaComplemento.setConcepto(concepto);
        lineaComplemento.setCantidad(cantidad);
        lineaComplemento.setImporte(importe);
    }
    
    public void modificarComplementos(List<ContratoLineaComplementoVO> complementos) {
        int i=1;
        this.complementos = new ArrayList();
        for (ContratoLineaComplementoVO complemento : complementos) {
            this.complementos.add(
                new ContratoLineaComplemento(
                    this, 
                    complemento.getTipoProducto(), 
                    complemento.getConcepto(),
                    complemento.getCantidad(), 
                    complemento.getImporte(), 
                    i
                )
            );
            i++;
        }
    }

    public void eliminarComplemento(Integer complementoId) {
        complementos.remove(getComplementoById(complementoId));
    }
    
    public void eliminarComplementos() {
        while (complementos.size() > 0) {
            complementos.remove(0);
        }
    }
    
    @Override
    public BigDecimal getComplementosImporte() {
        BigDecimal result = BigDecimal.ZERO;
        for (ContratoLineaComplemento complemento : complementos) {
            result = result.add(complemento.getImporteTotal());
        }
        return result;
    }
    /*
    public void subirComplemento(int index) {
        if (index > 0) {
            ContratoLineaComplemento complemento = getLineaComplemento(index);
            complementos.set(index, complementos.get(index - 1));
            complementos.set(index - 1, complemento);
        }
    }

    public void bajarComplemento(int index) {
        if (index < complementos.size()) {
            ContratoLineaComplemento complemento = getLineaComplemento(index);
            complementos.set(index, complementos.get(index + 1));
            complementos.set(index + 1, complemento);
        }
    }
    */
    
    /*
    public int getNumGastosAdicionales() {
        return gastosAdicionales.size();
    }
    */
   
    private ContratoLineaGastoAdicional getGastoAdicionalById(Integer gastoAdicionalId) {
        for (ContratoLineaGastoAdicional gastoAdicional : gastosAdicionales) {
            if (Objects.equals(gastoAdicionalId, gastoAdicional.getId())) {
                return gastoAdicional;
            }
        }
        return null;
    }
    
    @Override
    public List<ContratoLineaGastoAdicionalRO> getGastosAdicionales() {
        return Collections.unmodifiableList(gastosAdicionales);
    }
    
    public void anadirGastoAdicional(TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {        
        gastosAdicionales.add(
            new ContratoLineaGastoAdicional(
                this, 
                tipoProducto,
                concepto,
                cantidad,
                importe,
                gastosAdicionales.size() + 1
            )
        ); 
    }
    
    public void modificarGastoAdicional(Integer gastoAdicionalId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        ContratoLineaGastoAdicional lineaGastoAdicional = getGastoAdicionalById(gastoAdicionalId);
        if (lineaGastoAdicional == null) {
            throw new LineaGastoAdicionalNotExistException(contrato, this, gastoAdicionalId);
        }
        lineaGastoAdicional.setTipoProducto(tipoProducto);
        lineaGastoAdicional.setConcepto(concepto);
        lineaGastoAdicional.setCantidad(cantidad);
        lineaGastoAdicional.setImporte(importe);
    }
    
    public void modificarGastosAdicionales(List<ContratoLineaGastoAdicionalVO> gastosAdicionales) {
        int i=1;
        this.gastosAdicionales = new ArrayList();
        for (ContratoLineaGastoAdicionalVO gastoAdicional :  gastosAdicionales) {
            this.gastosAdicionales.add(
                new ContratoLineaGastoAdicional(
                    this, 
                    gastoAdicional.getTipoProducto(), 
                    gastoAdicional.getConcepto(),
                    gastoAdicional.getCantidad(), 
                    gastoAdicional.getImporte(), 
                    i
                )
            );
            i++;
        }
    }
    
    public void eliminarGastoAdicional(Integer gastoAdicionalId) {
        gastosAdicionales.remove(getGastoAdicionalById(gastoAdicionalId));
    }
    
    public void eliminarGastosAdicionales() {
        while (gastosAdicionales.size() > 0) {          
            gastosAdicionales.remove(0);
        }
        //gastosAdicionales = new ArrayList();
    }
    
    @Override
    public BigDecimal getGastosAdicionalesImporte() {
        BigDecimal result = BigDecimal.ZERO;
        for (ContratoLineaGastoAdicional gastoAdicional : gastosAdicionales) {
            result = result.add(gastoAdicional.getImporteTotal());
        }
        return result;
    }
    
    /*
    public void subirGastoAdicional(int index) {
        if (index > 0) {
            ContratoLineaGastoAdicional gastoAdicional = getLineaGastoAdicional(index);
            gastosAdicionales.set(index, gastosAdicionales.get(index - 1));
            gastosAdicionales.set(index - 1, gastoAdicional);
        }
    }
    
    public void bajarGastoAdicional(int index) {
        if (index < complementos.size()) {
            ContratoLineaGastoAdicional gastoAdicional = getLineaGastoAdicional(index);
            gastosAdicionales.set(index, gastosAdicionales.get(index + 1));
            gastosAdicionales.set(index + 1, gastoAdicional);
        }
    }
    */

    @Override
    public AlbaranEntrega getAlbaranEntrega() {
        return albaranEntrega;
    }

    public void asignarAlbaranEntrega(AlbaranEntrega albaranEntrega) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!Objects.equals(this.albaranEntrega, albaranEntrega)) {
                //ArgumentValidator.isNotEqual(estadoAnterior, AlbaranEstado.ANULADO, "No puede asignar productos a albaranes ANULADOS.");
                //ArgumentValidator.isNotEqual(estadoAnterior, AlbaranEstado.FINALIZADO, "No puede asignar productos a albaranes FINALIZADOS.");
                //Si hay albaran de entrega previo lo quita
                String booking = getBooking();
                if ((this.albaranEntrega != null) && (this.albaranEntrega.lineasContiene(booking))) {
                    AlbaranEstado estadoAnterior = this.albaranEntrega.getEstado();
                    if (AlbaranEstado.ACTIVO.equals(estadoAnterior)) {
                        this.albaranEntrega.reabrir();
                    }
                    this.albaranEntrega.lineaEliminar(booking); 
                    if (AlbaranEstado.ACTIVO.equals(estadoAnterior)) {
                        if (this.albaranEntrega != null) this.albaranEntrega.activar();
                    }
                }
                if ((albaranEntrega != null) && (!albaranEntrega.lineasContiene(booking))) {
                    AlbaranEstado estadoAnterior = albaranEntrega.getEstado();
                    if (AlbaranEstado.ACTIVO.equals(estadoAnterior)) {
                        albaranEntrega.reabrir();
                    }
                    if (stock == null) {
                        albaranEntrega.lineaAnadir(booking, tipoProducto, lote, concepto, getCheckList());
                    } else {
                        albaranEntrega.lineaAnadir(booking, stock, concepto, getCheckList());
                    }
                    if (AlbaranEstado.ACTIVO.equals(estadoAnterior)) {
                        albaranEntrega.activar();
                    }
                }
                this.albaranEntrega = albaranEntrega;
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public AlbaranRecogida getAlbaranRecogida() {
        return albaranRecogida;
    }

    public void asignarAlbaranRecogida(AlbaranRecogida albaranRecogida) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!Objects.equals(this.albaranRecogida, albaranRecogida)) {
                //Si hay albaran de entrega previo lo quita
                String booking = getBooking();
                if ((this.albaranRecogida != null) && (this.albaranRecogida.lineasContiene(booking))) {
                    this.albaranRecogida.lineaEliminar(booking); 
                }
                if ((albaranRecogida != null) && (!albaranRecogida.lineasContiene(booking))) {
                    if (stock == null) {
                        albaranRecogida.lineaAnadir(booking, tipoProducto, lote, concepto, getCheckList());
                    } else {
                        albaranRecogida.lineaAnadir(booking, stock, concepto, getCheckList());
                    }
                }
                this.albaranRecogida = albaranRecogida;
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
        
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
        if (!(object instanceof ContratoLinea)) {
            return false;
        }
        ContratoLinea other = (ContratoLinea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.contrato.ContratoLinea[ id=" + id + " ]";
    }

    void desasociarAlbaranEntrega() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNull(fechaEntrega, "No puede cancelar la asociacion de la linea de contrato con el albaran de entrega porque ya esta entregada");
            albaranEntrega = null;
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    void desasociarAlbaranRecogida() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNull(fechaDevolucion, "No puede cancelar la asociacion de la linea de contrato con el albaran de recogida porque ya esta recogido");
            albaranRecogida = null;
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    void recoger(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNotNull(this.fechaEntrega, "El producto " + stock.getNumeroSerie() + " no estaba entregado."); 
            ArgumentValidator.isNull(this.fechaDevolucion, "El producto " + stock.getNumeroSerie() + " ya estaba recogido.");
            ArgumentValidator.isNotNull(albaranRecogida, "El producto " + stock.getNumeroSerie() + " no se puede recoger porque no tiene albaran.");
            ArgumentValidator.isTrue(tipoOperacion.equals(ContratoTipoOperacion.ALQUILER), "No puede recoger el producto " + stock.getNumeroSerie() + " porque es una venta."); 
            this.fechaDevolucion = fecha;
            stock.recogidaAlquiler(contrato, albaranRecogida.getDestino(), "RECOGIDA");
            assertFechasCorrectas(); 
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    void eliminar() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNull(getFechaEntrega(), "No se pueden eliminar lineas de productos entregados.");
            cancelarReservaStockComercialSiLaHay();
            asignarAlbaranEntrega(null);
            asignarAlbaranRecogida(null);
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    private List<String> getCheckList() {
        List<String> checkList = new ArrayList();
        for (ContratoLineaComplemento complemento : complementos) {
            checkList.add(complemento.getCantidad() + " x " + complemento.getConcepto());
        }
        for (ContratoLineaGastoAdicional gastosAdicional : gastosAdicionales) {
            checkList.add(gastosAdicional.getCantidad() + " x " + gastosAdicional.getConcepto());
        }
        return checkList;
    }
    
    void lineaActualizarAlbaranEntregaSiTiene() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if ((albaranEntrega != null) && (fechaEntrega == null)) {
                AlbaranEstado estadoAnterior = this.albaranEntrega.getEstado();
                if (AlbaranEstado.ACTIVO.equals(estadoAnterior)) {
                    this.albaranEntrega.reabrir();
                }
                if (stock != null) {
                    albaranEntrega.lineaModificar(getBooking(), stock, concepto, getCheckList());
                } else {
                    albaranEntrega.lineaModificar(getBooking(), tipoProducto, lote, concepto, getCheckList());
                }
                if (AlbaranEstado.ACTIVO.equals(estadoAnterior)) {
                    this.albaranEntrega.activar();
                }
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    public ContratoLineaEstado getEstado() {
        if (fechaEntrega == null) {
            return ContratoLineaEstado.PENDIENTE_ENTREGA;
        } else {
            if (fechaDevolucion == null) {
                if (ContratoTipoOperacion.VENTA.equals(tipoOperacion)) {
                    return ContratoLineaEstado.FINALIZADO;
                } else {
                    if (fechaDevolucionPrevista == null) {
                        return ContratoLineaEstado.EN_ALQUILER; 
                    } else {
                        return ContratoLineaEstado.PENDIENTE_RECOGIDA;
                    }
                }
            } else {
                return ContratoLineaEstado.FINALIZADO;
            }
        }
        
    }

    

}
