package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.ContactoRol;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ContactoRolDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContactoRolAssembler {
    
    static public ContactoRolDto toDto(ContactoRol rol) {
        if (rol == null) {
            return null;
        }   
        ContactoRolDto dto = new ContactoRolDto();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        return dto;
    }
    
    static public List<ContactoRolDto> toDto(List<ContactoRol> roles) {
        if (roles == null) {
            return null;
        }
        List<ContactoRolDto> dtos = new ArrayList<>();
        roles.forEach(rol -> {
            dtos.add(toDto(rol));
        });
        return dtos;
    }
    
}
