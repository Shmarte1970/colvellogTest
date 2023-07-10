package es.zarca.covellog.application.exception;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class UndefinedBussinesException extends BusinessException {
    
    static private String createMessage(Throwable exception) {
        ArgumentValidator.isNotNull(exception, "Excepcion nula.");
       /* if (exception == null) {
            return "null";
        }*/
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
            String msg = "";
            int i=1;
            for (ConstraintViolation actual : constraintViolationException.getConstraintViolations()) {
                msg = msg + " " + i + ": " + actual.getPropertyPath().toString() + " " + actual.getMessage();
            }
            return msg;
        } else if (exception instanceof PersistenceException) {
            Throwable cause = exception.getCause();
            if (cause instanceof ConstraintViolationException) { 
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception.getCause();
                String msg = "";
                int i=1;
                for (ConstraintViolation actual : constraintViolationException.getConstraintViolations()) {
                    msg = msg + " " + i + ": " + actual.getPropertyPath().toString() + " " + actual.getMessage();
                }
                return msg;
            }
        }
        return exception.getMessage();
    }

    public UndefinedBussinesException(Throwable exception) {
        super("Undefined Bussines Exception:" + createMessage(exception));
        initCause(exception);
    }
    
    public UndefinedBussinesException(String message) {        
        super("Undefined Bussines Exception:" + message);
    }

}
