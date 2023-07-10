package es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas;

import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import es.zarca.covellog.interfaces.facade.albaran.internal.assembler.AlbaranMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ReservaAssembler {
    
    static public List<ReservaDto> toDto(List<Reserva> lista) {
        List<ReservaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ReservaDto toDto(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        ReservaDto dto = new ReservaDto();
        dto.setId(reserva.getId());
        dto.setFriendlyId(reserva.getFriendlyId());
        dto.setTipo(ReservaTipoAssembler.toDto(reserva.getTipo()));
        dto.setEstado(ReservaEstadoAssembler.toDto(reserva.getEstado()));
        dto.setEstadoFecha(reserva.getEstadoFecha());
        dto.setFecha(reserva.getFecha());
        dto.setUbicacion(UbicacionAssembler.toDto(reserva.getUbicacion()));
        dto.setCliente(EmpresaMiniDtoAssembler.toDto(reserva.getCliente()));
        dto.setBooking(reserva.getBooking());
        dto.setTransportistaId(reserva.getTransportista() == null ? null : reserva.getTransportista().getId());
        dto.setTransportistaNombre(reserva.getTransportistaNombre());
        dto.setChofer(reserva.getChofer());
        dto.setMatricula(reserva.getMatricula());
        dto.setObservaciones(reserva.getObservaciones());
        dto.setLineas(ReservaLineaAssembler.toDto(reserva.getLineas()));
        dto.setCanActivar(reserva.getCanActivar());
        dto.setCanReabrir(reserva.getCanReabrir());
        dto.setCanAnular(reserva.getCanAnular());
        dto.setCanModificar(reserva.getCanModificar());
        dto.setAlbaran(AlbaranMiniDtoAssembler.toDto(reserva.getAlbaran()));
        return dto;
    }
    
}