package es.zarca.covellog.interfaces.facade.stock.internal.assembler.reservas;

import es.zarca.covellog.domain.model.stock.reservas.ReservaLineaRO;
import es.zarca.covellog.interfaces.facade.stock.dto.StockFijadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaLineaDto;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.MovimientoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.StockMiniDtoAssembler;
import es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ReservaLineaAssembler {
    
    static public List<ReservaLineaDto> toDto(List<ReservaLineaRO> lista) {
        List<ReservaLineaDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ReservaLineaDto toDto(ReservaLineaRO linea) {
        if (linea == null) {
            return null;
        }
        ReservaLineaDto dto = new ReservaLineaDto();
        dto.setId(linea.getId());
        dto.setBooking(linea.getBooking());
        dto.setStock(StockMiniDtoAssembler.toDto(linea.getStock()));
        dto.setStockFijado(
            new StockFijadoDto(
                linea.getNumSerie(), 
                UbicacionAssembler.toDto(linea.getReserva().getUbicacion()), 
                TipoProductoAssembler.toDto(linea.getTipoProducto()), 
                linea.getLote(),
                ""
            )
        );
        dto.setTipoProducto(TipoProductoAssembler.toDto(linea.getTipoProducto()));
        dto.setLote(linea.getLote());
        dto.setNumSerie(linea.getNumSerie());
        dto.setMovimiento(MovimientoAssembler.toDto(linea.getMovimiento()));
        dto.setNumSerieAsignado(linea.getNumSerieAsignado());
        dto.setFechaEntrega(linea.getFechaEntrega());
        dto.setCheckList(linea.getCheckList());
        return dto;
    }
    
}