package es.zarca.covellog.application.productos.familias.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class TipoProductoClaseNotExistException extends BusinessException {

    public TipoProductoClaseNotExistException(String id) {
        super("La clase de producto con Id \"" + id + "\" no existe.");
    }
    
}
