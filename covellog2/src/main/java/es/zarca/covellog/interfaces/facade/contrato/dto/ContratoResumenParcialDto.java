package es.zarca.covellog.interfaces.facade.contrato.dto;

import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public class ContratoResumenParcialDto {
    private BigDecimal total;
    private BigDecimal totalBase;
    private BigDecimal totalComplementos;
    private BigDecimal totalGastosAdicionales;
    private BigDecimal totalTransporte;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalBase() {
        return totalBase;
    }

    public void setTotalBase(BigDecimal totalBase) {
        this.totalBase = totalBase;
    }

    public BigDecimal getTotalComplementos() {
        return totalComplementos;
    }

    public void setTotalComplementos(BigDecimal totalComplementos) {
        this.totalComplementos = totalComplementos;
    }

    public BigDecimal getTotalGastosAdicionales() {
        return totalGastosAdicionales;
    }

    public void setTotalGastosAdicionales(BigDecimal totalGastosAdicionales) {
        this.totalGastosAdicionales = totalGastosAdicionales;
    }

    public BigDecimal getTotalTransporte() {
        return totalTransporte;
    }

    public void setTotalTransporte(BigDecimal totalTransporte) {
        this.totalTransporte = totalTransporte;
    }

    
    
}
