package es.zarca.covellog.interfaces.web.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

public class PresentationException extends RuntimeException { 
    public PresentationException(String message) {
        super(message);
    }
}