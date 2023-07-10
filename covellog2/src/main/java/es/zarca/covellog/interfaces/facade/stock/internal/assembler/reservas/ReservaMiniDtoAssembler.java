package es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas;

import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaMiniDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ReservaMiniDtoAssembler {
    
    static public List<ReservaMiniDto> toDto(List<Reserva> lista) {
        List<ReservaMiniDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ReservaMiniDto toDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        ReservaMiniDto dto = new ReservaMiniDto();
        dto.setId(reserva.getId());
        dto.setFriendlyId(reserva.getFriendlyId());
        dto.setTipo(ReservaTipoAssembler.toDto(reserva.getTipo()));
        dto.setBooking(reserva.getBooking()); 
        dto.setEstado(ReservaEstadoAssembler.toDto(reserva.getEstado()));
        return dto;
    }
    
}