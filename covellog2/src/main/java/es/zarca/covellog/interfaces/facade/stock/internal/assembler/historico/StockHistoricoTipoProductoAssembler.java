package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoTipoProducto;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoTipoProductoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoTipoProductoAssembler {
    
    static public List<StockHistoricoTipoProductoDto> toDto(List<StockHistoricoTipoProducto> lista) {
        List<StockHistoricoTipoProductoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoTipoProductoDto toDto(StockHistoricoTipoProducto value) {
        if (value == null) {
            return null;
        }
        StockHistoricoTipoProductoDto dto = new StockHistoricoTipoProductoDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setTipoProducto(TipoProductoAssembler.toDto(value.getTipoProducto()));
        return dto;
    }
    
}