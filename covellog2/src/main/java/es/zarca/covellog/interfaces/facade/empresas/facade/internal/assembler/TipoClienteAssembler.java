package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.cliente.TipoCliente;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoClienteDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class TipoClienteAssembler {
    
    static public TipoClienteDto toDto(TipoCliente tipoCliente) {
        if (tipoCliente == null) {
            return null;
        }
        TipoClienteDto dto = new TipoClienteDto();
        dto.setId(tipoCliente.getId());
        dto.setNombre(tipoCliente.getNombre());
        return dto;
    }
    
    static public List<TipoClienteDto> toDto(List<TipoCliente> tiposCliente) {
        if (tiposCliente == null) {
            return null;
        }
        List<TipoClienteDto> dtos = new ArrayList<>();
        tiposCliente.forEach((tipoCliente) -> {
            dtos.add(toDto(tipoCliente));
        });
        return dtos;
    }
}