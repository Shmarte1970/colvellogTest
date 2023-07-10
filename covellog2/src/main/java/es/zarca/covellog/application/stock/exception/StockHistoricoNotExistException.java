package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class StockHistoricoNotExistException extends BusinessException {

    public StockHistoricoNotExistException(int id) {
        super("El historico de stock con Id \"" + id + "\" no existe.");
    }
    
}
