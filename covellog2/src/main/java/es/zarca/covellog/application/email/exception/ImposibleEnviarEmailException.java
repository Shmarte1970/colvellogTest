/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.email.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ImposibleEnviarEmailException extends BusinessException {

    public ImposibleEnviarEmailException(String missatje) {
        super("No se puede enviar el email de notificación: " + missatje);
    }
    
}
