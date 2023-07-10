package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.EmpresaDireccionEnvio;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class DireccionEnvioAssembler {
    
    static public DireccionEnvioDto toDto(EmpresaDireccionEnvio direccionEnvio) {
        if (direccionEnvio == null) {
            return null;
        }   
        DireccionEnvioDto dto = new DireccionEnvioDto();
        dto.setId(direccionEnvio.getId());
        dto.setContactos(ContactoAssembler.toDto(direccionEnvio.getContactos()));
        dto.setNombre(direccionEnvio.getNombre());
        dto.setDescripcion(direccionEnvio.getDescripcion());
        dto.setHorario(direccionEnvio.getHorario());
        dto.setDireccion(DireccionAssembler.toDto(direccionEnvio.getDireccion()));    
        dto.setObservaciones(DobleObservacionAssembler.toDto(direccionEnvio.getObservaciones()));
        return dto;
    }
    
    static public List<DireccionEnvioDto> toDto(List<EmpresaDireccionEnvio> direccionesEnvio) {
        if (direccionesEnvio == null) {
            return null;
        }
        List<DireccionEnvioDto> dtos = new ArrayList<>();
        direccionesEnvio.forEach((direccionEnvio) -> {
            dtos.add(toDto(direccionEnvio));
        });
        return dtos;
    }
   
}
