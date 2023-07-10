package es.zarca.covellog.interfaces.web.empresas.cliente.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.exception.*;


public class EmpresaNoTieneRolClientePresentationException extends PresentationException { 
    
    public EmpresaNoTieneRolClientePresentationException(Integer idEmpresa) {
        super(crearMessageIdEmpresa(idEmpresa));
    }
    
    public EmpresaNoTieneRolClientePresentationException(EmpresaDto empresa) {
        super(crearMessageEmpresa(empresa));
    }
        
    private static String crearMessageIdEmpresa(Integer idEmpresa) {
        if (idEmpresa == null) {
            return "La empresa con Id \"null\" no tiene el rol cliente.";
        }
        return "La empresa con Id \"" + idEmpresa.toString() + "\" no tiene el rol cliente.";
    }
    
    private static String crearMessageEmpresa(EmpresaDto empresa) {
        if (empresa == null) {
            return "La empresa \"null\" no tiene el rol cliente.";
        }
        return "La empresa \"" + empresa.getAliasNombre() + "\" no tiene el rol cliente.";
    }
   
}