/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodiPostalIncorrecteException extends BusinessException {

    public CodiPostalIncorrecteException(String codiPostal) {
        super("El código postal \"" + codiPostal + "\" es incorrecto.");
    }
    
}
