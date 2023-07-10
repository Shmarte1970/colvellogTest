package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.domain.model.albaran.AlbaranTipo;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranTipoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranTipoDtoAssembler {
    
    static public List<AlbaranTipoDto> toDto(List<AlbaranTipo> lista) {
        List<AlbaranTipoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranTipoDto toDto(AlbaranTipo albaranTipo) {
        if (albaranTipo == null) {
            return null;
        }
        AlbaranTipoDto dto = new AlbaranTipoDto();
        dto.setId(albaranTipo.getId());
        dto.setNombre(albaranTipo.getNombre());
        return dto;
    }
    
}