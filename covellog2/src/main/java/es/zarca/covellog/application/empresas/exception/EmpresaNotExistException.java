package es.zarca.covellog.application.empresas.exception;

import es.zarca.covellog.application.exception.BusinessException;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class EmpresaNotExistException extends BusinessException {

    public EmpresaNotExistException(Integer idEmpresa) {
        super(crearMessageIdEmpresa(idEmpresa));
    }
    
    public EmpresaNotExistException(String nombre) {
        super(crearMessageNombre(nombre));
    }
    
    private static String crearMessageIdEmpresa(Integer idEmpresa) {
        if (idEmpresa == null) {
            return "La empresa con Id \"null\" no existe.";
        }
        return "La empresa con Id \"" + idEmpresa.toString() + "\" no existe.";
    }
    
    private static String crearMessageNombre(String nombre) {
        if (nombre == null) {
            return "La empresa con nombre \"null\" no existe.";
        }
        return "La empresa con nombre \"" + nombre + "\" no existe.";
    }
}
