/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.clients.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ClientNotExistException extends BusinessException {

    public ClientNotExistException(Integer id) {
        super("El cliente con Id=" + id.toString() + " no existe.");
    }
    
}
