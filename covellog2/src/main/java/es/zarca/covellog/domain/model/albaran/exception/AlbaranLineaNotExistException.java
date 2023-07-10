package es.zarca.covellog.domain.model.albaran.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.albaran.Albaran;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AlbaranLineaNotExistException extends BusinessException {

    public AlbaranLineaNotExistException(Albaran albaran, String referencia) {
        super("El albaran \"" + albaran.getFriendlyId() + "\" no tiene .ninguna linea con la referencia \"" + referencia + "\"");
    }
}