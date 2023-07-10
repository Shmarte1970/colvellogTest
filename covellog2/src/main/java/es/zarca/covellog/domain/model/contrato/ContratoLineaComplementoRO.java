package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public interface ContratoLineaComplementoRO  {

    public Integer getId();
    public Integer getOrden();
    public TipoProducto getTipoProducto();
    public String getConcepto();
    public int getCantidad();
    public BigDecimal getImporte();
    
}
