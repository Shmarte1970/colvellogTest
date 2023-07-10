package es.zarca.covellog.application.transporte.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class TransporteNotExistException extends BusinessException {

    public TransporteNotExistException(Integer id) {
        super("El transporte con Id \"" + id + "\" no existe.");
    }
    
}
