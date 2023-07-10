package es.zarca.covellog.domain.model.albaran.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AlbaranEntregaNotExistException extends BusinessException {

    public AlbaranEntregaNotExistException(Integer id) {
        super("El albaran de entrega \"" + id + "\" no existe.");
    }

}