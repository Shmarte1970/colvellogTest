package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoContratoDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoContratosDto {
    private StockDto stock;
    private List<StockHistoricoContratoDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoContratoDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoContratoDto> historico) {
        this.historico = historico;
    }

}