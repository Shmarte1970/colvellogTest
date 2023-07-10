package es.zarca.covellog.domain.model.stock.reservas;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "reserva_linea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaLinea.findAll", query = "SELECT r FROM ReservaLinea r"),
    @NamedQuery(name = "ReservaLinea.findById", query = "SELECT r FROM ReservaLinea r WHERE r.id = :id"),
    @NamedQuery(name = "ReservaLinea.findByBooking", query = "SELECT r FROM ReservaLinea r WHERE r.booking = :booking"),
    @NamedQuery(name = "ReservaLinea.findByLote", query = "SELECT r FROM ReservaLinea r WHERE r.lote = :lote"),
    @NamedQuery(name = "ReservaLinea.findByNumSerie", query = "SELECT r FROM ReservaLinea r WHERE r.numSerie = :numSerie")})
public class ReservaLinea implements ReservaLineaRO, Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "booking")
    private String booking;
    @Size(max = 50)
    @Column(name = "lote")
    private String lote;
    @Size(max = 50)
    @Column(name = "num_serie")
    private String numSerie;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "stock_id", referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private Stock stock;
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;
    @JoinColumn(name = "reserva_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Reserva reserva;
    @JoinColumn(name = "movimiento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Movimiento movimiento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservaLinea", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<ReservaLineaCheckList> checkList;    

    public ReservaLinea() {
    }

    public ReservaLinea(Reserva reserva, String booking, Stock stock, List<String> checkList) {
        this.reserva = reserva;
        this.booking = booking;
        this.stock = stock;
        if (stock != null) {
            tipoProducto = stock.getTipoProducto();
            lote = stock.getLote();
            numSerie = stock.getNumeroSerie();
        }
        setCheckList(checkList);
    }
    
    public ReservaLinea(Reserva reserva, String booking, TipoProducto tipoProducto, String lote, List<String> checkList) {
        this.reserva = reserva;
        this.booking = booking;
        this.tipoProducto = tipoProducto;
        this.lote = lote;
        setCheckList(checkList);
    }
    
    public void modificar(Stock stock, List<String> checkList) {
        this.stock = stock;
        setCheckList(checkList);
        if (stock != null) {
            tipoProducto = stock.getTipoProducto();
            lote = stock.getLote();
            numSerie = stock.getNumeroSerie();
        } else {
            tipoProducto = null;
            lote = null;
            numSerie = null;
        }
    }
    
    public void modificar(TipoProducto tipoProducto, String lote, List<String> checkList) {
        stock = null;
        numSerie = null;
        this.tipoProducto = tipoProducto;
        this.lote = lote;
        setCheckList(checkList);
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getBooking() {
        return booking;
    }

    @Override
    public String getLote() {
        return lote;
    }

    private void setLote(String lote) {
        this.lote = lote;
    }
    
    @Override
    public Stock getStock() {
        return stock;
    }

    private void setStock(Stock stock) {
        this.stock = stock;
        numSerie = stock.getNumeroSerie();
        tipoProducto = stock.getTipoProducto();
        lote = stock.getLote();
    }

    @Override
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    private void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String getNumSerie() {
        return numSerie;
    }

    @Override
    public Reserva getReserva() {
        return reserva;
    }

    @Override
    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    @Override
    public String getNumSerieAsignado() {
        if (movimiento == null) {
            return null;
        }
        return movimiento.getNumSerieAsignado(booking);
    }

    public Date getFechaEntrega() {
        if (movimiento == null) {
            return null;
        }
        return movimiento.getFecha();
    }
    
    public final void setCheckList(List<String> checkList) {
        this.checkList = new ArrayList();
        Integer index = 0;
        for (String desc : checkList) {
            this.checkList.add(new ReservaLineaCheckList(this, index, desc));
            index++;
        }
    }

    @Override
    public List<String> getCheckList() {
        List<String> result = new ArrayList();
        for (ReservaLineaCheckList item : checkList) {
            result.add(item.getDescripcion());
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
        if (!(object instanceof ReservaLinea)) {
            return false;
        }
        ReservaLinea other = (ReservaLinea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.reservas.ReservaLinea[ id=" + id + " ]";
    }

    

    

}
