/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.clients.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodiIncorrecteException extends BusinessException {

    public CodiIncorrecteException(String codi) {
        super("El código de cliente " + codi + " es incorrecto.");
    }
    
}
