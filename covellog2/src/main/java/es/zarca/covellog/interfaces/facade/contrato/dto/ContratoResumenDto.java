package es.zarca.covellog.interfaces.facade.contrato.dto;

import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public class ContratoResumenDto {
    private ContratoResumenParcialDto resumenAlquiler;
    private ContratoResumenParcialDto resumenVenta;
    private ContratoResumenParcialDto resumenTotal;
    private Integer estimacionMesesAlquiler;

    public ContratoResumenParcialDto getResumenAlquiler() {
        return resumenAlquiler;
    }

    public void setResumenAlquiler(ContratoResumenParcialDto resumenAlquiler) {
        this.resumenAlquiler = resumenAlquiler;
    }

    public ContratoResumenParcialDto getResumenVenta() {
        return resumenVenta;
    }

    public void setResumenVenta(ContratoResumenParcialDto resumenVenta) {
        this.resumenVenta = resumenVenta;
    }

    public ContratoResumenParcialDto getResumenTotal() {
        return resumenTotal;
    }

    public void setResumenTotal(ContratoResumenParcialDto resumenTotal) {
        this.resumenTotal = resumenTotal;
    }

    public Integer getEstimacionMesesAlquiler() {
        return estimacionMesesAlquiler;
    }

    public void setEstimacionMesesAlquiler(Integer estimacionMesesAlquiler) {
        this.estimacionMesesAlquiler = estimacionMesesAlquiler;
    }
    
}
