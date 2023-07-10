package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EmpresaMismoNombreExistException extends BusinessException {

    public EmpresaMismoNombreExistException(String nombre) {
        super("Ya existe otra empresa con el nombre \"" + nombre + "\"");
    }
    
}
