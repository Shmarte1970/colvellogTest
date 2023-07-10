package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.StockPorLote;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorLoteDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockPorLoteAssembler {
    
    static public List<StockPorLoteDto> toDto(List<StockPorLote> lista) {
        List<StockPorLoteDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockPorLoteDto toDto(StockPorLote stock) {
        if (stock == null) {
            return null;
        }
        StockPorLoteDto dto = new StockPorLoteDto();
        dto.setId(stock.getId());
        dto.setTipoProducto(TipoProductoAssembler.toDto(stock.getTipoProducto()));
        dto.setPropietario(stock.getPropietario().getAliasNombre());
        dto.setUbicacion(UbicacionAssembler.toDto(stock.getUbicacion()));
        dto.setLote(stock.getLote());
        dto.setFlota(FlotaAssembler.toDto(stock.getFlota()));
        dto.setEstado(EstadoAssembler.toDto(stock.getEstado()));
        dto.setCondicion(CondicionAssembler.toDto(stock.getCondicion()));
        dto.setCantidad(stock.getStock());
        return dto;
    }
    
}