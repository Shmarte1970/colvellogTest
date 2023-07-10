/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler;

import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PaisDtoAssembler {
    
    public PaisDTO toDto(Pais pais) {
        if (pais == null) {
            return null;
        }
        PaisDTO dto = new PaisDTO(pais.getId(), pais.getCodiIso2().toString(), pais.getNom());
        return dto;
    }
}
