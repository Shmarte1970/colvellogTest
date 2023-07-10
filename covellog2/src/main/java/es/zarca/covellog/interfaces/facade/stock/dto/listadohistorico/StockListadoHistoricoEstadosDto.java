package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoEstadoDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoEstadosDto {
    private StockDto stock;
    private List<StockHistoricoEstadoDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoEstadoDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoEstadoDto> historico) {
        this.historico = historico;
    }

}