package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.stock.dto.CondicionDto;

/**
 *
 * @author francisco
 */
public class StockHistoricoCondicionDto extends StockHistoricoDto {
    private CondicionDto condicion;

    public CondicionDto getCondicion() {
        return condicion;
    }

    public void setCondicion(CondicionDto condicion) {
        this.condicion = condicion;
    }
    
}