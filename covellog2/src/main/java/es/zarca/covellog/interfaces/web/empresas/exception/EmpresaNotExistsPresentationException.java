package es.zarca.covellog.interfaces.web.empresas.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import es.zarca.covellog.interfaces.web.exception.*;


public class EmpresaNotExistsPresentationException extends PresentationException { 
    
    public EmpresaNotExistsPresentationException(Integer idEmpresa) {
        super(crearMessageIdEmpresa(idEmpresa));
    }
    
    public EmpresaNotExistsPresentationException(String nombre) {
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