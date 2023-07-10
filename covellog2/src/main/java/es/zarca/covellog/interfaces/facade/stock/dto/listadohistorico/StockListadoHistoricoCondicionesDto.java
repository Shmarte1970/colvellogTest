package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoCondicionDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoCondicionesDto {
    private StockDto stock;
    private List<StockHistoricoCondicionDto> historico;
    
    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
}

    public List<StockHistoricoCondicionDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoCondicionDto> historico) {
        this.historico = historico;
    }

}