package es.zarca.covellog.domain.model.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class StockPorAlmacenNotExistException extends BusinessException {

    public StockPorAlmacenNotExistException(String id) {
        super("El stock por almacen con Id \"" + id + "\" no existe.");
    }
    
}
