package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class NoEstaDisponibleException extends BusinessException {

    public NoEstaDisponibleException(Stock stock) {
        super("El producto \"" + stock.getNumeroSerie() + "\" no esta disponible.");
    }
    
}
