package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.movimientos.MovimientoLinea;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoLineaDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class MovimientoLineaAssembler {
    
    static public List<MovimientoLineaDto> toDto(List<MovimientoLinea> lista) {
        List<MovimientoLineaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public MovimientoLineaDto toDto(MovimientoLinea movimientoLinea) {
        if (movimientoLinea == null) {
            return null;
        }
        MovimientoLineaDto dto = new MovimientoLineaDto();
        dto.setId(movimientoLinea.getId());
        dto.setBooking(movimientoLinea.getBooking());
        dto.setStock(StockMiniDtoAssembler.toDto(movimientoLinea.getStock()));
        return dto;
    }
    
}