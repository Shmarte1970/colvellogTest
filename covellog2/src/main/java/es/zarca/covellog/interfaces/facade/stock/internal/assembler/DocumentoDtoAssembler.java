package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.generic.DocumentoRO;
import es.zarca.covellog.interfaces.facade.stock.dto.DocumentoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class DocumentoDtoAssembler {
    
    static public List<DocumentoDto> toDto(List<? extends DocumentoRO> lista) {
        List<DocumentoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public DocumentoDto toDto(DocumentoRO documento) {
        if (documento == null) {
            return null;
        }
        DocumentoDto dto = new DocumentoDto();
        dto.setId(documento.getId());
        dto.setNombre(documento.getNombre());
        return dto;
    }

}