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
public class ProvinciaNomNotUniqueException extends BusinessException {

    public ProvinciaNomNotUniqueException(Provincia provinciaConflicto) {
        super("Ya existe una província con el nombre \"" + provinciaConflicto.getNom() + "\".");
    }
    
    public ProvinciaNomNotUniqueException(Provincia provinciaError, Provincia provinciaConflicto) {
        super("Ya existe una província con el nombre \"" + provinciaConflicto.getNom() + "\".");
    }
    
}
