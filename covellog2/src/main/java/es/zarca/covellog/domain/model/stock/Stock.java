package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.albaran.AlbaranEntrega;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.domain.model.app.Tags;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.exception.DomainException;
import es.zarca.covellog.domain.model.exception.DomainExceptionHandler;
import es.zarca.covellog.domain.model.exception.EstadoInicialStockNoPermitidoModelException;
import es.zarca.covellog.domain.model.exception.NoEstaAlquiladoModelException;
import es.zarca.covellog.domain.model.exception.NoEstaBloqueadoModelException;
import es.zarca.covellog.domain.model.exception.NoEstaDadoDeBajaModelException;
import es.zarca.covellog.domain.model.exception.NoEstaVendidoModelException;
import es.zarca.covellog.domain.model.exception.StockNoDisponibleModelException;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List;
import javax.inject.Inject;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Customizer;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "stock")
@Customizer(StockHistoricoActivoCustomizer.class)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findById", query = "SELECT s FROM Stock s WHERE s.id = :id")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "propietario_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa propietario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "propietario_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date propietarioFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "propietario_observaciones")
    private String propietarioObservaciones;
    @Embedded
    @AttributeOverride(name="tags", column=@Column(name="propietario_tags"))
    private Tags propietarioTags;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoPropietario detallePropietario;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoPropietario> historicoPropietario;
    
    @NotNull
    @Column(name = "condicion_id")
    private CondicionStock condicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "condicion_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date condicionFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "condicion_observaciones")
    private String condicionObservaciones;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoCondicion detalleCondicion;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoCondicion> historicoCondiciones;
    
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne
    private StockEstado estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_fecha_efectiva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFechaEfectiva;
    @Lob
    @Size(max = 10000)
    @Column(name = "estado_observaciones")
    private String estadoObservaciones;
    @JoinColumn(name = "estado_contrato_id", referencedColumnName = "id")
    @ManyToOne
    private Contrato estadoContrato;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoEstado detalleEstado;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("id ASC")
    private List<StockHistoricoEstado> historicoEstados;
    
    @JoinColumn(name = "flota_id", referencedColumnName = "id")
    @ManyToOne
    private Flota flota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flota_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flotaFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "flota_observaciones")
    private String flotaObservaciones;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoFlota detalleFlota;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoFlota> historicoFlotas;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "lote")
    private String lote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lote_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loteFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "lote_observaciones")
    private String loteObservaciones;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoLote detalleLote;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoLote> historicoLotes;
    
    
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne
    private TipoProducto tipoProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_producto_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tipoProductoFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "tipo_producto_observaciones")
    private String tipoProductoObservaciones;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoTipoProducto detalleTipoProducto;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)*/
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoTipoProducto> historicoTiposProducto;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "num_serie")
    private String numSerie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num_serie_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date numSerieFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "num_serie_observaciones")
    private String numSerieObservaciones;
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoNumeroSerie detalleNumeroSerie;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoNumeroSerie> historicoNumerosSerie;
    
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    @ManyToOne
    private Ubicacion ubicacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ubicacion_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ubicacionFecha;
    @Lob
    @Size(max = 10000)
    @Column(name = "ubicacion_observaciones")
    private String ubicacionObservaciones;
    
    /*@OneToOne(mappedBy = "stock")
    private StockHistoricoUbicacion detalleUbicacion;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoUbicacion> historicoUbicaciones;
    
    @OneToOne(mappedBy = "stock")
    private StockHistoricoContrato detalleContrato;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stock", orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<StockHistoricoContrato> historicoContratos;
    
    private DobleObservacion observaciones;
    
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="albaran_entrega_id")
    private AlbaranEntrega albaranEntrega;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="albaran_recogida_id")
    private AlbaranRecogida albaranRecogida;
    


   
    public Stock() {
    }

    public Stock(StockEstado estado, Date fecha, String observaciones) {
        if ( (!estado.equals(StockEstado.DISPONIBLE)) && (!estado.equals(StockEstado.BLOQUEADO)) ) {
            throw new EstadoInicialStockNoPermitidoModelException(estado);
        }
        cambiarEstado(estado, fecha, observaciones);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Empresa getPropietario() {
        /*if (getDetallePropietario() == null) {
            return null;
        }
        return getDetallePropietario().getPropietario();*/
        return propietario;
    }

    public Date getPropietarioFecha() {
        return propietarioFecha;
    }

    public String getPropietarioObservaciones() {
        return propietarioObservaciones;
    }

    public Tags getPropietarioTags() {
        return propietarioTags;
    }
    
    /*public StockHistoricoPropietario getDetallePropietario() {
        return detallePropietario;
    }*/

    public List<StockHistoricoPropietario> getHistoricoPropietarios() {
        return historicoPropietario;
    }
    
    public void cambiarPropietario(Empresa empresa, Date fechaCambio, Tags tags, String observaciones) {
        if (historicoPropietario == null) {
            historicoPropietario = new ArrayList();
        }
        //finaliza el periodo actual, si es que tiene (REGLA: SOLO UN PERIODO ABIERTO PARA HISTORICOS)
        /*if (historicoPropietario.size() > 0) {
            historicoPropietario.get(historicoPropietario.size() - 1).finalizar(fechaCambio);
        }
        historicoPropietario.add(
            new StockHistoricoPropietario(empresa, tags, this, fechaCambio, observaciones)
        );*/
        historicoPropietario.add(
            new StockHistoricoPropietario(this.propietario, this.propietarioTags, this, this.propietarioFecha, fechaCambio, this.propietarioObservaciones)
        );
        this.propietarioFecha = fechaCambio;
        this.propietarioObservaciones = observaciones;
        this.propietario = empresa;
        this.propietarioTags = tags;
    }

   public void deshacerUltimoCambioPropietario() {
        ArgumentValidator.isNotEmpty(historicoPropietario, "No se puede eliminar el ultimo propietario.");
        StockHistoricoPropietario anterior = historicoPropietario.get(historicoPropietario.size() - 1);
        this.propietarioFecha = anterior.getFechaInicio();
        this.propietarioObservaciones = anterior.getObservaciones();
        this.propietario = anterior.getPropietario();
        this.propietarioTags = anterior.getTags();
        historicoPropietario.remove(historicoPropietario.size() - 1);
    }
    
    public CondicionStock getCondicion() {
        /*if (getDetalleCondicion() == null) {
            return null;
        }
        return getDetalleCondicion().getCondicion();*/
        return condicion;
    }
    
    public Date getCondicionFecha() {
        return condicionFecha;
    }

    public String getCondicionObservaciones() {
        return condicionObservaciones;
    }
    
    /*public StockHistoricoCondicion getDetalleCondicion() {
        return detalleCondicion;
    }*/

    public List<StockHistoricoCondicion> getHistoricoCondiciones() {
        return historicoCondiciones;
    }
    
    public void cambiarCondicion(CondicionStock condicion, Date fechaCambio, String observaciones) {
        if (historicoCondiciones == null) {
            historicoCondiciones = new ArrayList();
        }
        /*//finaliza el periodo actual, si es que tiene (REGLA: SOLO UN PERIODO ABIERTO PARA HISTORICOS)
        if (historicoCondiciones.size() > 0) {
            historicoCondiciones.get(historicoCondiciones.size() - 1).finalizar(fechaCambio);
        }
        historicoCondiciones.add(
            new StockHistoricoCondicion(condicion, this, fechaCambio, observaciones)
        );*/
        historicoCondiciones.add(
            new StockHistoricoCondicion(this.condicion, this, this.condicionFecha, fechaCambio, this.condicionObservaciones)
        );
        this.condicionFecha = fechaCambio;
        this.condicionObservaciones = observaciones;
    }
    
   /* public void deshacerUltimoCambioCondicion() {
        ArgumentValidator.isNotEmpty(historicoCondiciones, "El producto no tiene condicion.");
        ArgumentValidator.isTrue(historicoCondiciones.size() > 1, "No se puede eliminar la ultima condicion.");
        historicoCondiciones.remove(historicoCondiciones.size() - 1);
        historicoCondiciones.get(historicoCondiciones.size() - 1).reabrir();
    }*/
    
    
    public StockEstado getEstado() {
        return estado;
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public Date getEstadoFechaEfectiva() {
        return estadoFechaEfectiva;
    }

    public String getEstadoObservaciones() {
        return estadoObservaciones;
    }

    public Contrato getEstadoContrato() {
        return estadoContrato;
    }
    
   /* public StockHistoricoEstado getDetalleEstado() {
        return detalleEstado;
    }*/

    public List<StockHistoricoEstado> getHistoricoEstados() {
        return historicoEstados;
    }
    
    public void cambiarEstado(StockEstado estado, Contrato contrato, Date fechaCambio, String observaciones) {
        if (historicoEstados == null) {
            historicoEstados = new ArrayList();
        }
        //Copia el estado actual en el historico finalizandolo y lo sustituye por el nuevo
        historicoEstados.add(
            new StockHistoricoEstado(this.estado, this.estadoContrato, this, this.estadoFecha, fechaCambio, this.estadoObservaciones)
        );
        this.estado = estado;
        this.estadoContrato = contrato;
        this.estadoFecha = fechaCambio;
        this.estadoObservaciones = observaciones;        
    }
    
    public void cambiarEstado(StockEstado estado, Contrato contrato, String observaciones) {
        cambiarEstado(estado, contrato, new Date(), observaciones);
    }
    
    public final void cambiarEstado(StockEstado estado, Date fechaCambio, String observaciones) {
        cambiarEstado(estado, null, fechaCambio, observaciones);
    }
    
    public final void cambiarEstado(StockEstado estado, String observaciones) {
        cambiarEstado(estado, null, new Date(), observaciones);
    }
    
    /*public void cambiarEstado(Date fechaEfectiva, EstadoStock estado, Contrato contrato, Date fechaCambio, String observaciones) {
        if (historicoEstados == null) {
            historicoEstados = new ArrayList();
        }
        //finaliza el periodo actual, si es que tiene (REGLA: SOLO UN PERIODO ABIERTO PARA HISTORICOS)
        if (historicoEstados.size() > 0) {
            historicoEstados.get(historicoEstados.size() - 1).finalizar(fechaCambio);
        }
        historicoEstados.add(
            new StockHistoricoEstado(fechaEfectiva, estado, contrato, this, fechaCambio, observaciones)
        );
    }*/
    
  /*  public void deshacerUltimoCambioEstado() {
        ArgumentValidator.isTrue(historicoEstados.size() > 0, "No se puede deshacer el ultimo estado de stock comercial del producto " + numSerie + " porque ya ha no quedan estados en el historico.");
        StockHistoricoEstado ultimoHistorico = historicoEstados.get(historicoEstados.size() - 1);
        this.estado = ultimoHistorico.getEstado();
        this.estadoContrato = ultimoHistorico.getContrato();
        this.estadoFecha = ultimoHistorico.getFechaInicio();
        this.estadoObservaciones = ultimoHistorico.getObservaciones();
        historicoEstados.remove(historicoEstados.size() - 1);
        //historicoEstados.get(historicoEstados.size() - 1).reabrir();
    }*/
    
    public void volverUltimoEstadoHistorico(String observaciones) {
        ArgumentValidator.isNotNull(historicoEstados, "El producto " + numSerie + " no tiene historico de estado.");
        ArgumentValidator.isTrue(historicoEstados.size() > 0, "El producto " + numSerie + " no puede volver al ultimo estado del historico porque el historico esta vacio.");
        StockHistoricoEstado ultimoHistorico = historicoEstados.get(historicoEstados.size() - 1);
        cambiarEstado(ultimoHistorico.getEstado(), ultimoHistorico.getContrato(), observaciones);
    }
    
    public Flota getFlota() {
        /*if (getDetalleFlota() == null) {
            return null;
        }
        return getDetalleFlota().getFlota();*/
        return flota;
    }

    public Date getFlotaFecha() {
        return flotaFecha;
    }

    public String getFlotaObservaciones() {
        return flotaObservaciones;
    }
    
    /*public StockHistoricoFlota getDetalleFlota() {
        return detalleFlota;a
    }*/

    public List<StockHistoricoFlota> getHistoricoFlotas() {
        return historicoFlotas;
    }
    
    public void cambiarFlota(Flota flota, String observaciones) {
        ArgumentValidator.isFalse(this.flota == flota, "El producto \"" + numSerie + "\" ya pertenece a la flota \"" + this.flota.getNombre() + "\"");
        Date fechaCambio = new Date();
        if (historicoFlotas == null) {
            historicoFlotas = new ArrayList();
        }
        //Copia la flota actual en el historico finalizandola y lo sustituye por la nueva        
        historicoFlotas.add(
            new StockHistoricoFlota(this.flota, this, this.flotaFecha, fechaCambio, this.flotaObservaciones)
        );
        this.flota = flota;
        this.flotaFecha = fechaCambio;
        this.flotaObservaciones = observaciones;
    }
    
    /*public void deshacerUltimoCambioFlota() {
        ArgumentValidator.isTrue(historicoFlotas.size() > 0, "No se puede deshacer el ultimo de flota de stock comercial del producto " + numSerie + " porque ya ha no quedan flotas en el historico.");
        StockHistoricoFlota ultimoHistorico = historicoFlotas.get(historicoFlotas.size() - 1);
        this.flota = ultimoHistorico.getFlota();
        this.flotaFecha = ultimoHistorico.getFechaInicio();
        this.flotaObservaciones = ultimoHistorico.getObservaciones();
        historicoFlotas.remove(historicoFlotas.size() - 1);
    }*/
    
    public String getLote() {
        return lote;
    }

    public Date getLoteFecha() {
        return loteFecha;
    }

    public String getLoteObservaciones() {
        return loteObservaciones;
    }
    
    public List<StockHistoricoLote> getHistoricoLotes() {
        return historicoLotes;
    }
    
    public void cambiarLote(String lote, Date fechaCambio, String observaciones) {
        ArgumentValidator.isFalse(this.lote == lote, "El producto \"" + numSerie + "\" ya pertenece al lote \"" + this.lote + "\"");
        if (historicoLotes == null) {
            historicoLotes = new ArrayList();
        }
        historicoLotes.add(
            new StockHistoricoLote(this.lote, this, this.loteFecha, fechaCambio, this.loteObservaciones)
        );
        this.lote = lote;
        this.loteFecha = fechaCambio;
        this.loteObservaciones = observaciones;
    }
    
    /*private void deshacerUltimoCambioLote() {
        ArgumentValidator.isTrue(historicoLotes.size() > 0, "No se puede deshacer el ultimo de lote de stock comercial del producto " + numSerie + " porque ya ha no quedan lotes en el historico.");
        StockHistoricoLote ultimoHistorico = historicoLotes.get(historicoLotes.size() - 1);
        this.lote = ultimoHistorico.getLote();
        this.loteFecha = ultimoHistorico.getFechaInicio();
        this.loteObservaciones = ultimoHistorico.getObservaciones();
        historicoLotes.remove(historicoLotes.size() - 1);
    }*/
    
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public Date getTipoProductoFecha() {
        return tipoProductoFecha;
    }

    public void setTipoProductoFecha(Date tipoProductoFecha) {
        this.tipoProductoFecha = tipoProductoFecha;
    }

    public String getTipoProductoObservaciones() {
        return tipoProductoObservaciones;
    }

    public void setTipoProductoObservaciones(String tipoProductoObservaciones) {
        this.tipoProductoObservaciones = tipoProductoObservaciones;
    }

    public List<StockHistoricoTipoProducto> getHistoricoTiposProducto() {
        return historicoTiposProducto;
    }
    
    public void cambiarTipoProducto(TipoProducto tipoProducto, Date fechaCambio, String observaciones) {
        ArgumentValidator.isFalse(this.tipoProducto == tipoProducto, "El producto \"" + numSerie + "\" ya es del tipo producto \"" + this.tipoProducto.getId() + "\"");
        if (historicoTiposProducto == null) {
            historicoTiposProducto = new ArrayList();
        }
        historicoTiposProducto.add(
            new StockHistoricoTipoProducto(this.tipoProducto, this, this.tipoProductoFecha, fechaCambio, this.tipoProductoObservaciones)
        );
        this.tipoProducto = tipoProducto;
        this.tipoProductoFecha = fechaCambio;
        this.tipoProductoObservaciones = observaciones;
    }
    
    /*public void deshacerUltimoCambioTipoProducto() {        
        ArgumentValidator.isTrue(historicoTiposProducto.size() > 0, "No se puede deshacer el ultimo de tipo de producto de stock comercial del producto " + numSerie + " porque ya ha no quedan tipos de producto en el historico.");
        StockHistoricoTipoProducto ultimoHistorico = historicoTiposProducto.get(historicoTiposProducto.size() - 1);
        this.tipoProducto = ultimoHistorico.getTipoProducto();
        this.tipoProductoFecha = ultimoHistorico.getFechaInicio();
        this.tipoProductoObservaciones = ultimoHistorico.getObservaciones();
        historicoTiposProducto.remove(historicoTiposProducto.size() - 1);
    }*/
    
    public String getNumeroSerie() {
        return numSerie;
    }

    public Date getNumSerieFecha() {
        return numSerieFecha;
    }

    public String getNumSerieObservaciones() {
        return numSerieObservaciones;
    }
    
    public List<StockHistoricoNumeroSerie> getHistoricoNumerosSerie() {
        return historicoNumerosSerie;
    }
    
    public void cambiarNumeroSerie(String numeroSerie, Date fechaCambio, String observaciones) {
        ArgumentValidator.isFalse(this.numSerie == numeroSerie, "El producto \"" + numSerie + "\" ya tiene el numero de serie \"" + this.numSerie + "\"");
        if (historicoNumerosSerie == null) {
            historicoNumerosSerie = new ArrayList();
        }
        historicoNumerosSerie.add(
            new StockHistoricoNumeroSerie(this.numSerie, this, this.numSerieFecha, fechaCambio, this.numSerieObservaciones)
        );
        this.numSerie = numeroSerie;
        this.numSerieFecha = fechaCambio;
        this.numSerieObservaciones = observaciones;
    }
    
    /*public void deshacerUltimoCambioNumeroSerie() {
        ArgumentValidator.isNotEmpty(historicoNumerosSerie, "El producto no tiene numero serie.");
        ArgumentValidator.isTrue(historicoNumerosSerie.size() > 1, "No se puede eliminar el ultimo numero serie.");
        historicoNumerosSerie.remove(historicoNumerosSerie.size() - 1);
        historicoNumerosSerie.get(historicoNumerosSerie.size() - 1).reabrir();
    }*/
    
    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    
    public List<StockHistoricoUbicacion> getHistoricoUbicaciones() {
        return historicoUbicaciones;
    }
    
    public void cambiarUbicacion(Ubicacion ubicacion, Date fechaCambio, String observaciones) {
        ArgumentValidator.isFalse(this.ubicacion == ubicacion, "El producto \"" + numSerie + "\" ya esta en la ubicacion \"" + this.ubicacion.getNombre() + "\"");
        if (historicoUbicaciones == null) {
            historicoUbicaciones = new ArrayList();
        }
        historicoUbicaciones.add(
            new StockHistoricoUbicacion(this.ubicacion, this, this.ubicacionFecha, fechaCambio, this.ubicacionObservaciones)
        );
        this.ubicacion = ubicacion;
        this.ubicacionFecha = fechaCambio;
        this.ubicacionObservaciones = observaciones;
    }
    
    public void cambiarUbicacion(Ubicacion ubicacion, String observaciones) {
        cambiarUbicacion(ubicacion, new Date(), observaciones);
    }
    
    /*public void deshacerUltimoCambioUbicacion() {
        ArgumentValidator.isNotEmpty(historicoUbicaciones, "El producto no tiene ubicacion.");
        ArgumentValidator.isTrue(historicoUbicaciones.size() > 1, "No se puede eliminar la ultima ubicacion.");
        historicoUbicaciones.remove(historicoUbicaciones.size() - 1);
        historicoUbicaciones.get(historicoNumerosSerie.size() - 1).reabrir();
    }*/
    
    public void volverUltimaUbicacionHistorico(String observaciones) {
        ArgumentValidator.isNotNull(historicoUbicaciones, "El producto " + numSerie + " no tiene historico de ubicacion.");
        ArgumentValidator.isTrue(historicoUbicaciones.size() > 0, "El producto " + numSerie + " no puede volver a la ultima ubicacion del historico porque el historico esta vacio.");
        StockHistoricoUbicacion ultimoHistorico = historicoUbicaciones.get(historicoUbicaciones.size() - 1);
        cambiarUbicacion(ultimoHistorico.getUbicacion(), new Date(), observaciones);
    }
    
    public Contrato getContrato() {
        if (getDetalleContrato() == null) {
            return null;
        }
        return getDetalleContrato().getContrato();
    }
    
    public StockHistoricoContrato getDetalleContrato() {
        return detalleContrato;
    }

    public List<StockHistoricoContrato> getHistoricoContratos() {
        return historicoContratos;
    }
    
    public void cambiarContrato(Contrato contrato, Date fechaCambio, String observaciones) {
        if (historicoContratos == null) {
            historicoContratos = new ArrayList();
        }
        //finaliza el periodo actual, si es que tiene (REGLA: SOLO UN PERIODO ABIERTO PARA HISTORICOS)
        if (historicoContratos.size() > 0) {
            historicoContratos.get(historicoContratos.size() - 1).finalizar(fechaCambio);
        }
        historicoContratos.add(
            new StockHistoricoContrato(contrato, this, fechaCambio, observaciones)
        );
    }
    
    /*public void deshacerUltimoCambioContrato() {
        ArgumentValidator.isNotEmpty(historicoContratos, "El producto no tiene ubicacion.");
        ArgumentValidator.isTrue(historicoContratos.size() > 1, "No se puede eliminar la ultima ubicacion.");
        historicoContratos.remove(historicoContratos.size() - 1);
        historicoContratos.get(historicoNumerosSerie.size() - 1).reabrir();
    }*/
    
    
    


    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacion observaciones) {
        this.observaciones = observaciones;
    }
    
    private void assertCambioEstadoValido(StockEstado nuevoEstado, Date fechaEfectiva, Contrato contrato) {
        ArgumentValidator.isNotNull(nuevoEstado, "El nuevo estado no puede ser NULL.");
        switch (nuevoEstado.getId()) {
            case "BA":
            case "BL":
            case "RA":
            case "RV":
            case "VE":
                if (!getEstado().equals(StockEstado.DISPONIBLE)) {
                    throw new StockNoDisponibleModelException(this);
                }
                break;
            case "DI":
                ArgumentValidator.isTrue(
                    ((getEstado().equals(StockEstado.ALQUILADO))
                    || (getEstado().equals(StockEstado.BAJA))
                    || (getEstado().equals(StockEstado.BLOQUEADO))
                    || (getEstado().equals(StockEstado.RESERVADO_ALQUILER))
                    || (getEstado().equals(StockEstado.RESERVADO_VENTA))
                    || (getEstado().equals(StockEstado.VENDIDO)))
                    , "El producto no puede cambiar de " + getEstado().getNombre() + " a " + nuevoEstado.getNombre() + ".");
                break;
                
        }
    }
    
    public void baja() {
        baja(new Date(), null);
    }
    
    public void baja(Date fecha) {
        baja(fecha, null);
    }
    
    public void baja(Date fecha, String observaciones) {
        cambiarEstado(StockEstado.BAJA, fecha, observaciones);
    }
    
    public void reincorporarBaja() {
        reincorporarBaja(new Date(), "Reincorporar baja");
    }
    
    public void reincorporarBaja(Date fecha) {
        reincorporarBaja(fecha, "Reincorporar baja");
    }
    
    public void reincorporarBaja(Date fecha, String observaciones) {
        if (!getEstado().equals(StockEstado.BAJA)) {
            throw new NoEstaDadoDeBajaModelException(id.toString());
        }
        cambiarEstado(StockEstado.DISPONIBLE, fecha, observaciones);
    }
    
    public void bloquear() {
        bloquear(new Date(), null);
    }
    
    public void bloquear(Date fecha) {
        bloquear(fecha, null);
    }
    
    public void bloquear(Date fecha, String observaciones) {
        if (!getEstado().equals(StockEstado.DISPONIBLE)) {
            throw new StockNoDisponibleModelException(this);
        }
        cambiarEstado(StockEstado.BLOQUEADO, fecha, observaciones);
    }

    public void desbloquear() {
        desbloquear(new Date(), null);
    }
    
    public void desbloquear(Date fecha) {
        desbloquear(fecha, null);
    }
    
    public void desbloquear(Date fecha, String observaciones) {
        if (!getEstado().equals(StockEstado.BLOQUEADO)){
            throw new NoEstaBloqueadoModelException(id.toString());
        }
        cambiarEstado(StockEstado.DISPONIBLE, null, fecha, null);
    }
    
    public void reservarParaAlquiler(Contrato contrato, String observaciones) {
        if (!getEstado().equals(StockEstado.DISPONIBLE)) {
            throw new StockNoDisponibleModelException(this);
        }
        cambiarEstado(StockEstado.RESERVADO_ALQUILER, contrato, observaciones);
    }
    
    public void reservarParaVenta(Contrato contrato, String observaciones) {
        if (!getEstado().equals(StockEstado.DISPONIBLE)) {
            throw new StockNoDisponibleModelException(this);
        }
        cambiarEstado(StockEstado.RESERVADO_VENTA, contrato, observaciones);       
    }
    
    public void cancelarReservaComercialSiTiene(Contrato contrato, String observaciones) {
        if ((getEstado().equals(StockEstado.RESERVADO_ALQUILER)) || (getEstado().equals(StockEstado.RESERVADO_VENTA))) {
            volverUltimoEstadoHistorico("RESERVA CANCELADA");
        }
    }
    
    public void entregaAlquiler(Contrato contrato, Ubicacion ubicacionEnvio, String observaciones) {
        cambiarEstado(StockEstado.ALQUILADO, contrato, observaciones);
        cambiarUbicacion(ubicacionEnvio, observaciones);
    }
    
    public void cancelarEntregaAlquiler(Contrato contrato, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if ((!getEstado().equals(StockEstado.ALQUILADO)) || (!estadoContrato.equals(contrato))){
                throw new NoEstaAlquiladoModelException(this);
            }
            volverUltimoEstadoHistorico("ENTREGA CANCELADA");
            volverUltimaUbicacionHistorico("ENTREGA CANCELADA");
        } catch (Exception ex) {
            ex.printStackTrace();
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public void recogidaAlquiler(Contrato contrato, Ubicacion ubicacionDevolucion, String observaciones) {
        if (!getEstado().equals(StockEstado.ALQUILADO)) {
            throw new NoEstaAlquiladoModelException(this);
        }
        if ((estadoContrato != null) && (!estadoContrato.equals(contrato))) {
            throw new DomainException("No se puede efectuar la recogida de alquiler porque el contrato no coincide.");
        }
        cambiarEstado(StockEstado.DISPONIBLE, contrato, observaciones);
        cambiarUbicacion(ubicacionDevolucion, observaciones);
    }
    
    public void cancelarRecogidaAlquiler(Contrato contrato, String observaciones) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        try {
            if (!StockEstado.DISPONIBLE.equals(getEstado())) {
                throw new DomainException("No se puede cancelar la recogida de alquiler porque el stock dice esta " + (getEstado() != null ? getEstado().getNombre() : "SIN ESTADO"));
            }
         /*   if (Objects.equals(estadoContrato, contrato)) {
                throw new DomainException("No se puede cancelar la recogida de alquiler porque el contrato no coincide.");
            }*/
            volverUltimoEstadoHistorico("RECOGIDA CANCELADA");
            volverUltimaUbicacionHistorico("RECOGIDA CANCELADA");
        } catch (Exception ex) {
            DomainExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void entregaVenta(Contrato contrato, Ubicacion ubicacionEnvio, String observaciones) {
        if ( 
            (getEstado().equals(StockEstado.DISPONIBLE))
            ||
            ((getEstado().equals(StockEstado.RESERVADO_VENTA)) && (estadoContrato.equals(contrato)))
            ) {
            cambiarEstado(StockEstado.VENDIDO, contrato, observaciones);
            cambiarUbicacion(ubicacionEnvio, observaciones);
        } else {
            throw new StockNoDisponibleModelException(this);
        }
    }

    public void cancelarEntregaVenta(Contrato contrato, String observaciones) {
        if ((!getEstado().equals(StockEstado.VENDIDO)) && (estadoContrato.equals(contrato))) {
            throw new NoEstaVendidoModelException(id.toString());
        }
        volverUltimoEstadoHistorico("ENTREGA CANCELADA");
        volverUltimaUbicacionHistorico("ENTREGA CANCELADA");
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
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.Stock[ id=" + id + " ]";
    }

    public AlbaranEntrega getAlbaranEntrega() {
        return albaranEntrega;
    }

    public void setAlbaranEntrega(AlbaranEntrega albaranEntrega) {
        this.albaranEntrega = albaranEntrega;
    }

    public AlbaranRecogida getAlbaranRecogida() {
        return albaranRecogida;
    }

    public void setAlbaranRecogida(AlbaranRecogida albaranRecogida) {
        this.albaranRecogida = albaranRecogida;
    }

    
    

    
}