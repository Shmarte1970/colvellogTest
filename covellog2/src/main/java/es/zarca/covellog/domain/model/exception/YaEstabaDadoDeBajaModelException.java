package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import java.util.Date;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class YaEstabaDadoDeBajaModelException extends BusinessException {

    public YaEstabaDadoDeBajaModelException(String id, Date fechaBaja) {
        super(id + ": Ya estaba dado de baja en la fecha " + DateUtil.dateToString(fechaBaja));
    }
    
}
