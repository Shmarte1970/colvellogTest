package es.zarca.covellog.domain.model.albaran.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AlbaranRecogidaNotExistException extends BusinessException {

    public AlbaranRecogidaNotExistException(Integer id) {
        super("El albaran de recogida \"" + id + "\" no existe.");
    }

}