package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContratoLineaNotExistException extends BusinessException {

    public ContratoLineaNotExistException(Integer contratoId, Integer lineaId) {
        super("El contrato con Id=\"" + contratoId + "\" no tiene ninguna linea con id=\"" + lineaId + "\".");
    }
    
    public ContratoLineaNotExistException(Integer contratoId, Stock producto) {        
        super("El contrato con Id=\"" + contratoId + "\" no tiene ninguna linea con el producto \"" + producto.getNumeroSerie() + "\".");
    }
    
}
