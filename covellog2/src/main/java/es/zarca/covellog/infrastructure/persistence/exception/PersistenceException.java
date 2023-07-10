/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.infrastructure.persistence.exception;

import es.zarca.covellog.application.exception.*;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PersistenceException extends BusinessException {

    public PersistenceException(ConstraintViolationException ex) {
        super(convertMessage(ex));
    }
    
    public PersistenceException(String message) {
        super(message);
    }
    
    public PersistenceException(Set<ConstraintViolation<? extends Object>> violations) {
        super(convertViolations(violations));
    }
    
    static private String convertViolations(Set<ConstraintViolation<? extends Object>> violations) {
        String message = "";
        int i=1;
        for (ConstraintViolation actual : violations) {
            message = message + " " + i + ": " + actual.getPropertyPath().toString() + " " + actual.getMessage();
        }
        return message;
    }
   
    static private String convertMessage(ConstraintViolationException ex) {
        return convertViolations(ex.getConstraintViolations());
    }
    
}
