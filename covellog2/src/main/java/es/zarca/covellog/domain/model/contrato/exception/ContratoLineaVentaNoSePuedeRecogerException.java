package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContratoLineaVentaNoSePuedeRecogerException extends BusinessException {

    public ContratoLineaVentaNoSePuedeRecogerException(Integer contratoId, Integer lineaId) {
        super("La linea con id=\"" + lineaId + "\" del contrato con Id=\"" + contratoId + "\" no se puede recoger porque es una venta.");
    }
    
    public ContratoLineaVentaNoSePuedeRecogerException(Integer contratoId, Stock producto) {        
        super("El producto \"" + producto.getNumeroSerie() + "\" del contrato con Id=\"" + contratoId + "\" no se puede recoger porque es una venta.");
    }
    
}
