package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class StockNotExistException extends BusinessException {

    public StockNotExistException(int id) {
        super("El stock con Id \"" + id + "\" no existe.");
    }
    
}
