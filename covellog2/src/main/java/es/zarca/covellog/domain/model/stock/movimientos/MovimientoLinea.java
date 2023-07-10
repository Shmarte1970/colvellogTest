package es.zarca.covellog.domain.model.stock.movimientos;

import es.zarca.covellog.domain.model.stock.Stock;
import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "movimiento_linea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimientoLinea.findAll", query = "SELECT m FROM MovimientoLinea m"),
    @NamedQuery(name = "MovimientoLinea.findById", query = "SELECT m FROM MovimientoLinea m WHERE m.id = :id")
})
public class MovimientoLinea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="movimiento_id")
    private Movimiento movimiento;
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "booking")
    private String booking;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="stock_id")
    private Stock stock;
  
    public MovimientoLinea() {
    }

    public MovimientoLinea(Movimiento movimiento, String booking, Stock stock) {
        this.movimiento = movimiento;
        this.booking = booking;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}