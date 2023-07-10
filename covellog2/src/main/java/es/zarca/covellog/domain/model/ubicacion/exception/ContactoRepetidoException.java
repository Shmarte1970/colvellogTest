package es.zarca.covellog.domain.model.ubicacion.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContactoRepetidoException extends BusinessException {

    public ContactoRepetidoException(Ubicacion ubicacion, Contacto contacto) {
        super("No se puede añadir el contacto \"" + contacto.getNombre() + "\" a la ubicación \"" + ubicacion.getNombre() + "\" porque está repetido.");
    }
    
}
