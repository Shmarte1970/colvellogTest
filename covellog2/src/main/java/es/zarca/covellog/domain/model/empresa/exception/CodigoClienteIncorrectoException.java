package es.zarca.covellog.domain.model.empresa.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodigoClienteIncorrectoException extends BusinessException {

    public CodigoClienteIncorrectoException(String codigo) {
        super("El código de cliente \"" + codigo + "\" es incorrecto, el formato correcto es CLXXXXXX.");
    }
    
}
