package es.zarca.covellog.domain.model.app.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class MalformedTagsException extends BusinessException {

    public MalformedTagsException(String tags) {
        super("Palabras clave incorrectas. Deben empezar por # y se separan con comas o espacios.");
    }
    
}
