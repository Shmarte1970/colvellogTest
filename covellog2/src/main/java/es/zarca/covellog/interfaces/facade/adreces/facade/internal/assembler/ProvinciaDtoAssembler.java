/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler;

import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ProvinciaDtoAssembler {
    
    public ProvinciaDTO toDto(Provincia provincia) {
        PaisDtoAssembler paisDtoAssembler = new PaisDtoAssembler();
        ProvinciaDTO dto = new ProvinciaDTO(provincia.getId(), provincia.getNom(), provincia.getCodi(), provincia.getCodiPostal(), paisDtoAssembler.toDto(provincia.getPais()));
        return dto;
    }
}
