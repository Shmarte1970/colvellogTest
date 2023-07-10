/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler;

import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PoblacioDTO;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PoblacioDtoAssembler {
    
    private static final Logger logger = Logger.getLogger(PoblacioDtoAssembler.class.getName());
    
    static public PoblacioDTO toDto(Poblacio poblacio) {
        if (poblacio == null) {
            return null;
        }
        ProvinciaDtoAssembler provinciaDtoAssembler = new ProvinciaDtoAssembler();
        PoblacioDTO dto = new PoblacioDTO(poblacio.getId(), poblacio.getNom(), poblacio.getCodiMunicipi(), poblacio.getDc(), provinciaDtoAssembler.toDto(poblacio.getProvincia()));
        return dto;
    }
}
