package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public interface IGastoTipoProducto {
    TipoProducto getTipoProducto();
    String getConcepto();
    int getCantidad();
    BigDecimal getImporte();
    Integer getOrden();
}
