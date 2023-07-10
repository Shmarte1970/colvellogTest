package es.zarca.covellog.interfaces.facade.comerciales.internal.assembler;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ComercialAssembler {
    
    static public ComercialDto toDto(Comercial comercial) {
        if (comercial == null) {
            return null;
        }   
        ComercialDto dto = new ComercialDto();
        dto.setId(comercial.getId());
        dto.setNombreCompleto(comercial.getNombreCompleto());
        dto.setUsername(comercial.getUsername());
        dto.setNombre(comercial.getNombre());
        dto.setApellidos(comercial.getApellidos());
        if (comercial.getCanalesContacto() != null) {
            dto.setEmail(comercial.getCanalesContacto().getEmail());
            dto.setTelefono(comercial.getCanalesContacto().getTelefono());
            dto.setTelefono2(comercial.getCanalesContacto().getTelefono2());
        }
        dto.setGrupos(comercial.getGrupos());
        return dto;
    }
    
    static public List<ComercialDto> toDto(List<Comercial> comerciales) {
        if (comerciales == null) {
            return null;
        }
        List<ComercialDto> dtos = new ArrayList<>();
        comerciales.forEach((comercial) -> {
            dtos.add(toDto(comercial));
        });
        return dtos;
    }
    
    static public List<Integer> modelToArrayId(List<Comercial> comerciales) {
        if (comerciales == null) {
            return null;
        }
        List<Integer> comercialesIds = new ArrayList<>();
        comerciales.forEach((comercial) -> {
            comercialesIds.add(comercial.getId());
        });
        return comercialesIds;
    }
    
    static public List<Integer> dtoToArrayId(List<ComercialDto> comerciales) {
        if (comerciales == null) {
            return null;
        }
        List<Integer> comercialesIds = new ArrayList<>();
        comerciales.forEach((comercial) -> {
            comercialesIds.add(comercial.getId());
        });
        return comercialesIds;
    }
    
}
