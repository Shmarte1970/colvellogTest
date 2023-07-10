package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.StockEstado;
import es.zarca.covellog.interfaces.facade.stock.dto.EstadoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class EstadoAssembler {
    
    static public List<EstadoDto> toDto(List<StockEstado> lista) {
        List<EstadoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public EstadoDto toDto(StockEstado estado) {
        if (estado == null) {
            return null;
        }
        EstadoDto dto = new EstadoDto();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }
    
}