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
public class ProvinciaNotExistException extends BusinessException {

    public ProvinciaNotExistException(Integer idProvincia) {
        super("La província con Id=" + idProvincia.toString() + " no existe.");
    }
    
}
