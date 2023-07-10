package es.zarca.covellog.interfaces.facade.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.util.logging.Logger;

/**
 *
 * @author francisco
 */
public class FacadeExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());
    
    private static Throwable desempaquetarExcepcion(Exception ex) {
        Throwable t = ex.getCause();
        if (t == null) {
            return ex;
        } 
        while (t.getCause() != null) {
            t = t.getCause();
            System.err.println("COJONES desempaquetarExcepcion: " + t.getClass().getName());
        }
        return t;
    }
    
    public static void handleException(Exception ex) throws BusinessException {
        ArgumentValidator.isNotNull(ex, "Excepcion nula.");
        System.err.println("COJONES handleException: " + ex.getClass().getName());
        System.err.println("COJONES handleException: " + ex.getMessage());
        if (ex.getCause() != null) {
            System.err.println("COJONES handleException: " + ex.getCause().getMessage());
        }
        System.err.println("STACK TRACE ***************************************************************************************************************");
        ex.printStackTrace();
        System.err.println("STACK TRACE ***************************************************************************************************************");
        Throwable t = desempaquetarExcepcion(ex);
        if (t instanceof BusinessException) {       
            throw (BusinessException)t;
        } /*else if (t instanceof ConstraintViolationException) {
            throw (ConstraintViolationException)t;
        } else if (t instanceof ValidationException) {
            throw (ValidationException)t;
        } else if (t instanceof EJBException) {
            Exception cause = ((EJBException) t).getCausedByException();
            if (cause instanceof ConstraintViolationException) {
                throw (ConstraintViolationException)cause;
            } else if (cause instanceof NullPointerException) {
                throw (NullPointerException)cause;
            }  
        }*/
        throw new UndefinedBussinesException(t);
    }
    
} 
