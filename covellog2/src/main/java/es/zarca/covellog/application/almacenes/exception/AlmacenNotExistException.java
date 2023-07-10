package es.zarca.covellog.application.almacenes.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AlmacenNotExistException extends BusinessException {

    public AlmacenNotExistException(Integer id) {
        super("El almacen con Id \"" + id + "\" no existe.");
    }
    
}
