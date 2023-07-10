package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContactoEliminarInexistenteException extends BusinessException {

    public ContactoEliminarInexistenteException(Empresa empresa, Contacto contacto) {
        super("No se puede eliminar el contacto \"" + contacto.getNombre() + "\" de la empresa \"" + empresa.getAliasNombre() + "\" porque no existe.");
    }
    
}
