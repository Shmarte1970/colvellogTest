package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ReservaNotExistException extends BusinessException {

    public ReservaNotExistException(Integer id) {
        super("La reserva con Id \"" + String.valueOf(id) + "\" no existe.");
    }
    
}
