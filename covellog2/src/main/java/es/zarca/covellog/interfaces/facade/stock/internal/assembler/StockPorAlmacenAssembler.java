package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.stock.StockPorAlmacen;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorAlmacenDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.internal.assembler.UbicacionAssembler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class StockPorAlmacenAssembler {
    
    static public List<StockPorAlmacenDto> toDto(List<StockPorAlmacen> lista) {
        List<StockPorAlmacenDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public StockPorAlmacenDto toDto(StockPorAlmacen stock) {
        if (stock == null) {
            return null;
        }
        StockPorAlmacenDto dto = new StockPorAlmacenDto();
        dto.setId(stock.getId());
        dto.setTipoProducto(TipoProductoAssembler.toDto(stock.getTipoProducto()));
        dto.setPropietario(stock.getPropietario().getAliasNombre());
        dto.setUbicacion(UbicacionAssembler.toDto(stock.getUbicacion()));
        dto.setFlota(FlotaAssembler.toDto(stock.getFlota()));
        dto.setEstado(EstadoAssembler.toDto(stock.getEstado()));
        dto.setCondicion(CondicionAssembler.toDto(stock.getCondicion()));
        dto.setCantidad(stock.getStock());
        return dto;
    }
    
}