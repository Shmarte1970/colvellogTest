package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class StockHistoricoDto {
    private Date fechaInicio;
    private Date fechaFin;
    private String observaciones;

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}