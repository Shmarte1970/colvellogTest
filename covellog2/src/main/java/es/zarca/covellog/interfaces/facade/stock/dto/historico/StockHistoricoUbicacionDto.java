package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;

/**
 *
 * @author francisco
 */
public class StockHistoricoUbicacionDto extends StockHistoricoDto {
    private UbicacionDto ubicacion;

    public UbicacionDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDto ubicacion) {
        this.ubicacion = ubicacion;
    }
    
}