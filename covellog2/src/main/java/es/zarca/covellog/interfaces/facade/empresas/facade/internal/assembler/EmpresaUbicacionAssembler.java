package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.EmpresaUbicacion;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.UbicacionEmpresaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class EmpresaUbicacionAssembler {
    
    static public UbicacionEmpresaDto toDto(EmpresaUbicacion ubicacion) {
        if (ubicacion == null) {
            return null;
        }   
        UbicacionEmpresaDto dto = new UbicacionEmpresaDto();
        dto.setId(ubicacion.getId());
        dto.setEmpresa(EmpresaAssembler.toDto(ubicacion.getEmpresa()));
        dto.setContactos(ContactoAssembler.toDto(ubicacion.getContactos()));
        dto.setNombre(ubicacion.getNombre());
        dto.setDescripcion(ubicacion.getDescripcion());
        dto.setDireccion(DireccionAssembler.toDto(ubicacion.getDireccion()));
        dto.setHorario(ubicacion.getHorario());
        dto.setObservaciones(DobleObservacionAssembler.toDto(ubicacion.getObservaciones()));
        return dto;
    }
    
    static public void toDto(UbicacionEmpresaDto dto, EmpresaUbicacion ubicacion) {
        if (ubicacion != null) {
            dto.setId(ubicacion.getId());
            dto.setContactos(ContactoAssembler.toDto(ubicacion.getContactos()));
            dto.setNombre(ubicacion.getNombre());
            dto.setEmpresa(EmpresaAssembler.toDto(ubicacion.getEmpresa()));
            dto.setDescripcion(ubicacion.getDescripcion());
            dto.setDireccion(DireccionAssembler.toDto(ubicacion.getDireccion()));
            dto.setHorario(ubicacion.getHorario());
            dto.setObservaciones(DobleObservacionAssembler.toDto(ubicacion.getObservaciones()));
        }
    }
    
    static public List<UbicacionEmpresaDto> toDto(List<EmpresaUbicacion> direccionesEnvio) {
        if (direccionesEnvio == null) {
            return null;
        }
        List<UbicacionEmpresaDto> dtos = new ArrayList<>();
        direccionesEnvio.forEach((ubicacion) -> {
            dtos.add(toDto(ubicacion));
        });
        return dtos;
    }
   
}
