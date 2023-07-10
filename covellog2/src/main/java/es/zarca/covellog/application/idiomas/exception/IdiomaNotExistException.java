package es.zarca.covellog.application.idiomas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class IdiomaNotExistException extends BusinessException {

    public IdiomaNotExistException(CodigoIdioma codigoIdioma) {
        super("El idioma con ´id \"" + codigoIdioma.getString() + "\" no existe.");
    }
    
}
