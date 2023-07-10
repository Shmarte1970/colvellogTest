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
public class ImportarPeticionsContactePendentsLockedException extends BusinessException {

    public ImportarPeticionsContactePendentsLockedException() {
        super("No puede convertir las peticiones de contacto en oportunidades porque ya hay otra conversión en curso");
    }
    
}
