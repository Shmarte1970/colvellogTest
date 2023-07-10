package es.zarca.covellog.domain.model.empresa.cliente.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class TipoFacturacionNotExistException extends BusinessException {

    public TipoFacturacionNotExistException(String id) {
        super("El tipo de facturacion con id \"" + id + "\" no existe.");
    }
    
}
