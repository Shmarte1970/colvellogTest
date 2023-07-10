package es.zarca.covellog.interfaces.facade.base.internal.assembler;

import es.zarca.covellog.domain.model.base.Estado;
import es.zarca.covellog.interfaces.facade.base.dto.EstadoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class EstadoDtoAssembler {
    
    static public List<EstadoDto> toDto(List<Estado> lista) {
        List<EstadoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public EstadoDto toDto(Estado estado) {
        if (estado == null) {
            return null;
        }
        EstadoDto dto = new EstadoDto();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }
    
}