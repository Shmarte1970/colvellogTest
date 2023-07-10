package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.producto.FamiliaProducto;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import static es.zarca.covellog.interfaces.facade.stock.internal.assembler.TipoProductoAssembler.toDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class FamiliaProductoAssembler {
    
    static public List<FamiliaProductoDto> toDto(List<FamiliaProducto> lista) {
        List<FamiliaProductoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public FamiliaProductoDto toDto(FamiliaProducto familiaProducto) {
        if (familiaProducto == null) {
            return null;
        }
        FamiliaProductoDto dto = new FamiliaProductoDto();
        dto.setId(familiaProducto.getId());
        dto.setNombre(familiaProducto.getNombre());
        dto.setObservaciones(familiaProducto.getObservaciones());
        dto.setFechaBaja(DateUtil.dateTimeToString(familiaProducto.getFechaBaja()));
        return dto;
    }
    
}