/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodiIso2NotNullException extends BusinessException {

    public CodiIso2NotNullException() {
        super("El código ISO alpha 2 de país no puede ser nulo.");
    }
    
}
