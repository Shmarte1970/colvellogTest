package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoLoteDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoLotesDto {
    private StockDto stock;
    private List<StockHistoricoLoteDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoLoteDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoLoteDto> historico) {
        this.historico = historico;
    }

}