package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoCondicion;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoCondicionDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.CondicionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoCondicionAssembler {
    
    static public List<StockHistoricoCondicionDto> toDto(List<StockHistoricoCondicion> lista) {
        List<StockHistoricoCondicionDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoCondicionDto toDto(StockHistoricoCondicion value) {
        if (value == null) {
            return null;
        }
        StockHistoricoCondicionDto dto = new StockHistoricoCondicionDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setCondicion(CondicionAssembler.toDto(value.getCondicion()));
        return dto;
    }
    
}