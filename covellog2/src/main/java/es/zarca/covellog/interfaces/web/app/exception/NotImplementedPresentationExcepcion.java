package es.zarca.covellog.interfaces.web.app.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import es.zarca.covellog.interfaces.web.exception.*;


public class NotImplementedPresentationExcepcion extends PresentationException { 
    public NotImplementedPresentationExcepcion(String message) {
        super("Not Implemented: " + message);
    }
}