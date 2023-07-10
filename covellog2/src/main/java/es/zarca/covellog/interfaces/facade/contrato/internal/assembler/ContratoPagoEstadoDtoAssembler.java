package es.zarca.covellog.interfaces.facade.contrato.internal.assembler;

import es.zarca.covellog.domain.model.contrato.ContratoPagoEstado;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoPagoEstadoDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoPagoEstadoDtoAssembler {
    
    static public List<ContratoPagoEstadoDto> toDto(List<ContratoPagoEstado> lista) {
        List<ContratoPagoEstadoDto> listaDto = new ArrayList<>();
        if (lista != null) {
            lista.forEach(model -> {
                listaDto.add(toDto(model));
            });
        }
        return listaDto;
    }
    
    static public ContratoPagoEstadoDto toDto(ContratoPagoEstado tipoOperacion) {
        if (tipoOperacion == null) {
            return null;
        }
        ContratoPagoEstadoDto dto = new ContratoPagoEstadoDto();
        dto.setId(tipoOperacion.getId());
        dto.setNombre(tipoOperacion.getNombre());
        return dto;
    }
    
}