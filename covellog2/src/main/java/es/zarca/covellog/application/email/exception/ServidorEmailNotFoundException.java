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
public class ServidorEmailNotFoundException extends BusinessException {

    public ServidorEmailNotFoundException(String id) {
        super("No se encuentra el servidor de correo " + id + ".");
    }
    
}
