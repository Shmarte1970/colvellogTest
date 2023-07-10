package es.zarca.covellog.domain.model.contrato;

/**
 *
 * @author francisco
 */
public class ContratoResumenTotales {
    private Double totalAlquiler;
    private Double totalComplementosAlquiler;
    private Double totalVenta;
    private Double totalComplementosVenta;
    private Double totalGastosAdicionales;
    private Double totalTransporte;

    public ContratoResumenTotales(Double totalAlquiler, Double totalComplementosAlquiler, Double totalVenta, Double totalComplementosVenta, Double totalGastosAdicionales, Double totalTransporte) {
        this.totalAlquiler = totalAlquiler;
        this.totalComplementosAlquiler = totalComplementosAlquiler;
        this.totalVenta = totalVenta;
        this.totalComplementosVenta = totalComplementosVenta;
        this.totalGastosAdicionales = totalGastosAdicionales;
        this.totalTransporte = totalTransporte;
    }

    public Double getTotalAlquiler() {
        return totalAlquiler;
    }

    public Double getTotalComplementosAlquiler() {
        return totalComplementosAlquiler;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public Double getTotalComplementosVenta() {
        return totalComplementosVenta;
    }

    public Double getTotalGastosAdicionales() {
        return totalGastosAdicionales;
    }

    public Double getTotalTransporte() {
        return totalTransporte;
    }
    
    
}
