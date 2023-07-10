/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.adreces.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.adreces.pais.CodiIsoPais2;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PaisNotExistException extends BusinessException {

    public PaisNotExistException(Integer idPais) {
        super("El país con Id=" + idPais.toString() + " no existe.");
    }
    
    public PaisNotExistException(CodiIsoPais2 codi) {
        super("El país con código=" + codi.toString() + " no existe.");
    }
    
}
