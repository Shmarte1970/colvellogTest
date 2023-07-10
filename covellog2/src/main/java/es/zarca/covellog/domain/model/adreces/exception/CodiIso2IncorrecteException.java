package es.zarca.covellog.domain.model.adreces.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodiIso2IncorrecteException extends BusinessException {

    public CodiIso2IncorrecteException(String codiIso2) {
        super("El código de idioma " + codiIso2 + " es incorrecto, debe tener solo 5 letras.");
    }
    
}
