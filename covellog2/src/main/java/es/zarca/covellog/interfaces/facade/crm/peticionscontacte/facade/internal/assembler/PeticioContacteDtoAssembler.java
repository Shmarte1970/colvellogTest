/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.internal.assembler;

import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacte;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.dto.PeticioContacteDTO;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PeticioContacteDtoAssembler {
    
    private static final Logger logger = Logger.getLogger(PeticioContacteDtoAssembler.class.getName());
    
    public PeticioContacteDTO toDto(PeticioContacte peticioContacte) {
        PeticioContacteDTO dto = new PeticioContacteDTO();
        dto.setSubmitTime(peticioContacte.getSubmitTime());
        dto.setForm(peticioContacte.getForm());
        dto.setNom(peticioContacte.getNom());
        dto.setEmail(peticioContacte.getEmail());
        dto.setEmpresa(peticioContacte.getEmpresa());
        dto.setTelefon(peticioContacte.getTelefon());
        dto.setAssumpte(peticioContacte.getAssumpte());
        dto.setMissatje(peticioContacte.getMissatje());
        dto.setIp(peticioContacte.getIp());   
        return dto;
    }
}
