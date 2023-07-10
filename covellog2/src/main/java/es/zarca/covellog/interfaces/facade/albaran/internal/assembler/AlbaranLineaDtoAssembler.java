package es.zarca.covellog.interfaces.facade.albaran.internal.assembler;

import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranLineaDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockAssembler;
import java.util.ArrayList;
import java.util.List;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas.ReservaMiniDtoAssembler;
import es.zarca.covellog.domain.model.albaran.IAlbaranLinea;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import java.util.Collections;

/**
 *
 * @author francisco
 */
public class AlbaranLineaDtoAssembler {
    
    static public List<AlbaranLineaDto> toDto(List<? extends IAlbaranLinea> lista) {
        List<AlbaranLineaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public AlbaranLineaDto toDto(IAlbaranLinea albaranLinea) {
        if (albaranLinea == null) {
            return null;
        }
        AlbaranLineaDto dto = new AlbaranLineaDto();
        dto.setId(albaranLinea.getId());
        dto.setStock(StockAssembler.toDto(albaranLinea.getStock()));
        dto.setAsignacionStock(StockAssembler.toDto(albaranLinea.getAsignacionStock()));
        dto.setBooking(albaranLinea.getBooking());
        dto.setNumSerie(albaranLinea.getNumSerie());
        dto.setTipoProducto(TipoProductoAssembler.toDto(albaranLinea.getTipoProducto()));
        dto.setLote(albaranLinea.getLote());
        dto.setDescripcion(albaranLinea.getDescripcion());
        dto.setFechaEntrega(albaranLinea.getFechaEntrega());
        dto.setFechaSalida(albaranLinea.getFechaSalida());
        dto.setFechaLlegada(albaranLinea.getFechaLlegada());
        dto.setReserva(ReservaMiniDtoAssembler.toDto(albaranLinea.getReserva()));
        dto.setCheckList(Collections.unmodifiableList(albaranLinea.getCheckList()));
        return dto;
    }
    
}