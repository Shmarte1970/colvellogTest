package es.zarca.covellog.comercial.domain.oferta.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class OfertaLineaNotExistException extends BusinessException {

    public OfertaLineaNotExistException(Integer ofertaId, Integer lineaId) {
        super("El oferta con Id=\"" + ofertaId + "\" no tiene ninguna linea con id=\"" + lineaId + "\".");
    }
    
    public OfertaLineaNotExistException(Integer ofertaId, Stock producto) {        
        super("El oferta con Id=\"" + ofertaId + "\" no tiene ninguna linea con el producto \"" + producto.getNumeroSerie() + "\".");
    }
    
}
