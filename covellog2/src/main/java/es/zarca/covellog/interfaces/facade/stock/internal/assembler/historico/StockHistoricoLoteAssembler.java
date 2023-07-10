package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoLote;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoLoteDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoLoteAssembler {
    
    static public List<StockHistoricoLoteDto> toDto(List<StockHistoricoLote> lista) {
        List<StockHistoricoLoteDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoLoteDto toDto(StockHistoricoLote value) {
        if (value == null) {
            return null;
        }
        StockHistoricoLoteDto dto = new StockHistoricoLoteDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setLote(value.getLote());
        return dto;
    }
    
}