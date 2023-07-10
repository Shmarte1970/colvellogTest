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
public class PaisNomNotUniqueException extends BusinessException {

    public PaisNomNotUniqueException(Pais paisConflicto) {
        super("No puede asignar el nombre de país \"" + paisConflicto.getNom() + "\" porque ya se está usando en el país \"" + paisConflicto.getNom() + "\".");
    }
    
    public PaisNomNotUniqueException(Pais paisError, Pais paisConflicto) {
        super("No puede asignar el nombre de país \"" + paisConflicto.getNom() + "\" al país  \"" + paisError.getNom() + "\" porque ya se está usando en el país \"" + paisConflicto.getNom() + "\".");
    }
    
}
