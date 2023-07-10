package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import java.util.Date;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class YaEstabaBloqueadoModelException extends BusinessException {

    public YaEstabaBloqueadoModelException(String id, Date fechaBaja) {
        super(id + ": Ya estaba bloqueado en la fecha " + DateUtil.dateToString(fechaBaja));
    }
    
}
