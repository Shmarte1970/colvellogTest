package es.zarca.covellog.interfaces.facade.contactos.internal.assembler;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.ContactoRolAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.IdiomaAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContactoAssembler {
    
    static public ContactoDto toDto(Contacto contacto) {
        if (contacto == null) {
            return null;
        }   
        ContactoDto dto = new ContactoDto();
        dto.setId(contacto.getId());
        dto.setNombre(contacto.getNombre());
        dto.setApellidos(contacto.getApellidos());
        dto.setDescripcion(contacto.getDescripcion());
        dto.setTelefono(contacto.getCanalesContacto().getTelefono());
        dto.setTelefono2(contacto.getCanalesContacto().getTelefono2());
        dto.setEmail(contacto.getCanalesContacto().getEmail());           
        dto.setIdioma(IdiomaAssembler.toDto(contacto.getIdioma()));
        dto.setObservaciones(contacto.getObservaciones());
        dto.setHorario(contacto.getHorario());
        dto.setRoles(ContactoRolAssembler.toDto(contacto.getRoles()));
        return dto;
    }
    
    static public List<ContactoDto> toDto(List<? extends Contacto> contactos) {
        if (contactos == null) {
            return null;
        }
        List<ContactoDto> dtos = new ArrayList<>();
        contactos.forEach((contacto) -> {
            dtos.add(toDto(contacto));
        });
        return dtos;
    }
    
    static public ContactoDto clone(ContactoDto contacto) {
        if (contacto == null) {
            return null;
        }   
        ContactoDto dto = new ContactoDto();
        dto.setId(contacto.getId());
        dto.setNombre(contacto.getNombre());
        dto.setApellidos(contacto.getApellidos());
        dto.setDescripcion(contacto.getDescripcion());
        dto.setTelefono(contacto.getTelefono());
        dto.setTelefono2(contacto.getTelefono2());
        dto.setEmail(contacto.getEmail());           
        dto.setIdioma(contacto.getIdioma());
        dto.setObservaciones(contacto.getObservaciones());
        dto.setHorario(contacto.getHorario());
        return dto;
    }
    
    static public List<Integer> dtoToArrayId(List<ContactoDto> contactos) {
        if (contactos == null) {
            return null;
        }
        List<Integer> contactosIds = new ArrayList<>();
        contactos.forEach((contacto) -> {
            contactosIds.add(contacto.getId());
        });
        return contactosIds;
    }
    
}