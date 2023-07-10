package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.Flota;
import es.zarca.covellog.interfaces.facade.stock.dto.FlotaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class FlotaAssembler {
    
    static public List<FlotaDto> toDto(List<Flota> lista) {
        List<FlotaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public FlotaDto toDto(Flota flota) {
        if (flota == null) {
            return null;
        }
        FlotaDto dto = new FlotaDto();
        dto.setId(flota.getId());
        dto.setNombre(flota.getNombre());
        return dto;
    }
    
}