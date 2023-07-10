package es.zarca.covellog.domain.model.empresa.proveedor.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodigoProveedorIncorrectoException extends BusinessException {

    public CodigoProveedorIncorrectoException(String codigo) {
        super("El código de proveedor " + codigo + " es incorrecto, el formato correcto es PRXXXXXX.");
    }
    
}
