package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.contrato.Contrato;
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
@Table(name = "stock_historico_contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoContrato.findAll", query = "SELECT s FROM StockHistoricoContrato s"),
    @NamedQuery(name = "StockHistoricoContrato.findById", query = "SELECT s FROM StockHistoricoContrato s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoContrato.findByFechaInicio", query = "SELECT s FROM StockHistoricoContrato s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoContrato.findByFechaFin", query = "SELECT s FROM StockHistoricoContrato s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoContrato.findByContrato", query = "SELECT s FROM StockHistoricoContrato s WHERE s.contrato = :contrato")})
public class StockHistoricoContrato extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne
    private Contrato contrato;
    
    public StockHistoricoContrato() {
    }

    public StockHistoricoContrato(Contrato contrato, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.contrato = contrato;
    }

    public StockHistoricoContrato(Contrato contrato, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.contrato = contrato;
    }
    
    public Contrato getContrato() {
        return contrato;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoContrato[ id=" + getId() + " ]";
    }
    
}