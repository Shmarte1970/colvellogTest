package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas.ReservaMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class MovimientoAssembler {
    
    static public List<MovimientoDto> toDto(List<Movimiento> lista) {
        List<MovimientoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public MovimientoDto toDto(Movimiento movimiento) {
        if (movimiento == null) {
            return null;
        }
        MovimientoDto dto = new MovimientoDto();
        dto.setId(movimiento.getId());
        dto.setReserva(ReservaMiniDtoAssembler.toDto(movimiento.getReserva()));
        dto.setFriendlyId(movimiento.getFriendlyId());
        dto.setBooking(movimiento.getBooking());
        dto.setTipo(MovimientoTipoAssembler.toDto(movimiento.getTipo()));
        dto.setEstado(MovimientoEstadoAssembler.toDto(movimiento.getEstado()));
        dto.setFecha(movimiento.getFecha());
        dto.setUbicacion(UbicacionAssembler.toDto(movimiento.getUbicacion()));
        dto.setCliente(EmpresaMiniDtoAssembler.toDto(movimiento.getCliente()));
        dto.setTransportistaId(movimiento.getTransportista() == null ? null : movimiento.getTransportista().getId());
        dto.setTransportistaNombre(movimiento.getTransportistaNombre());
        dto.setChofer(movimiento.getChofer());
        dto.setMatricula(movimiento.getMatricula());
        dto.setObservaciones(movimiento.getObservaciones());
        dto.setLineas(MovimientoLineaAssembler.toDto(movimiento.getLineas()));
        return dto;
    }
    
}