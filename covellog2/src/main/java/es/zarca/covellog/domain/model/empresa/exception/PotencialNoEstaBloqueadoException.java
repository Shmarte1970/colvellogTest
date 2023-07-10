package es.zarca.covellog.domain.model.empresa.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PotencialNoEstaBloqueadoException extends BusinessException {

    public PotencialNoEstaBloqueadoException(Empresa empresa) {
        super("El potencial de la empresa \"" + 
            empresa.getAliasNombre() + 
            "\" no esta bloqueado."
        );
    }
    
}
