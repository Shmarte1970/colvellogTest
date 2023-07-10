package es.zarca.covellog.application.contratos.form;

/**
 *
 * @author francisco
 */
public class ContratoCondicionesForm {    
    private boolean transporteEntregaAdelantado;
    private boolean transporteRecogidaAdelantado;
    private boolean montajeAdelantado;
    private boolean desmontajeAdelantado;
    private Integer mesesFianza;
    private String tipoFacturacionId;
    private String facturarPor;
    private Integer descuento;

    public boolean isTransporteEntregaAdelantado() {
        return transporteEntregaAdelantado;
    }

    public void setTransporteEntregaAdelantado(boolean transporteEntregaAdelantado) {
        this.transporteEntregaAdelantado = transporteEntregaAdelantado;
    }

    public boolean isTransporteRecogidaAdelantado() {
        return transporteRecogidaAdelantado;
    }

    public void setTransporteRecogidaAdelantado(boolean transporteRecogidaAdelantado) {
        this.transporteRecogidaAdelantado = transporteRecogidaAdelantado;
    }

    public boolean isMontajeAdelantado() {
        return montajeAdelantado;
    }

    public void setMontajeAdelantado(boolean montajeAdelantado) {
        this.montajeAdelantado = montajeAdelantado;
    }

    public boolean isDesmontajeAdelantado() {
        return desmontajeAdelantado;
    }

    public void setDesmontajeAdelantado(boolean desmontajeAdelantado) {
        this.desmontajeAdelantado = desmontajeAdelantado;
    }

    public Integer getMesesFianza() {
        return mesesFianza;
    }

    public void setMesesFianza(Integer mesesFianza) {
        this.mesesFianza = mesesFianza;
    }

    public String getTipoFacturacionId() {
        return tipoFacturacionId;
    }

    public void setTipoFacturacionId(String tipoFacturacionId) {
        this.tipoFacturacionId = tipoFacturacionId;
    }

    public String getFacturarPor() {
        return facturarPor;
    }

    public void setFacturarPor(String facturarPor) {
        this.facturarPor = facturarPor;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

}
