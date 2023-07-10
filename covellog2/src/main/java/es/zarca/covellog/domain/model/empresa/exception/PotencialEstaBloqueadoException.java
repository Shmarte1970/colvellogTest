package es.zarca.covellog.domain.model.empresa.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PotencialEstaBloqueadoException extends BusinessException {

    public PotencialEstaBloqueadoException(Empresa empresa) {
        super("El potencial de la empresa \"" + 
            empresa.getAliasNombre() + 
            "\" ya estaba bloqueado desde \"" + 
            DateUtil.dateTimeToString(empresa.getPotencial().getFechaBloqueo()) + "\""
        );
    }
    
}
