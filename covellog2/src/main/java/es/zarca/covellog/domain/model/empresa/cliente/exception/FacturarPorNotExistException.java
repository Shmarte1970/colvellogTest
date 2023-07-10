package es.zarca.covellog.domain.model.empresa.cliente.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FacturarPorNotExistException extends BusinessException {

    public FacturarPorNotExistException(String id) {
        super("El facturar por con id \"" + id + "\" no existe.");
    }
    
}
