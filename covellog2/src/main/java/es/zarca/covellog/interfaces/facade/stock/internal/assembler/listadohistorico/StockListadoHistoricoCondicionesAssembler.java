package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoCondicionAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoCondicionesDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoCondicionesAssembler {
    
    static public List<StockListadoHistoricoCondicionesDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoCondicionesDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoCondicionesDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoCondicionesDto dto = new StockListadoHistoricoCondicionesDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoCondicionAssembler.toDto(value.getHistoricoCondiciones()));
        return dto;
    }
    
}