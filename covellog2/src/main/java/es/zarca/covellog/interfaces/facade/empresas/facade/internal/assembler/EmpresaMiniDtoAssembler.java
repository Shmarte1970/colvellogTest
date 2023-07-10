package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class EmpresaMiniDtoAssembler {
    
    static public EmpresaMiniDto toDto(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        EmpresaMiniDto dto = new EmpresaMiniDto();
        dto.setId(empresa.getId());
        dto.setFriendlyId(empresa.getFriendlyId());
        dto.setCif(empresa.getCif() == null ? null : empresa.getCif().toString());
        dto.setNombre(empresa.getNombre());
        dto.setAlias(empresa.getAlias());
        return dto;
    }
    
    static public List<EmpresaMiniDto> toDto(List<Empresa> empresas) {
        if (empresas == null) {
            return null;
        }
        List<EmpresaMiniDto> dtos = new ArrayList<>();
        empresas.forEach((empresa) -> {
            dtos.add(toDto(empresa));
        });
        return dtos;
    }

}
