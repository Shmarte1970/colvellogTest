package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class StockNoDisponibleModelException extends BusinessException {

    public StockNoDisponibleModelException(Stock stock) {
        super("El producto \"" + stock.getNumeroSerie() + "\" no esta disponible");
    }
    
}
