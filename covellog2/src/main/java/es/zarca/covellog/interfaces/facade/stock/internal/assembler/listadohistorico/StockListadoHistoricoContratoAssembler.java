package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoContratoAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoContratosDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoContratoAssembler {
    
    static public List<StockListadoHistoricoContratosDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoContratosDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoContratosDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoContratosDto dto = new StockListadoHistoricoContratosDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoContratoAssembler.toDto(value.getHistoricoContratos()));
        return dto;
    }
    
}