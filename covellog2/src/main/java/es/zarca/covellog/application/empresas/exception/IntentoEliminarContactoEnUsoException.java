package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.contactos.Contacto;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class IntentoEliminarContactoEnUsoException extends BusinessException {

    public IntentoEliminarContactoEnUsoException(Contacto contacto, String dondeEstaEnUso) {
        super(createMessage(contacto, dondeEstaEnUso));
    }
    
    public static String createMessage(Contacto contacto, String dondeEstaEnUso) {
        if (dondeEstaEnUso != null) {
            return "No se puede eliminar el contacto \"" + contacto.getNombre() + "\" porque está en uso en \"" + dondeEstaEnUso + "\".";
        }
        return "No se puede eliminar el contacto \"" + contacto.getNombre() + "\" porque está en uso.";
    }
    
}