package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoFlotaDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoFlotasDto {
    private StockDto stock;
    private List<StockHistoricoFlotaDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoFlotaDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoFlotaDto> historico) {
        this.historico = historico;
    }
    
}