package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.CondicionStock;
import es.zarca.covellog.interfaces.facade.stock.dto.CondicionDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class CondicionAssembler {
    
    static public List<CondicionDto> toDto(List<CondicionStock> lista) {
        List<CondicionDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public CondicionDto toDto(CondicionStock condicion) {
        if (condicion == null) {
            return null;
        }
        CondicionDto dto = new CondicionDto();
        dto.setId(condicion.getId());
        dto.setNombre(condicion.getNombre());
        return dto;
    }
    
}