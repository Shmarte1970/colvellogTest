package es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler;

import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.DobleObservacionAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class UbicacionAssembler {
    
    static public UbicacionDto toDto(Ubicacion ubicacion) {
        UbicacionDto dto = new UbicacionDto();
        if (ubicacion == null) {
            return null;
        }
        toDto(dto, ubicacion);
        return dto;
    }
    
    static public void toDto(UbicacionDto dto, Ubicacion ubicacion) {
        dto.setId(ubicacion.getId());
        dto.setEmpresa(EmpresaMiniDtoAssembler.toDto(ubicacion.getEmpresa()));
        dto.setContactos(ContactoAssembler.toDto(ubicacion.getContactos()));
        dto.setNombre(ubicacion.getNombre());
        dto.setDescripcion(ubicacion.getDescripcion());
        dto.setDireccion(DireccionAssembler.toDto(ubicacion.getDireccion()));
        dto.setHorario(ubicacion.getHorario());
        dto.setObservaciones(DobleObservacionAssembler.toDto(ubicacion.getObservaciones()));
    }
    
    static public List<UbicacionDto> toDto(List<? extends Ubicacion> ubicaciones) {
        if (ubicaciones == null) {
            return null;
        }
        List<UbicacionDto> dtos = new ArrayList<>();
        ubicaciones.forEach((ubicacion) -> {
            dtos.add(toDto(ubicacion));
        });
        return dtos;
    }

}