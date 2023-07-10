package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Tags;
import es.zarca.covellog.domain.model.empresa.Empresa;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "stock_historico_propietario")
@XmlRootElement
public class StockHistoricoPropietario extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "propietario_id", referencedColumnName = "id")
    @ManyToOne
    private Empresa propietario;
    
    @Embedded
    private Tags tags;
    
    public StockHistoricoPropietario() {
    }

    public StockHistoricoPropietario(Empresa propietario, Tags tags, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.propietario = propietario;
        this.tags = tags;
    }
    
    public StockHistoricoPropietario(Empresa propietario, Tags tags, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.propietario = propietario;
        this.tags = tags;
    }

    public Empresa getPropietario() {
        return propietario;
    }

    public Tags getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoPropietario[ id=" + getId() + " ]";
    }
    
}