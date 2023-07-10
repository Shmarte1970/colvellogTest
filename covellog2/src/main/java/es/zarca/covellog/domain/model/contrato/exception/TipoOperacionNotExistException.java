package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class TipoOperacionNotExistException extends BusinessException {

    public TipoOperacionNotExistException(String id) {
        super("El tipo de operacion con Id \"" + id + "\" no existe.");
    }
    
}
