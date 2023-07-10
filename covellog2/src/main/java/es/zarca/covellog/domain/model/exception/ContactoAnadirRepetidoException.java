package es.zarca.covellog.domain.model.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.empresa.EmpresaContacto;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContactoAnadirRepetidoException extends BusinessException {

    public ContactoAnadirRepetidoException(Empresa empresa, EmpresaContacto contacto) {
        super("No se puede añadir el contacto \"" + contacto.getNombre() + "\" a la empresa \"" + empresa.getAliasNombre() + "\" porque está repetido.");
    }
    
}
