package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;
import es.zarca.covellog.domain.model.empresa.Empresa;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EmpresaNoTieneClienteException extends BusinessException {

    public EmpresaNoTieneClienteException(Empresa empresa) {
        super(crearMessage(empresa));
    }
    
    private static String crearMessage(Empresa empresa) {
        return "La empresa con nombre \"" + empresa.getAliasNombre() + "\" no es un cliente.";
    }
}
