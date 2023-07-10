package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoLoteAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoLotesDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoLotesAssembler {
    
    static public List<StockListadoHistoricoLotesDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoLotesDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoLotesDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoLotesDto dto = new StockListadoHistoricoLotesDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoLoteAssembler.toDto(value.getHistoricoLotes()));
        return dto;
    }
    
}