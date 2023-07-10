package es.zarca.covellog.interfaces.web.empresas.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import es.zarca.covellog.interfaces.web.exception.*;


public class EmpresaNotSelectedPresentationException extends PresentationException { 
    public EmpresaNotSelectedPresentationException() {
        super("No ha seleccionado ninguna empresa.");
    }
}