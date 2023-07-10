package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public class ContratoLineaGastoAdicionalVO {

    private final Integer id;
    private final TipoProducto tipoProducto;
    private final String concepto;
    private final int cantidad;
    private final BigDecimal importe;
    
    public ContratoLineaGastoAdicionalVO(Integer id, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        this.id = id;
        this.tipoProducto = tipoProducto;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.importe = importe;
    }

    public Integer getId() {
        return id;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }
    
    public String getConcepto() {
        return concepto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getImporte() {
        return importe;
    }
    
}