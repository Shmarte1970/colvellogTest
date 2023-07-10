package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoNumeroSerieAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoNumerosSerieDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoNumerosSerieAssembler {
    
    static public List<StockListadoHistoricoNumerosSerieDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoNumerosSerieDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoNumerosSerieDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoNumerosSerieDto dto = new StockListadoHistoricoNumerosSerieDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoNumeroSerieAssembler.toDto(value.getHistoricoNumerosSerie()));
        return dto;
    }
    
}