package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.producto.UnidadMedida;
import es.zarca.covellog.interfaces.facade.stock.dto.UnidadMedidaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class UnidadMedidaAssembler {
    
    static public List<UnidadMedidaDto> toDto(List<UnidadMedida> lista) {
        List<UnidadMedidaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public UnidadMedidaDto toDto(UnidadMedida unidadMedida) {
        if (unidadMedida == null) {
            return null;
        }
        UnidadMedidaDto dto = new UnidadMedidaDto();
        dto.setId(unidadMedida.getId());
        dto.setNombre(unidadMedida.getNombre());
        return dto;
    }
    
}