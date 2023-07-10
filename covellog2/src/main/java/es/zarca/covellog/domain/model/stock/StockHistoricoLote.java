package es.zarca.covellog.domain.model.stock;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "stock_historico_lote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoLote.findAll", query = "SELECT s FROM StockHistoricoLote s"),
    @NamedQuery(name = "StockHistoricoLote.findById", query = "SELECT s FROM StockHistoricoLote s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoLote.findByFechaInicio", query = "SELECT s FROM StockHistoricoLote s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoLote.findByFechaFin", query = "SELECT s FROM StockHistoricoLote s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoLote.findByLote", query = "SELECT s FROM StockHistoricoLote s WHERE s.lote = :lote")})
public class StockHistoricoLote extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 30)
    @Column(name = "lote")
    private String lote;
    
    public StockHistoricoLote() {
    }

    public StockHistoricoLote(String lote, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.lote = lote;
    }

    public StockHistoricoLote(String lote, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.lote = lote;
    }
    
    
    
    public String getLote() {
        return lote;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoLote[ id=" + getId() + " ]";
    }
    
}