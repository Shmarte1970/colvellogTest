package es.zarca.covellog.application.stock.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContratoNotExistException extends BusinessException {

    public ContratoNotExistException(Integer id) {
        super("El contrato con Id \"" + id + "\" no existe.");
    }
    
}
