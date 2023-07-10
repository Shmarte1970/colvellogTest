/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public abstract class BusinessException extends RuntimeException { 
    public BusinessException(String message) {
        super(message);
    }
}