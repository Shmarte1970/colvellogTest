/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.idiomas.exception;

import es.zarca.covellog.application.exception.*;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class CodiIdiomaIncorrecteException extends BusinessException {

    public CodiIdiomaIncorrecteException(String codiIdioma) {
        super("El código de idioma " + codiIdioma + " es incorrecto, debe ser del estilo \"es_ES\".");
    }
    
}
