/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.usuarios.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PasswordsNoCoincidenException extends BusinessException {

    public PasswordsNoCoincidenException() {
        super("Las contraseñas no coinciden.");
    }
    
}
