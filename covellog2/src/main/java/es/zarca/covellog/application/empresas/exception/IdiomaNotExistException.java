package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class IdiomaNotExistException extends BusinessException {

    public IdiomaNotExistException(CodigoIdioma codigoIdioma) {
        super("El idioma con Id \"" + codigoIdioma.toString() + "\" no existe.");
    }
    
}
