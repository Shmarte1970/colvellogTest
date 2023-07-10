package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EstadoNotExistException extends BusinessException {

    public EstadoNotExistException(String id) {
        super("El estado con Id \"" + id + "\" no existe.");
    }
    
}
