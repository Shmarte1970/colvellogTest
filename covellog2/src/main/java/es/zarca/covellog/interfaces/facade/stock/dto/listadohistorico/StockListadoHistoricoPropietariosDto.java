package es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoPropietarioDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoPropietariosDto {
    private StockDto stock;
    private List<StockHistoricoPropietarioDto> historico;

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public List<StockHistoricoPropietarioDto> getHistorico() {
        return historico;
    }

    public void setHistorico(List<StockHistoricoPropietarioDto> historico) {
        this.historico = historico;
    }
    
}