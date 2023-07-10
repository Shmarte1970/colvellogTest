/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.crm.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class OportunitatNotFoundException extends BusinessException {

    public OportunitatNotFoundException(int id) {
        super("No se encuentra la oportunidad con id = " + String.valueOf(id) + ".");
    }
    
}
