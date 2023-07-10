package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CondicionNotExistException extends BusinessException {

    public CondicionNotExistException(String id) {
        super("La condicion con Id \"" + id + "\" no existe.");
    }
    
}
