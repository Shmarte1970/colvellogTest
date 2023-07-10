package es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler;

import es.zarca.covellog.domain.model.empresa.Potencial;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.PotencialDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class PotencialAssembler {
    
    static public PotencialDto toDto(Potencial potencial) {
        if (potencial == null) {
            return null;
        }   
        PotencialDto dto = new PotencialDto();
        dto.setComerciales(ComercialAssembler.toDto(potencial.getComerciales()));
        dto.setContactos(ContactoAssembler.toDto(potencial.getContactos()));
        dto.setObservaciones(DobleObservacionAssembler.toDto(potencial.getObservaciones()));
        dto.setFechaBloqueo(DateUtil.dateTimeToString(potencial.getFechaBloqueo()));
        return dto;
    }
    
    static public List<PotencialDto> toDto(List<Potencial> potencials) {
        if (potencials == null) {
            return null;
        }
        List<PotencialDto> dtos = new ArrayList<>();
        potencials.forEach((potencial) -> {
            dtos.add(toDto(potencial));
        });
        return dtos;
    }
    
}
