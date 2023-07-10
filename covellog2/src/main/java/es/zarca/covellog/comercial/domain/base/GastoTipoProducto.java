package es.zarca.covellog.comercial.domain.base;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author francisco
 */
@MappedSuperclass
public class GastoTipoProducto extends Gasto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;
    
    public GastoTipoProducto() {
    }

    public GastoTipoProducto(TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe, Integer orden) {
        super(concepto, cantidad, importe, orden);
        this.tipoProducto = tipoProducto;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
    @Override
    public String toString() {
        return "es.zarca.covellog.comercial.domain.base.GastoAdicional[ id=" + getId() + " ]";
    }
    
}
