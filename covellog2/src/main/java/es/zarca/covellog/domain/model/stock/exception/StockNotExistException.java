package es.zarca.covellog.domain.model.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class StockNotExistException extends BusinessException {

    public StockNotExistException(Integer id) {
        super("El stock con Id \"" + id + "\" no existe.");
    }
    
}
