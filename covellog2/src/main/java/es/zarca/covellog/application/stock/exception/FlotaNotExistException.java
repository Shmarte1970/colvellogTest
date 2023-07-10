package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FlotaNotExistException extends BusinessException {

    public FlotaNotExistException(String id) {
        super("La flota con Id \"" + id + "\" no existe.");
    }
    
}
