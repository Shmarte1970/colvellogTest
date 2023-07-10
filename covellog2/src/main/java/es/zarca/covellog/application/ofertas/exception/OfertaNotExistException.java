package es.zarca.covellog.application.ofertas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import java.util.logging.Logger;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class OfertaNotExistException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public OfertaNotExistException(Integer id) {
        super("La oferta con Id \"" + id + "\" no existe.");
    }
    
    
}
