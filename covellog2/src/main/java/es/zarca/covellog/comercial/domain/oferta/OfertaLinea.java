package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.comercial.domain.base.GastoTipoProducto;
import es.zarca.covellog.comercial.domain.oferta.exception.OfertaLineaComplementoNotExistException;
import es.zarca.covellog.comercial.domain.oferta.exception.OfertaLineaGastoAdicionalNotExistException;
import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import es.zarca.covellog.domain.model.contrato.TransporteConPrecio;
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
import java.util.ArrayList;
import java.util.Collection;
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
@Table(name = "oferta_linea")
@XmlRootElement
public class OfertaLinea implements IOfertaLinea, Serializable {

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
    
    @JoinColumn(name = "oferta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oferta oferta;
    
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stock;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "linea", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<OfertaLineaComplemento> complementos;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "linea", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<OfertaLineaGastoAdicional> gastosAdicionales;

    public OfertaLinea() {
    }

    
     
    public OfertaLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<IGastoTipoProducto> complementos,
        List<IGastoTipoProducto> gastosAdicionales,
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
        this.complementos = new ArrayList<>(complementos.size());
        for (IGastoTipoProducto complemento : complementos) {
            this.complementos.add(
                new OfertaLineaComplemento(
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
        this.gastosAdicionales = new ArrayList<>(gastosAdicionales.size());
        for (IGastoTipoProducto gastoAdicional :  gastosAdicionales) {
            this.gastosAdicionales.add(
                new OfertaLineaGastoAdicional(
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

    OfertaLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        Stock stock,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<IGastoTipoProducto> complementos,
        List<IGastoTipoProducto> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        this(tipoOperacion, fechaEntregaPrevista, concepto, importe, entrega, recogida, complementos, gastosAdicionales, observaciones);
        this.stock = stock;
        tipoProducto = stock.getTipoProducto();
        ubicacion = stock.getUbicacion();
        lote = stock.getLote();
        numeroSerie = stock.getNumeroSerie();
    }
    
    OfertaLinea(
        ContratoTipoOperacion tipoOperacion,
        Date fechaEntregaPrevista,
        SeleccionProducto seleccionProducto,
        String concepto,
        BigDecimal importe,
        TransporteConPrecio entrega,
        TransporteConPrecio recogida,
        List<IGastoTipoProducto> complementos,
        List<IGastoTipoProducto> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        this(tipoOperacion, fechaEntregaPrevista, concepto, importe, entrega, recogida, complementos, gastosAdicionales, observaciones);
        if (seleccionProducto.getStock() != null) {
            stock = seleccionProducto.getStock();
            tipoProducto = stock.getTipoProducto();
            ubicacion = stock.getUbicacion();
            lote = stock.getLote();
            numeroSerie = stock.getNumeroSerie();    
        } else {
            tipoProducto = seleccionProducto.getTipoProducto();
            ubicacion = seleccionProducto.getUbicacion();
            lote = seleccionProducto.getLote();
            stock = null;
            numeroSerie = null;
        }        
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
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
//        return oferta.getFriendlyId() + "-" + id.toString();
        return "FALTA HACER";
    }
    
    @Override
    public ContratoTipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void cambiarTipoOperacion(ContratoTipoOperacion tipoOperacion) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!this.tipoOperacion.equals(this.tipoOperacion)) {
                if (tipoOperacion.equals(ContratoTipoOperacion.VENTA)) {
                    recogidaTransporte = null;
                    recogidaImporte = null;
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
    
    @Override
    public BigDecimal getImporteTotalBase() {
        return importe;
    }
    
    @Override
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
    
    @Override
    public Date getFechaEntregaPrevista() {
        return new Date(fechaEntregaPrevista.getTime());
    }  

    public void modificarFechaEntregaPrevista(Date fecha) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            ArgumentValidator.isNotNull(fecha, "La fecha prevista de entrega es obligatoria.");
            //no esta alquiladoArgumentValidator.isNull(this.fechaEntrega, "No puede cambiar la fecha de entrega prevista del producto " + stock.getNumSerie() + " porque ya esta entregado.");
            if (!fechaEntregaPrevista.equals(fecha)) {
                fechaEntregaPrevista = new Date(fecha.getTime());
            }
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
                ArgumentValidator.isTrue(ContratoTipoOperacion.VENTA.equals(tipoOperacion), "No es una venta.");
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
    
    public void asignarProducto(Stock producto) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!Objects.equals(producto, stock)) {
                ArgumentValidator.isNotNull(producto, "El producto es obligatorio.");  
                tipoProducto = producto.getTipoProducto();
                ubicacion = producto.getUbicacion();
                lote = producto.getLote();
                numeroSerie = producto.getNumeroSerie();
                stock = producto;
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    void asignarProducto(SeleccionProducto seleccionProducto) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        ArgumentValidator.isNotNull(seleccionProducto, "La seleccion del producto es obligatoria.");
        try {
            if (seleccionProducto.getStock() != null) {
                stock = seleccionProducto.getStock();
                tipoProducto = stock.getTipoProducto();
                ubicacion = stock.getUbicacion();
                lote = stock.getLote();
                numeroSerie = stock.getNumeroSerie();    
            } else {
                tipoProducto = seleccionProducto.getTipoProducto();
                ubicacion = seleccionProducto.getUbicacion();
                lote = seleccionProducto.getLote();
                stock = null;
                numeroSerie = null;
            }
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
}

    private OfertaLineaComplemento getComplementoById(Integer complementoId) {
        for (OfertaLineaComplemento complemento : complementos) {
            if (Objects.equals(complementoId, complemento.getId())) {
                return complemento;
            }
        }
        return null;
    }
    
    @Override
    public List<OfertaLineaComplemento> getComplementos() {
        return Collections.unmodifiableList(complementos);
    }
    
    public void anadirComplemento(TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        complementos.add(new OfertaLineaComplemento(this, tipoProducto, concepto, cantidad, importe, complementos.size() + 1));
    }
    
    public void modificarComplemento(Integer complementoId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        OfertaLineaComplemento lineaComplemento = getComplementoById(complementoId);
        if (lineaComplemento == null) {
            throw new OfertaLineaComplementoNotExistException(oferta, this, complementoId);
        }
        lineaComplemento.setTipoProducto(tipoProducto);
        lineaComplemento.setConcepto(concepto);
        lineaComplemento.setCantidad(cantidad);
        lineaComplemento.setImporte(importe);
    }
    
    public void modificarComplementos(Collection<IGastoTipoProducto> complementos) {
        int i=1;
        this.complementos = new ArrayList<>(complementos.size());
        for (IGastoTipoProducto complemento : complementos) {
            this.complementos.add(
                new OfertaLineaComplemento(
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
        for (OfertaLineaComplemento complemento : complementos) {
            result = result.add(complemento.getImporteTotal());
        }
        return result;
    }
    
    private OfertaLineaGastoAdicional getGastoAdicionalById(Integer gastoAdicionalId) {
        for (OfertaLineaGastoAdicional gastoAdicional : gastosAdicionales) {
            if (Objects.equals(gastoAdicionalId, gastoAdicional.getId())) {
                return gastoAdicional;
            }
        }
        return null;
    }
    
    @Override
    public List<GastoTipoProducto> getGastosAdicionales() {
        return Collections.unmodifiableList(gastosAdicionales);
    }
    
    public void anadirGastoAdicional(TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {        
        gastosAdicionales.add(
            new OfertaLineaGastoAdicional(
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
        OfertaLineaGastoAdicional lineaGastoAdicional = getGastoAdicionalById(gastoAdicionalId);
        if (lineaGastoAdicional == null) {
            throw new OfertaLineaGastoAdicionalNotExistException(oferta, this, gastoAdicionalId);
        }
        lineaGastoAdicional.setTipoProducto(tipoProducto);
        lineaGastoAdicional.setConcepto(concepto);
        lineaGastoAdicional.setCantidad(cantidad);
        lineaGastoAdicional.setImporte(importe);
    }
    
    public void modificarGastosAdicionales(Collection<IGastoTipoProducto> gastosAdicionales) {
        int i=1;
        this.gastosAdicionales = new ArrayList<>(gastosAdicionales.size());
        for (IGastoTipoProducto gastoAdicional :  gastosAdicionales) {
            this.gastosAdicionales.add(
                new OfertaLineaGastoAdicional(
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
    
    public BigDecimal getGastosAdicionalesImporte() {
        BigDecimal result = BigDecimal.ZERO;
        for (OfertaLineaGastoAdicional gastoAdicional : gastosAdicionales) {
            result = result.add(gastoAdicional.getImporteTotal());
        }
        return result;
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
        if (!(object instanceof OfertaLinea)) {
            return false;
        }
        OfertaLinea other = (OfertaLinea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.oferta.OfertaLinea[ id=" + id + " ]";
    }

    void eliminar() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }


}
