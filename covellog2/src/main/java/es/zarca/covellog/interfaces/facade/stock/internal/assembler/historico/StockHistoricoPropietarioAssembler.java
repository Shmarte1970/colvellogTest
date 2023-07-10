package es.zarca.covellog.interfaces.facade.stock.internal.assembler.historico;

import es.zarca.covellog.domain.model.stock.StockHistoricoPropietario;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.dto.historico.StockHistoricoPropietarioDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockHistoricoPropietarioAssembler {
    
    static public List<StockHistoricoPropietarioDto> toDto(List<StockHistoricoPropietario> lista) {
        List<StockHistoricoPropietarioDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockHistoricoPropietarioDto toDto(StockHistoricoPropietario value) {
        if (value == null) {
            return null;
        }
        StockHistoricoPropietarioDto dto = new StockHistoricoPropietarioDto();
        dto.setFechaInicio(value.getFechaInicio());
        dto.setFechaFin(value.getFechaFin());
        dto.setObservaciones(value.getObservaciones());
        dto.setPropietario(EmpresaMiniDtoAssembler.toDto(value.getPropietario()));
        dto.setTags(value.getTags() == null ? null : value.getTags().getString());
        return dto;
    }
    
}