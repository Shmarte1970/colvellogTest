package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.stock.dto.FlotaDto;

/**
 *
 * @author francisco
 */
public class StockHistoricoFlotaDto extends StockHistoricoDto {
    private FlotaDto flota;

    public FlotaDto getFlota() {
        return flota;
    }

    public void setFlota(FlotaDto flota) {
        this.flota = flota;
    }
    
}