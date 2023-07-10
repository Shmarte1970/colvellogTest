package es.zarca.covellog.interfaces.facade.stock.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoContratoDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.util.Date;

/**
 *
 * @author francisco
 */
public class StockDto extends StockMiniDto {
    private Date flotaFecha;
    private String flotaObservaciones;
    private Date estadoFecha;
    private Date estadoFechaEfectiva;
    private Integer estadoContratoId;
    private String estadoContratoFriendlyId;
    private String estadoObservaciones;
    private Date condicionFecha;
    private String condicionObservaciones;
    private UbicacionDto ubicacion;
    private Date ubicacionFecha;
    private String ubicacionObservaciones;
    private StockHistoricoContratoDto detalleContrato;
    private Integer propietarioId;
    private String propietario;
    private Date propietarioFecha;
    private String propietarioObservaciones;
    private DobleObservacionDto observaciones;


    public Date getFlotaFecha() {
        return flotaFecha;
    }

    public void setFlotaFecha(Date flotaFecha) {
        this.flotaFecha = flotaFecha;
    }

    public String getFlotaObservaciones() {
        return flotaObservaciones;
    }

    public void setFlotaObservaciones(String flotaObservaciones) {
        this.flotaObservaciones = flotaObservaciones;
    }
    
    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public void setEstadoFecha(Date estadoFecha) {
        this.estadoFecha = estadoFecha;
    }

    public Date getEstadoFechaEfectiva() {
        return estadoFechaEfectiva;
    }

    public void setEstadoFechaEfectiva(Date estadoFechaEfectiva) {
        this.estadoFechaEfectiva = estadoFechaEfectiva;
    }

    public Integer getEstadoContratoId() {
        return estadoContratoId;
    }

    public void setEstadoContratoId(Integer estadoContratoId) {
        this.estadoContratoId = estadoContratoId;
    }

    public String getEstadoContratoFriendlyId() {
        return estadoContratoFriendlyId;
    }

    public void setEstadoContratoFriendlyId(String estadoContratoFriendlyId) {
        this.estadoContratoFriendlyId = estadoContratoFriendlyId;
    }
    
    public String getEstadoObservaciones() {
        return estadoObservaciones;
    }

    public void setEstadoObservaciones(String estadoObservaciones) {
        this.estadoObservaciones = estadoObservaciones;
    }

    public Date getCondicionFecha() {
        return condicionFecha;
    }

    public void setCondicionFecha(Date condicionFecha) {
        this.condicionFecha = condicionFecha;
    }

    public String getCondicionObservaciones() {
        return condicionObservaciones;
    }

    public void setCondicionObservaciones(String condicionObservaciones) {
        this.condicionObservaciones = condicionObservaciones;
    }

    public UbicacionDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDto ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getUbicacionFecha() {
        return ubicacionFecha;
    }

    public void setUbicacionFecha(Date ubicacionFecha) {
        this.ubicacionFecha = ubicacionFecha;
    }

    public String getUbicacionObservaciones() {
        return ubicacionObservaciones;
    }

    public void setUbicacionObservaciones(String ubicacionObservaciones) {
        this.ubicacionObservaciones = ubicacionObservaciones;
    }
    
/*    public StockHistoricoUbicacionDto getDetalleUbicacion() {
        return detalleUbicacion;
    }

    public void setDetalleUbicacion(StockHistoricoUbicacionDto detalleUbicacion) {
        this.detalleUbicacion = detalleUbicacion;
    }*/

    public StockHistoricoContratoDto getDetalleContrato() {
        return detalleContrato;
    }

    public void setDetalleContrato(StockHistoricoContratoDto detalleContrato) {
        this.detalleContrato = detalleContrato;
    }

    public Integer getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Integer propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public Date getPropietarioFecha() {
        return propietarioFecha;
    }

    public void setPropietarioFecha(Date propietarioFecha) {
        this.propietarioFecha = propietarioFecha;
    }

    public String getPropietarioObservaciones() {
        return propietarioObservaciones;
    }

    public void setPropietarioObservaciones(String propietarioObservaciones) {
        this.propietarioObservaciones = propietarioObservaciones;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }


}
