/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.usuarios.exception;

import es.zarca.covellog.application.adreces.exception.*;
import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class UsernameYaExisteException extends BusinessException {

    public UsernameYaExisteException(String username) {
        super("El username \"" + username + "\" ya está siendo utilizado por otro usuario.");
    }
    
}
