package es.zarca.covellog.comercial.domain.oferta.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.comercial.domain.oferta.IOfertaLinea;
import es.zarca.covellog.comercial.domain.oferta.Oferta;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class OfertaLineaGastoAdicionalNotExistException extends BusinessException {

    public OfertaLineaGastoAdicionalNotExistException(Oferta oferta, IOfertaLinea linea, Integer lineaGastoAdicionalId) {
        super("La linea con id=\"" + linea.getId() + "\" de la oferta \"" + oferta.getFriendlyId() + "\" no tiene ninguna linea de gasto adicional con id=\"" + lineaGastoAdicionalId + "\".");
    }
    
}
