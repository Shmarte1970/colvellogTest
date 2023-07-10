package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class NoEstaVendidoModelException extends BusinessException {

    public NoEstaVendidoModelException(String id) {
        super(id + ": No esta vendido");
    }
    
}
