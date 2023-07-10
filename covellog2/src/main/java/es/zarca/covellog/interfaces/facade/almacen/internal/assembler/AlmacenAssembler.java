package es.zarca.covellog.interfaces.facade.almacen.internal.assembler;

import es.zarca.covellog.domain.model.almacen.Almacen;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;

/**
 *
 * @author francisco
 */
public class AlmacenAssembler extends UbicacionAssembler {
    
    static public AlmacenDto toDto(Almacen almacen) {
        if (almacen == null) {
            return null;
        }  
        AlmacenDto dto = new AlmacenDto();
        //AlmacenAssembler.toDto(dto, almacen);
        //dto.setDireccion(DireccionAssembler.toDto(almacen.getDireccion()));
        return dto;
    }
    
    /*static public List<AlmacenDto> toDto(List<Almacen> almacenes) {
        if (almacenes == null) {
            return null;
        }
        List<AlmacenDto> dtos = new ArrayList<>();
        almacenes.forEach((almacen) -> {
            dtos.add(toDto(almacen));
        });
        return dtos;
    }
   */
}
