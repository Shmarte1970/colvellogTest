package es.zarca.covellog.comercial.interfaces.facade.base.internal.assembler;

import es.zarca.covellog.comercial.domain.base.Nombre;
import es.zarca.covellog.comercial.interfaces.facade.base.dto.NombreDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class NombreDtoAssembler {
    
    static public List<NombreDto> toDto(List<Nombre> lista) {        
        if (lista != null) {
            List<NombreDto> listaDto = new ArrayList<>(lista.size());
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
            return listaDto;
        }
        return new ArrayList<>(0);
    }
    
    static public NombreDto toDto(Nombre nombre) {
        if (nombre == null) {
            return null;
        }
        NombreDto dto = new NombreDto();
        dto.setId(nombre.getId());
        dto.setNombre(nombre.getNombre());
        return dto;
    }
    
}