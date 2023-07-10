package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.IdiomaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class IdiomaAssembler {
    
    static public IdiomaDto toDto(Idioma idioma) {
        if (idioma == null) {
            return null;
        }   
        IdiomaDto dto = new IdiomaDto();
        dto.setId(idioma.getId().getString());
        dto.setNombre(idioma.getNombre());
        return dto;
    }
    
    static public List<IdiomaDto> toDto(List<Idioma> idiomas) {
        if (idiomas == null) {
            return null;
        }
        List<IdiomaDto> dtos = new ArrayList<>();
        for (Idioma idioma : idiomas) {
            dtos.add(toDto(idioma));
        }
        return dtos;
    }
    
}
