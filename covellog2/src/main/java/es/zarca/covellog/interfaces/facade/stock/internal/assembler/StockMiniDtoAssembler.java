package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockMiniDtoAssembler {
    
    static public List<StockMiniDto> toDto(List<Stock> lista) {
        List<StockMiniDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockMiniDto toDto(Stock stock) {
        if (stock == null) {
            return null;
        }
        StockMiniDto dto = new StockMiniDto();
        dto.setId(stock.getId());
        dto.setNumSerie(stock.getNumeroSerie());
        dto.setTipoProducto(TipoProductoAssembler.toDto(stock.getTipoProducto()));
        dto.setLote(stock.getLote());
        dto.setFlota(FlotaAssembler.toDto(stock.getFlota()));
        dto.setEstado(EstadoAssembler.toDto(stock.getEstado()));
        dto.setCondicion(CondicionAssembler.toDto(stock.getCondicion()));
        dto.setAlbaranEntregaFriendlyId(stock.getAlbaranEntrega() == null ? "Sin Albaran" : stock.getAlbaranEntrega().getFriendlyId());
        dto.setAlbaranRecogidaFriendlyId(stock.getAlbaranRecogida() == null ? "Sin Albaran" : stock.getAlbaranRecogida().getFriendlyId());
        return dto;
    }
    
}