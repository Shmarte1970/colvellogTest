package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranEstado;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranMiniDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranMiniDtoAssembler {
    
    static public List<AlbaranMiniDto> toDto(List<? extends Albaran> lista) {
        List<AlbaranMiniDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranMiniDto toDto(Albaran albaran) {
        if (albaran == null) {
            return null;
        }
        AlbaranMiniDto dto = new AlbaranMiniDto();
        dto.setId(albaran.getId());
        dto.setCodigoAlbaran(albaran.getFriendlyId());
        dto.setFecha(albaran.getFecha());
        dto.setTipo(AlbaranTipoDtoAssembler.toDto(albaran.getTipo()));
        dto.setEstado(albaran.getEstado().getNombre());
        return dto;
    }
    
}