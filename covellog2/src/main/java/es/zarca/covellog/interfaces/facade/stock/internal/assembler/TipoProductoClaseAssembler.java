package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.producto.TipoProductoClase;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoClaseDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class TipoProductoClaseAssembler {
    
    static public List<TipoProductoClaseDto> toDto(List<TipoProductoClase> lista) {
        List<TipoProductoClaseDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public TipoProductoClaseDto toDto(TipoProductoClase tipoProductoClase) {
        if (tipoProductoClase == null) {
            return null;
        }
        TipoProductoClaseDto dto = new TipoProductoClaseDto();
        dto.setId(tipoProductoClase.getId());
        dto.setNombre(tipoProductoClase.getNombre());
        return dto;
    }
    
}