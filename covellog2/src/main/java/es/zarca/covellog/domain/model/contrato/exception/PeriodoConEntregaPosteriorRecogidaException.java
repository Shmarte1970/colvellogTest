package es.zarca.covellog.domain.model.contrato.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import java.util.Date;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PeriodoConEntregaPosteriorRecogidaException extends BusinessException {

    public PeriodoConEntregaPosteriorRecogidaException(Date fechaEntrega, Date fechaDevolucion) {
        super(
            "El periodo de alquiler no puede tener fecha de entrega \"" + 
            DateUtil.dateTimeToString(fechaEntrega) + 
            "\" posterior a la fecha de recogida \"" +
            DateUtil.dateTimeToString(fechaDevolucion) +
            "\"."
        );
    }
    
}
