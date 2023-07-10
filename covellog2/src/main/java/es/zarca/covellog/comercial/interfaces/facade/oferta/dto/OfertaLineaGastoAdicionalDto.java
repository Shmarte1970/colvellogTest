package es.zarca.covellog.comercial.interfaces.facade.oferta.dto;

import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public class OfertaLineaGastoAdicionalDto {
    private Integer id;
    private TipoProductoDto gastoAdicional;
    private String concepto;
    private int cantidad;
    private BigDecimal importe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoProductoDto getGastoAdicional() {
        return gastoAdicional;
    }

    public void setGastoAdicional(TipoProductoDto gastoAdicional) {
        this.gastoAdicional = gastoAdicional;
    }
    
    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
    public BigDecimal getImporteTotal() {
        return importe.multiply(new BigDecimal(cantidad));
    }

}