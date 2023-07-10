package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoEstado;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoEstadoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.EstadoAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoEstadoAssembler {
    
    static public List<StockHistoricoEstadoDto> toDto(List<StockHistoricoEstado> lista) {
        List<StockHistoricoEstadoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoEstadoDto toDto(StockHistoricoEstado value) {
        if (value == null) {
            return null;
        }
        StockHistoricoEstadoDto dto = new StockHistoricoEstadoDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setEstado(EstadoAssembler.toDto(value.getEstado()));
        return dto;
    }
    
}