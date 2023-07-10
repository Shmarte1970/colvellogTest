package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
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
@Table(name = "stock_historico_ubicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoUbicacion.findAll", query = "SELECT s FROM StockHistoricoUbicacion s"),
    @NamedQuery(name = "StockHistoricoUbicacion.findById", query = "SELECT s FROM StockHistoricoUbicacion s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoUbicacion.findByFechaInicio", query = "SELECT s FROM StockHistoricoUbicacion s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoUbicacion.findByFechaFin", query = "SELECT s FROM StockHistoricoUbicacion s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoUbicacion.findByUbicacion", query = "SELECT s FROM StockHistoricoUbicacion s WHERE s.ubicacion = :ubicacion")})
public class StockHistoricoUbicacion extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    @ManyToOne
    private Ubicacion ubicacion;

    public StockHistoricoUbicacion() {
    }

    public StockHistoricoUbicacion(Ubicacion ubicacion, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.ubicacion = ubicacion;
    }

    public StockHistoricoUbicacion(Ubicacion ubicacion, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.ubicacion = ubicacion;
    }
    
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoUbicacion[ id=" + getId() + " ]";
    }
    
}