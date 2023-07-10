/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ProvinciaCodiNotUniqueException extends BusinessException {

    public ProvinciaCodiNotUniqueException(Provincia provinciaConflicto) {
        super("No puede asignar el código de provincia \"" + provinciaConflicto.getCodi() + "\" porque ya se está usando en la província \"" + provinciaConflicto.getNom() + "\".");
    }
    
    public ProvinciaCodiNotUniqueException(Provincia provinciaError, Provincia provinciaConflicto) {
        super("No puede asignar el código de provincia \"" + provinciaConflicto.getCodi() + "\" a la província  \"" + provinciaError.getNom() + "\" porque ya se está usando en la província \"" + provinciaConflicto.getNom() + "\".");
    }
    
}
