package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class NoEstaBloqueadoModelException extends BusinessException {

    public NoEstaBloqueadoModelException(String id) {
        super(id + ": No esta bloqueado");
    }
    
}
