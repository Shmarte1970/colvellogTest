package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PeriodoConRecogidaSinEntregaException extends BusinessException {

    public PeriodoConRecogidaSinEntregaException() {
        super("El periodo de alquiler no puede tener fecha de recogida sin fecha de entrega.");
    }
    
}
