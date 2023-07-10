package es.zarca.covellog.application.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PartialBussinesException extends BusinessException {
    
    public PartialBussinesException(int fallos, int total, String message) {        
        super("Han fallado " + String.valueOf(fallos) + "/" + String.valueOf(total) + "\n" + message);
    }
    
}
