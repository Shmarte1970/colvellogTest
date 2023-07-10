package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.contrato.Contrato;
import es.zarca.covellog.domain.model.contrato.ContratoLineaRO;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class LineaComplementoNotExistException extends BusinessException {

    public LineaComplementoNotExistException(Contrato contrato, ContratoLineaRO linea, Integer lineaGastoAdicionalId) {
        super("La linea con id=\"" + linea.getId() + "\" del contrato con Id=\"" + contrato.getId() + "\" no tiene ninguna linea de complemento con id=\"" + lineaGastoAdicionalId + "\".");
    }
    
}
