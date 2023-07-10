package es.zarca.covellog.domain.model.empresa.exception;

import es.zarca.covellog.application.exception.*;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EmpresaNoEsClienteException extends BusinessException {

    public EmpresaNoEsClienteException(Empresa empresa) {
        super("La empresa \"" + empresa.getAliasNombre() + "\" no es cliente.");
    }
    
}
