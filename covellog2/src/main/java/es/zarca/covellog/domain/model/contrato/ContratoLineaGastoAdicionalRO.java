package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public interface ContratoLineaGastoAdicionalRO {
    
    public Integer getId();
    public Integer getOrden();
    public String getConcepto();
    public int getCantidad();
    public BigDecimal getImporte();
    public ContratoLinea getLinea();
    public TipoProducto getTipoProducto();
        
}
