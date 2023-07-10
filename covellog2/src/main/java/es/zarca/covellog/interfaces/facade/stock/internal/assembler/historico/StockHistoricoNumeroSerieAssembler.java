package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoNumeroSerie;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoNumeroSerieDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoNumeroSerieAssembler {
    
    static public List<StockHistoricoNumeroSerieDto> toDto(List<StockHistoricoNumeroSerie> lista) {
        List<StockHistoricoNumeroSerieDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoNumeroSerieDto toDto(StockHistoricoNumeroSerie value) {
        if (value == null) {
            return null;
        }
        StockHistoricoNumeroSerieDto dto = new StockHistoricoNumeroSerieDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setNumSerie(value.getNumeroSerie());
        return dto;
    }
    
}