package es.zarca.covellog.domain.model.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class StockPorLoteNotExistException extends BusinessException {

    public StockPorLoteNotExistException(String id) {
        super("El stock por lote con Id \"" + id + "\" no existe.");
    }
    
}
