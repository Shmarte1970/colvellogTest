package es.zarca.covellog.application.contratos.form;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco
 */
public class ContratoLineaGastoAdicionalForm {
    private Integer id;
    @NotNull
    private String gastoAdicionalId;
    @NotEmpty
    private String concepto;
    @NotNull
    private BigDecimal importe;
    @NotNull    
    private Integer cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getGastoAdicionalId() {
        return gastoAdicionalId;
    }

    public void setGastoAdicionalId(String gastoAdicionalId) {
        this.gastoAdicionalId = gastoAdicionalId;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    
    
}