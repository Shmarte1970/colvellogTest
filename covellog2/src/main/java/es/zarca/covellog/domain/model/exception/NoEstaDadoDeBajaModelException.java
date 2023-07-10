package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import java.util.Date;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class NoEstaDadoDeBajaModelException extends BusinessException {

    public NoEstaDadoDeBajaModelException(String id) {
        super(id + ": No esta dado de baja");
    }
    
}
