package es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas;

import es.zarca.covellog.domain.model.stock.reservas.ReservaTipo;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaTipoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ReservaTipoAssembler {
    
    static public List<ReservaTipoDto> toDto(List<ReservaTipo> lista) {
        List<ReservaTipoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ReservaTipoDto toDto(ReservaTipo tipo) {
        if (tipo == null) {
            return null;
        }
        ReservaTipoDto dto = new ReservaTipoDto();
        dto.setId(tipo.getId());
        dto.setNombre(tipo.getNombre());
        return dto;
    }
    
}