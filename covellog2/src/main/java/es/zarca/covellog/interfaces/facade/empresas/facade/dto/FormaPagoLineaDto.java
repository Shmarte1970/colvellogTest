package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco
 */
public class FormaPagoLineaDto {
    
    private Integer numLinea;
    @NotNull
    @Min(1)
    @Max(100)
    private Integer porcentaje;
    private TipoPago tipoPago;
    private String cuenta;
    private TipoVencimiento tipoVencimiento;
    @Min(1)
    @Max(31)
    private Integer diaPago;

    public FormaPagoLineaDto() {
    }

    public FormaPagoLineaDto(Integer numLinea, Integer porcentaje, TipoPago tipoPago, String cuenta, TipoVencimiento tipoVencimiento, Integer diaPago) {
        this.numLinea = numLinea;
        this.porcentaje = porcentaje;
        this.tipoPago = tipoPago;
        this.cuenta = cuenta;
        this.tipoVencimiento = tipoVencimiento;
        this.diaPago = diaPago;
    }

    public Integer getNumLinea() {
        return numLinea;
    }

    public void setNumLinea(Integer numLinea) {
        this.numLinea = numLinea;
    }
    
    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public TipoVencimiento getTipoVencimiento() {
        return tipoVencimiento;
    }

    public void setTipoVencimiento(TipoVencimiento tipoVencimiento) {
        this.tipoVencimiento = tipoVencimiento;
    }

    public Integer getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Integer diaPago) {
        this.diaPago = diaPago;
    }

    @Override
    public String toString() {
        return "FormaPagoLineaDto{" + "porcentaje=" + porcentaje + ", tipoPago=" + tipoPago + ", cuenta=" + cuenta + ", tipoVencimiento=" + tipoVencimiento + ", diaPago=" + diaPago + '}';
    }
    
}
