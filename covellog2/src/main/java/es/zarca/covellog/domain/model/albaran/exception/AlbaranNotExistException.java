package es.zarca.covellog.domain.model.albaran.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AlbaranNotExistException extends BusinessException {

    public AlbaranNotExistException(Integer id) {
        super("El albaran \"" + id + "\" no existe.");
    }

}