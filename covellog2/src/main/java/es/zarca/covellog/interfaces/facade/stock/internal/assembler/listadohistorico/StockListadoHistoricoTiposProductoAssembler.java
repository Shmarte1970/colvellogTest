package es.zarca.covellog.interfaces.facade.stock.internal.assembler.listadohistorico;

import es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico.StockHistoricoTipoProductoAssembler;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoTiposProductoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockListadoHistoricoTiposProductoAssembler {
    
    static public List<StockListadoHistoricoTiposProductoDto> toDto(List<Stock> lista) {
        List<StockListadoHistoricoTiposProductoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockListadoHistoricoTiposProductoDto toDto(Stock value) {
        if (value == null) {
            return null;
        }
        StockListadoHistoricoTiposProductoDto dto = new StockListadoHistoricoTiposProductoDto();
        dto.setStock(StockAssembler.toDto(value));
        dto.setHistorico(StockHistoricoTipoProductoAssembler.toDto(value.getHistoricoTiposProducto()));
        return dto;
    }
    
}