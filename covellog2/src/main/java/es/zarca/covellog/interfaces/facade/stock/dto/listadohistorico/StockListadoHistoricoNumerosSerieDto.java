package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoNumeroSerieDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoNumerosSerieDto {
        private StockDto stock;
    private List<StockHistoricoNumeroSerieDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoNumeroSerieDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoNumeroSerieDto> historico) {
        this.historico = historico;
    }
    
}