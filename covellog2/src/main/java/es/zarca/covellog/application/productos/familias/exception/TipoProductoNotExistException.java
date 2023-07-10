package es.zarca.covellog.application.productos.familias.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class TipoProductoNotExistException extends BusinessException {

    public TipoProductoNotExistException(String id) {
        super("El tipo de producto con Id \"" + id + "\" no existe.");
    }
    
}
