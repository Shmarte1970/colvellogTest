/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.adreces.pais.Pais;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PaisCodiNotUniqueException extends BusinessException {

    public PaisCodiNotUniqueException(Pais paisConflicto) {
        super("No puede asignar el código de país \"" + paisConflicto.getCodiIso2() + "\" porque ya se está usando en el país \"" + paisConflicto.getNom() + "\".");
    }
    
    public PaisCodiNotUniqueException(Pais paisError, Pais paisConflicto) {
        super("No puede asignar el código de país \"" + paisConflicto.getCodiIso2() + "\" al país  \"" + paisError.getNom() + "\" porque ya se está usando en el país \"" + paisConflicto.getNom() + "\".");
    }
    
}
