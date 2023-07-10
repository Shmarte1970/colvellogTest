package es.zarca.covellog.application.stock.form;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import java.util.Date;

/**
 *
 * @author francisco
 */
public class ModificarStockForm {    
    private Date fecha;
    private String tipoProductoId;
    private String lote;
    private String numeroSerie;
    private DobleObservacionForm observaciones;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    public String getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(String tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }

}
