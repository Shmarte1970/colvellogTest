package es.zarca.covellog.application.exception;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francisco
 */
public class AppExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());
    
    public static void handleException(Exception ex) throws BusinessException {
        ex.printStackTrace();
        ArgumentValidator.isNotNull(ex, "Excepcion nula.");
        LOGGER.log(Level.SEVERE, "AppExceptionHandler: {0}", ex.getMessage());
        if (ex instanceof BusinessException) {            
            throw (BusinessException)ex;
        } 
        if (ex instanceof IllegalArgumentException) {            
            throw new UndefinedBussinesException(ex.getMessage());
        } 
        throw new UndefinedBussinesException("AppExceptionHandler: " + ex.getMessage());
    }
    
}
