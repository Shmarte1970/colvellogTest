package es.zarca.covellog.domain.model.ubicacion.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class UbicacionNotExistException extends BusinessException {

    public UbicacionNotExistException(Integer id) {
        super("La ubicacion con Id \"" + id + "\" no existe.");
    }
    
}
