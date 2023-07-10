/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.factures.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FacturaNotExistException extends BusinessException {

    public FacturaNotExistException(Integer id) {
        super("La factura con Id=" + id.toString() + " no existe.");
    }
    
}
