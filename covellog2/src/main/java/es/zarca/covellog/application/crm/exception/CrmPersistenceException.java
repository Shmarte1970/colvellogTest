/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.crm.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CrmPersistenceException extends BusinessException {

    public CrmPersistenceException(Exception ex) {
        super("Error al guardar los datos: " + ex.getMessage());
    }
    
    public CrmPersistenceException(String message) {
        super("Error al guardar los datos: " + message);
    }
    
}
