package es.zarca.covellog.interfaces.facade.stock.internal.assembler;

import es.zarca.covellog.domain.model.generic.Documento;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.producto.TipoProductoDocumento;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class TipoProductoAssembler {
    
    static public List<TipoProductoDto> toDto(List<TipoProducto> lista) {
        List<TipoProductoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public TipoProductoDto toDto(TipoProducto tipoProducto) {
        if (tipoProducto == null) {
            return null;
        }
        TipoProductoDto dto = new TipoProductoDto();
        dto.setId(tipoProducto.getId());
        dto.setDescripcion(tipoProducto.getDescripcion());
        dto.setFamilia(FamiliaProductoAssembler.toDto(tipoProducto.getFamilia()));
        dto.setClase(TipoProductoClaseAssembler.toDto(tipoProducto.getClase()));
        dto.setFechaAlta(DateUtil.dateTimeToString(tipoProducto.getFechaAlta()));
        dto.setFechaBaja(DateUtil.dateTimeToString(tipoProducto.getFechaBaja()));
        dto.setUnidadMedida(tipoProducto.getUnidadMedida().getId());
        return dto;
    }
    
    static public TipoProductoDto toDtoWithDocs(TipoProducto tipoProducto) {
        if (tipoProducto == null) {
            return null;
        }
        TipoProductoDto dto = toDto(tipoProducto);
        List<? extends Documento> docs = tipoProducto.getDocumentos();
        dto.setDocumentos(DocumentoDtoAssembler.toDto(docs));
        return dto;
    }
    
}