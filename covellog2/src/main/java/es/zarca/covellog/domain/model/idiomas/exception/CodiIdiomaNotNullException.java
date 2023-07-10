/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.idiomas.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodiIdiomaNotNullException extends BusinessException {

    public CodiIdiomaNotNullException() {
        super("El código de idioma no puede ser nulo.");
    }
    
}
