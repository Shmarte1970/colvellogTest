package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.producto.TipoProducto;
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
@Table(name = "stock_historico_tipo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StockHistoricoTipoProducto.findAll", query = "SELECT s FROM StockHistoricoTipoProducto s"),
    @NamedQuery(name = "StockHistoricoTipoProducto.findById", query = "SELECT s FROM StockHistoricoTipoProducto s WHERE s.id = :id"),
    @NamedQuery(name = "StockHistoricoTipoProducto.findByFechaInicio", query = "SELECT s FROM StockHistoricoTipoProducto s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "StockHistoricoTipoProducto.findByFechaFin", query = "SELECT s FROM StockHistoricoTipoProducto s WHERE s.fechaFin = :fechaFin"),
    @NamedQuery(name = "StockHistoricoTipoProducto.findByTipoProducto", query = "SELECT s FROM StockHistoricoTipoProducto s WHERE s.tipoProducto = :tipoProducto")})
public class StockHistoricoTipoProducto extends StockHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne
    private TipoProducto tipoProducto;
    
    public StockHistoricoTipoProducto() {
    }

    public StockHistoricoTipoProducto(TipoProducto tipoProducto, Stock stock, Date fechaInicio, String observaciones) {
        super(stock, fechaInicio, observaciones);
        this.tipoProducto = tipoProducto;
    }

    public StockHistoricoTipoProducto(TipoProducto tipoProducto, Stock stock, Date fechaInicio, Date fechaFin, String observaciones) {
        super(stock, fechaInicio, fechaFin, observaciones);
        this.tipoProducto = tipoProducto;
    }
    
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.StockHistoricoTipoProducto[ id=" + getId() + " ]";
    }
    
}