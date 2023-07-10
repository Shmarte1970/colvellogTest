package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EntregaPosteriorRecogidaPrevistaException extends BusinessException {

    public EntregaPosteriorRecogidaPrevistaException() {
        super("No se puede solicitar una recogida antes de la fecha de entrega.");
    }
    
}
