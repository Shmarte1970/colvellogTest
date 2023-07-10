package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoUbicacionDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoUbicacionesDto {
    private StockDto stock;
    private List<StockHistoricoUbicacionDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoUbicacionDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoUbicacionDto> historico) {
        this.historico = historico;
    }

}