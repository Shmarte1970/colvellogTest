package es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas;

import es.zarca.covellog.domain.model.stock.reservas.ReservaEstado;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaEstadoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ReservaEstadoAssembler {
    
    static public List<ReservaEstadoDto> toDto(List<ReservaEstado> lista) {
        List<ReservaEstadoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ReservaEstadoDto toDto(ReservaEstado estado) {
        if (estado == null) {
            return null;
        }
        ReservaEstadoDto dto = new ReservaEstadoDto();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }
    
}