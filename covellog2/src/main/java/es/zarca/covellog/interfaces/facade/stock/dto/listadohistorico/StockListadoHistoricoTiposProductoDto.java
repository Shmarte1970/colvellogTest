package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoTipoProductoDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoTiposProductoDto {
    private StockDto stock;
    private List<StockHistoricoTipoProductoDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoTipoProductoDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoTipoProductoDto> historico) {
        this.historico = historico;
    }

}