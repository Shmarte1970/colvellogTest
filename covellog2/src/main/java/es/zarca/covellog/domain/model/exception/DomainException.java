package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DomainException extends BusinessException {

    public DomainException(String message) {
        super(message);
    }
    
}
