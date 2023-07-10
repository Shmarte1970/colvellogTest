package es.zarca.covellog.application.productos.familias.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class UnidadMedidaNotExistException extends BusinessException {

    public UnidadMedidaNotExistException(String id) {
        super("La unidad de medida con Id \"" + id + "\" no existe.");
    }
    
}
