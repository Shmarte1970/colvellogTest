package es.zarca.covellog.interfaces.facade.helpers.assemblers;

import es.zarca.covellog.domain.model.helpers.TimestampEntity;
import es.zarca.covellog.interfaces.facade.helpers.dto.TimestampEntityDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class TimestampEntityDtoAssembler {
    
    static public List<TimestampEntityDto> toDto(List<TimestampEntity> lista) {
        List<TimestampEntityDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public TimestampEntityDto toDto(TimestampEntity timestampEntity) {
        if (timestampEntity == null) {
            return null;
        }
        TimestampEntityDto dto = new TimestampEntityDto();
        dto.setCreatedDate(timestampEntity.getCreatedDate());
        dto.setUpdatedDate(timestampEntity.getUpdatedDate());
        return dto;
    }
    
}