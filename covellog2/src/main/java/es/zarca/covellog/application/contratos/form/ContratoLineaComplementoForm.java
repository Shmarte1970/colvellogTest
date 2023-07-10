package es.zarca.covellog.application.contratos.form;

import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco
 */
public class ContratoLineaComplementoForm {    
    private Integer id;
    @NotNull
    private String complementoId;
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

    public String getComplementoId() {
        return complementoId;
    }

    public void setComplementoId(String complementoId) {
        this.complementoId = complementoId;
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