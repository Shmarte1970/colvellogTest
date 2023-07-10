package es.zarca.covellog.interfaces.facade.contrato.internal.assembler;

import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoTipoOperacionDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoTipoOperacionDtoAssembler {
    
    static public List<ContratoTipoOperacionDto> toDto(List<ContratoTipoOperacion> lista) {
        List<ContratoTipoOperacionDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoTipoOperacionDto toDto(ContratoTipoOperacion tipoOperacion) {
        if (tipoOperacion == null) {
            return null;
        }
        ContratoTipoOperacionDto dto = new ContratoTipoOperacionDto();
        dto.setId(tipoOperacion.getId());
        dto.setNombre(tipoOperacion.getNombre());
        return dto;
    }
    
}