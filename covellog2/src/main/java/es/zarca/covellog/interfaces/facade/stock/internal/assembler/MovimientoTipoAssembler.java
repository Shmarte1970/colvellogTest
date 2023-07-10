package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.movimientos.MovimientoTipo;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoTipoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class MovimientoTipoAssembler {
    
    static public List<MovimientoTipoDto> toDto(List<MovimientoTipo> lista) {
        List<MovimientoTipoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public MovimientoTipoDto toDto(MovimientoTipo tipo) {
        if (tipo == null) {
            return null;
        }
        MovimientoTipoDto dto = new MovimientoTipoDto();
        dto.setId(tipo.getId());
        dto.setNombre(tipo.getNombre());
        return dto;
    }
    
}