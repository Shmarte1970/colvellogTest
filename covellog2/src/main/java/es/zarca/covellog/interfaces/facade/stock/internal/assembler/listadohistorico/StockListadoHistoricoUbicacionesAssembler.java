package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoUbicacionAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoUbicacionesDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoUbicacionesAssembler {
    
    static public List<StockListadoHistoricoUbicacionesDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoUbicacionesDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoUbicacionesDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoUbicacionesDto dto = new StockListadoHistoricoUbicacionesDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoUbicacionAssembler.toDto(value.getHistoricoUbicaciones()));
        return dto;
    }
    
}