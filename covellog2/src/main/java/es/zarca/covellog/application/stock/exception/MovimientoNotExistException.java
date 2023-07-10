package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class MovimientoNotExistException extends BusinessException {

    public MovimientoNotExistException(Integer id) {
        super("El movimiento con Id \"" + String.valueOf(id) + "\" no existe.");
    }
    
}
