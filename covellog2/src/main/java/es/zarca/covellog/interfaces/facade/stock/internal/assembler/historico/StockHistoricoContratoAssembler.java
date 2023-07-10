package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoContrato;
import es.zarca.covellog.interfaces.facade.contrato.internal.assembler.ContratoDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoContratoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoContratoAssembler {
    
    static public List<StockHistoricoContratoDto> toDto(List<StockHistoricoContrato> lista) {
        List<StockHistoricoContratoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoContratoDto toDto(StockHistoricoContrato value) {
        if (value == null) {
            return null;
        }
        StockHistoricoContratoDto dto = new StockHistoricoContratoDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setContrato(ContratoDtoAssembler.toDto(value.getContrato()));
        return dto;
    }
    
}