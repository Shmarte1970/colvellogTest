/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PoblacioNotExistException extends BusinessException {

    public PoblacioNotExistException(Integer idPoblacion) {
        super("La población con Id=" + idPoblacion.toString() + " no existe.");
    }
    
}
