package es.zarca.covellog.interfaces.facade.almacen.internal.assembler;

import es.zarca.covellog.domain.model.almacen.Almacen;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlmacenDtoAssembler {

    static public AlmacenDto toDto(Almacen almacen) {
        AlmacenDto dto = new AlmacenDto();
        if (almacen == null) {
            return null;
        }
        toDto(dto, almacen);
        return dto;
    }
    
    static public void toDto(AlmacenDto dto, Almacen almacen) {
        UbicacionAssembler.toDto(dto, almacen);
        dto.setFechaBaja(almacen.getFechaBaja());
    }
    
    static public List<AlmacenDto> toDto(List<? extends Almacen> almacenes) {
        if (almacenes == null) {
            return null;
        }
        List<AlmacenDto> dtos = new ArrayList<>();
        almacenes.forEach((ubicacion) -> {
            dtos.add(toDto(ubicacion));
        });
        return dtos;
    }
    
}