package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoFlota;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoFlotaDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.FlotaAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoFlotaAssembler {
    
    static public List<StockHistoricoFlotaDto> toDto(List<StockHistoricoFlota> lista) {
        List<StockHistoricoFlotaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoFlotaDto toDto(StockHistoricoFlota value) {
        if (value == null) {
            return null;
        }
        StockHistoricoFlotaDto dto = new StockHistoricoFlotaDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setFlota(FlotaAssembler.toDto(value.getFlota()));
        return dto;
    }
    
}