package es.zarca.covellog.domain.model.stock;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "stock_historico_condicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoCondicion.findAll", query = "SELECT s FROM StockHistoricoCondicion s"),
    @NamedQuery(name = "StockHistoricoCondicion.findById", query = "SELECT s FROM StockHistoricoCondicion s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoCondicion.findByFechaInicio", query = "SELECT s FROM StockHistoricoCondicion s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoCondicion.findByFechaFin", query = "SELECT s FROM StockHistoricoCondicion s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoCondicion.findByCondicion", query = "SELECT s FROM StockHistoricoCondicion s WHERE s.condicion = :condicion")})
public class StockHistoricoCondicion extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "condicion_id")
    private CondicionStock condicion;
    
    public StockHistoricoCondicion() {
    }

    public StockHistoricoCondicion(CondicionStock condicion, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.condicion = condicion;
    }
    
    public StockHistoricoCondicion(CondicionStock condicion, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.condicion = condicion;
    }

    public CondicionStock getCondicion() {
        return condicion; //CondicionStock.fromId(condicion);
    }

    @Override
    public String toString() {
        return "StockHistoricoCondicion{" + "id=" + getId() + '}';
    }
    
}