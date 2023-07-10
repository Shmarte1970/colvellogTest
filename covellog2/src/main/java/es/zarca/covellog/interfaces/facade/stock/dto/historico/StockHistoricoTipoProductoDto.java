package es.zarca.covellog.interfaces.facade.stock.dto.historico;

import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;

/**
 *
 * @author francisco
 */
public class StockHistoricoTipoProductoDto extends StockHistoricoDto {
    private TipoProductoDto tipoProducto;

    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
}