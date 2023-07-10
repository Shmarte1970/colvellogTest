package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

/**
 *
 * @author francisco
 */
public class DetalleContratacionDto {
    private boolean transporteEntregaAdelantado;
    private boolean transporteRecogidaAdelantado;
    private boolean montajeAdelantado;
    private boolean desmontajeAdelantado;
    private int mesesFianza;
    private TipoFacturacionDto tipoFacturacion;
    private FacturarPorDto facturarPor;
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

    public int getMesesFianza() {
        return mesesFianza;
    }

    public void setMesesFianza(int mesesFianza) {
        this.mesesFianza = mesesFianza;
    }

    public TipoFacturacionDto getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(TipoFacturacionDto tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public FacturarPorDto getFacturarPor() {
        return facturarPor;
    }

    public void setFacturarPor(FacturarPorDto facturarPor) {
        this.facturarPor = facturarPor;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }
    
}
