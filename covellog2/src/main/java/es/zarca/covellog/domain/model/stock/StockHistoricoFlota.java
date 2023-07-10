package es.zarca.covellog.domain.model.stock;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "stock_historico_flota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoFlota.findAll", query = "SELECT s FROM StockHistoricoFlota s"),
    @NamedQuery(name = "StockHistoricoFlota.findById", query = "SELECT s FROM StockHistoricoFlota s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoFlota.findByFechaInicio", query = "SELECT s FROM StockHistoricoFlota s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoFlota.findByFechaFin", query = "SELECT s FROM StockHistoricoFlota s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoFlota.findByFlota", query = "SELECT s FROM StockHistoricoFlota s WHERE s.flota = :flota")})
public class StockHistoricoFlota extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "flota_id", referencedColumnName = "id")
    @ManyToOne
    private Flota flota;
    
    public StockHistoricoFlota() {
    }

    public StockHistoricoFlota(Flota flota, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.flota = flota;
    }

    public StockHistoricoFlota(Flota flota, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.flota = flota;
    }
    
    public Flota getFlota() {
        return flota;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoFlota[ id=" + getId() + " ]";
    }
    
}