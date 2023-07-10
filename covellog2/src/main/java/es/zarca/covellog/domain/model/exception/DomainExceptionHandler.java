package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francisco
 */
public class DomainExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());
    
    public static void handleException(Exception ex) throws BusinessException {
        LOGGER.log(Level.SEVERE, "DomainExceptionHandler: {0}", ex.getMessage());
        ex.printStackTrace();
        if (ex instanceof BusinessException) {            
            throw (BusinessException)ex;
        } 
        if (ex instanceof IllegalArgumentException) {            
            throw new UndefinedBussinesException(ex.getMessage() + "cccc");
        } 
        
        throw new UndefinedBussinesException("DomainExceptionHandler: " + ex.getMessage());
    }
    
}
