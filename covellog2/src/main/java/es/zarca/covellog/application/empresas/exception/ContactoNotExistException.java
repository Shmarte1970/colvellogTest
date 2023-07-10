package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContactoNotExistException extends BusinessException {

    public ContactoNotExistException(Empresa empresa, Integer contactoId) {
        super(createMessage(empresa, contactoId));
    }
        
    static private String createMessage(Empresa empresa, Integer contactoId) {
        if (contactoId == null) {
            return "La empresa \"" + empresa.getAliasNombre() + "\" no tienen ningún contacto con id \"null\".";
        } else {
            return "La empresa \"" + empresa.getAliasNombre() + "\" no tienen ningún contacto con id \"" + contactoId.toString() + "\".";
        }
    }
}
