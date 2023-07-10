package es.zarca.covellog.application.contratos.form;

import java.math.BigDecimal;

/**
 *
 * @author francisco
 */
public class TransporteConPrecioForm {    
    private Integer transporteId;
    private BigDecimal importe;

    public TransporteConPrecioForm(Integer transporteId, BigDecimal importe) {
        this.transporteId = transporteId;
        this.importe = importe;
    }

    public Integer getTransporteId() {
        return transporteId;
    }

    public void setTransporteId(Integer transporteId) {
        this.transporteId = transporteId;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }
    
}