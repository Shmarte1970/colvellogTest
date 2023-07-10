package es.zarca.covellog.interfaces.web.helpers;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author francisco
 */
public class ExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());
    
    public static void handleException(EJBException ex) {
        String msg = "";
        Throwable cause = ex.getCause();
        if (cause != null) {
            handleException(cause);
        } else {
            LOGGER.log(Level.SEVERE, msg);
            JsfUtil.addErrorMessage(ex.getLocalizedMessage());
        }
    }
    
    public static void handleException(Throwable ex) {
        if (ex != null) {
            ex.printStackTrace();
            String location = "";
            LOGGER.log(Level.SEVERE, "FINAL EXCEPTION: {0}", ex.getMessage());
            try {
                location =  
                    "Class: " + ex.getStackTrace()[0].getClassName() +
                    "\nMethod: " + ex.getStackTrace()[0].getMethodName() +
                    "\nFile: " + ex.getStackTrace()[0].getFileName() +
                    "\nLine: " + String.valueOf(ex.getStackTrace()[0].getLineNumber());
            } catch (Exception e) {
                location ="No se puede crear location";
            }
            if (ex instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex;
                String msg = "";
                int i=1;
                for (ConstraintViolation actual : constraintViolationException.getConstraintViolations()) {
                    msg = msg + " " + i + ": " + actual.getPropertyPath().toString() + " " + actual.getMessage();
                }
                JsfUtil.showErrorDialog(msg);
                JsfUtil.validationFailed();
            } else {
                Throwable cause = ex.getCause();
                if (cause != null) {
                    handleException(cause);
                } else {
                    JsfUtil.validationFailed();
                    JsfUtil.showErrorDialog(ex.getLocalizedMessage() + "\n\n" + location);
                    //ex.printStackTrace();
                }
            }
        }
    }
}
