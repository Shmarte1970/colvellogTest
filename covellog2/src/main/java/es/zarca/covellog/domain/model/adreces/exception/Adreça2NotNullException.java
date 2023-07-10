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
public class Adreça2NotNullException extends BusinessException {

    public Adreça2NotNullException() {
        super("La dirección 2 es un valor obligatorio.");
    }
    
}
