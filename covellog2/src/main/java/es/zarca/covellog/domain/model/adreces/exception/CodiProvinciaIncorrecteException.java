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
public class CodiProvinciaIncorrecteException extends BusinessException {

    public CodiProvinciaIncorrecteException(String codiIso2) {
        super("El código de província " + codiIso2 + " es incorrecto, debe tener solo 1 o 2 letras y en mayúsculas.");
    }
    
}
