package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.movimientos.MovimientoEstado;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoEstadoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class MovimientoEstadoAssembler {
    
    static public List<MovimientoEstadoDto> toDto(List<MovimientoEstado> lista) {
        List<MovimientoEstadoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public MovimientoEstadoDto toDto(MovimientoEstado estado) {
        if (estado == null) {
            return null;
        }
        MovimientoEstadoDto dto = new MovimientoEstadoDto();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }
    
}