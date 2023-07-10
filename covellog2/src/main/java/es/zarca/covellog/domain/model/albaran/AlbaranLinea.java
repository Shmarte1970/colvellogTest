package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "albaran_linea")
@XmlRootElement
public class AlbaranLinea implements IAlbaranLinea,Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "albaran_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Albaran albaran;
    @Basic(optional = false)
    @Column(name = "orden")
    private int orden;
    @Column(name = "booking")
    private String booking;
    @Column(name = "num_serie")
    private String numSerie;
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne
    private TipoProducto tipoProducto;
    @Basic(optional = true)
    @Size(max = 30)
    @Column(name = "lote")
    private String lote;
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock stock;
    /*@JoinColumn(name = "asignacion_stock_id", referencedColumnName = "id")
    @ManyToOne
    private Stock asignacionStock;*/
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_salida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalida;
    @Column(name = "fecha_llegada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLlegada;
    @JoinColumn(name = "reserva_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Reserva reserva;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albaranLinea", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<AlbaranLineaCheckList> checkList;
    
    public AlbaranLinea() {
    }

    public AlbaranLinea(Integer id) {
        this.id = id;
    }
    
    public final void setCheckList(List<String> checkList) {
        this.checkList = new ArrayList();
        Integer index = 0;
        for (String desc : checkList) {
            this.checkList.add(new AlbaranLineaCheckList(this, index, desc));
            index++;
        }
    }

    public AlbaranLinea(Albaran albaran, int orden, String booking, Stock stock, String descripcion, List<String> checkList) {
        this.albaran = albaran;
        this.booking = booking;
        this.tipoProducto = stock.getTipoProducto();
        this.lote = stock.getLote();
        if (stock != null) {
           this.numSerie = stock.getNumeroSerie(); 
        }
        this.stock = stock;
        this.descripcion = descripcion;
        this.orden = orden;
        setCheckList(checkList);
    }
    
    public AlbaranLinea(Albaran albaran, int orden, String booking, TipoProducto tipoProducto, String lote, String descripcion, List<String> checkList) {
        this.albaran = albaran;
        this.booking = booking;
        this.tipoProducto = tipoProducto;
        this.lote = lote;
        this.descripcion = descripcion;
        this.orden = orden;
        setCheckList(checkList);
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    public Albaran getAlbaran() {
        return albaran;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    @Override
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    
    @Override
    public Stock getStock() {
        return stock;
    }

    public void asignarStock(Stock stock) {
        System.err.println("COJONES asignarStock ");
        if (!Objects.equals(this.stock, stock)) {        
            System.err.println("COJONES asignarStock diferentes");
            ArgumentValidator.isNull(fechaSalida, "No se puede asignar un producto a una linea de albaran que ya a sido entregada.");            
            ArgumentValidator.isNotNull(albaran.getOrigen(), "Asigne un origen al albaran \"" + albaran.getFriendlyId() + "\"");
            if (stock != null) {
                ArgumentValidator.isTrue(
                    albaran.getOrigen().equals(stock.getUbicacion()), 
                    "No puede anadir el producto \"" + stock.getNumeroSerie() + 
                    "\" al albaran \"" + albaran.getFriendlyId() + 
                    "\" porque el albaran es para el origen \"" + albaran.getOrigen().getNombre() + 
                    "\" y el producto esta en \"" + stock.getUbicacion().getNombre() + "\"."
                );
            }
            this.stock = stock;
            System.err.println("COJONES asignarStock ASIGNADO " + (this.stock == null ? "NULO" : this.stock.getNumeroSerie()));
            if (stock != null) {
                numSerie = stock.getNumeroSerie();
                tipoProducto = stock.getTipoProducto();
                lote = stock.getLote();
            } else {
                numSerie = null;
                tipoProducto = null;
                lote = null;
            }
        } else {
            System.err.println("COJONES asignarStock IGUALES???????????????");
        }
    }
    

    @Override
    public Stock getAsignacionStock() {
        if (reserva != null) {
            return reserva.getStockMovimiento(booking);
        }
        return null;
    }
  
    @Override
    public String getBooking() {
        return booking;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (!Objects.equals(this.descripcion, descripcion)) {
            ArgumentValidator.isNull(fechaSalida, "No se puede modificar la descripcion de una linea de albaran que ya a sido entregada.");  
            ArgumentValidator.isTrue(AlbaranEstado.BORRADOR.equals(albaran.getEstado()), "Solo se puede modificar lineas a albaranes en estado BORRADOR.");
            this.descripcion = descripcion;
        }
    }
    
    @Override
    public List<String> getCheckList() {
        List<String> result = new ArrayList();
        for (AlbaranLineaCheckList item : checkList) {
            result.add(item.getDescripcion());
        }
        return result;
    }

    @Override
    public Date getFechaEntrega() {
        if (reserva != null) {
            return reserva.getFechaEntrega(booking);
        }
        return null;
    }

    @Override
    public Date getFechaSalida() {
        return fechaSalida;
    }

    @Override
    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    @Override
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    
    void entregar(Date fecha) {
        if (fechaSalida == null) {
            fechaSalida = fecha;
        }
        fechaLlegada = fecha;        
    }
    
    void anularMovimiento() {
        fechaSalida = null;
        fechaLlegada = null;
        if (albaran.getContrato() != null) {
            if (AlbaranTipo.ENTREGA.equals(albaran.getTipo())) {
                albaran.getContrato().onMovimientoEntregaAnulado(booking);
            } else {
                albaran.getContrato().onMovimientoRecogidaAnulado(booking);
            }
        }
    }
    
    void marcarSalida(Date fecha) {
        fechaSalida = fecha;
    }

    void desmarcarSalida() {
        fechaSalida = null;
    }

    void marcarLlegada(Date fecha) {
        fechaLlegada = fecha;
    }

    void desmarcarLlegada() {
        fechaLlegada = null;
    }

    void asignarReserva(Reserva reserva) {
        this.reserva = reserva;
    }

   /* public void setAsignacionStock(Stock asignacionStock) {
        this.asignacionStock = asignacionStock;
    }*/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlbaranLinea)) {
            return false;
        }
        AlbaranLinea other = (AlbaranLinea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.albaran.AlbaranLinea[ id=" + id + " ]";
    }

    void anularReserva() {
        ArgumentValidator.isNotNull(reserva, "No hay ninguna reserva que anular.");
        reserva.anular();
    }

    
}