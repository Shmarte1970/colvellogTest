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
@Table(name = "stock_historico_num_serie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoNumeroSerie.findAll", query = "SELECT s FROM StockHistoricoNumeroSerie s"),
    @NamedQuery(name = "StockHistoricoNumeroSerie.findById", query = "SELECT s FROM StockHistoricoNumeroSerie s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoNumeroSerie.findByFechaInicio", query = "SELECT s FROM StockHistoricoNumeroSerie s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoNumeroSerie.findByFechaFin", query = "SELECT s FROM StockHistoricoNumeroSerie s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoNumeroSerie.findByNumeroSerie", query = "SELECT s FROM StockHistoricoNumeroSerie s WHERE s.numeroSerie = :numeroSerie")})
public class StockHistoricoNumeroSerie extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 20)
    @Column(name = "num_serie")
    private String numeroSerie;
    
    public StockHistoricoNumeroSerie() {
    }

    public StockHistoricoNumeroSerie(String numeroSerie, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.numeroSerie = numeroSerie;
    }

    public StockHistoricoNumeroSerie(String numeroSerie, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.numeroSerie = numeroSerie;
    }
    
    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoNumeroSerie[ id=" + getId() + " ]";
    }
    
}