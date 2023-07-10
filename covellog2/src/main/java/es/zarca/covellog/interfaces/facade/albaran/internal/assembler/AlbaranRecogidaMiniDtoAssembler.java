package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranRecogidaMiniDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranRecogidaMiniDtoAssembler {
    
    static public List<AlbaranRecogidaMiniDto> toDto(List<? extends AlbaranRecogida> lista) {
        List<AlbaranRecogidaMiniDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranRecogidaMiniDto toDto(AlbaranRecogida albaran) {
        if (albaran == null) {
            return null;
        }
        AlbaranRecogidaMiniDto dto = new AlbaranRecogidaMiniDto();
        dto.setId(albaran.getId());
        dto.setCodigoAlbaran(albaran.getFriendlyId());
        dto.setFecha(albaran.getFecha());
        return dto;
    }
    
}