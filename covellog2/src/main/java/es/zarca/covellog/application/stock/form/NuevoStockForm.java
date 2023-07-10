package es.zarca.covellog.application.stock.form;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import java.util.List;

/**
 *
 * @author francisco
 */
public class NuevoStockForm {
    private String tipoProductoId;
    private Integer almacenId;
    private String lote;
    private List<String> numerosSerie;
    private Integer propietarioId;
    private String flotaId;
    private String estadoId;
    private String condicionId;
    private DobleObservacionForm observaciones;

    public String getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(String tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public Integer getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Integer almacenId) {
        this.almacenId = almacenId;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public List<String> getNumerosSerie() {
        return numerosSerie;
    }

    public void setNumerosSerie(List<String> numerosSerie) {
        this.numerosSerie = numerosSerie;
    }

    public Integer getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Integer propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getFlotaId() {
        return flotaId;
    }

    public void setFlotaId(String flotaId) {
        this.flotaId = flotaId;
    }

    public String getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(String estadoId) {
        this.estadoId = estadoId;
    }

    public String getCondicionId() {
        return condicionId;
    }

    public void setCondicionId(String condicionId) {
        this.condicionId = condicionId;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }

}
