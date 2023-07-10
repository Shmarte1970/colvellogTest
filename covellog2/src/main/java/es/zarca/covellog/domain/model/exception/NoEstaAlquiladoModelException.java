package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class NoEstaAlquiladoModelException extends BusinessException {

    public NoEstaAlquiladoModelException(Stock stock) {
        super("El producto \"" + stock.getNumeroSerie() + "\" no esta alquilado.");
    }
    
}
