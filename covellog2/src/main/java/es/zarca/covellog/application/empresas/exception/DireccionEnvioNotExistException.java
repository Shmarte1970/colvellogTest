package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DireccionEnvioNotExistException extends BusinessException {

    public DireccionEnvioNotExistException(Empresa empresa, Integer direccionEnvioId) {
        super(createMessage(empresa, direccionEnvioId));
    }
        
    static private String createMessage(Empresa empresa, Integer contactoId) {
        if (contactoId == null) {
            return "La empresa \"" + empresa.getAliasNombre() + "\" no tienen ninguna dirección de envío con id \"null\".";
        } else {
            return "La empresa \"" + empresa.getAliasNombre() + "\" no tienen ninguna dirección de envío con id \"" + contactoId.toString() + "\".";
        }
    }
}
