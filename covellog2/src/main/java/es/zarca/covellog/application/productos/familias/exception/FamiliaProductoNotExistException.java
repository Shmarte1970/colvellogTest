package es.zarca.covellog.application.productos.familias.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FamiliaProductoNotExistException extends BusinessException {

    public FamiliaProductoNotExistException(Integer id) {
        super("La familia de producto con Id \"" + id.toString() + "\" no existe.");
    }
    
    public FamiliaProductoNotExistException(String nombre) {
        super("El usuario con nombre \"" + nombre + "\" no existe.");
    }
    
}
