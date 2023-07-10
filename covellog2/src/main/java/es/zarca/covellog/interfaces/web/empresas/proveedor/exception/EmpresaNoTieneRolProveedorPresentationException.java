package es.zarca.covellog.interfaces.web.empresas.proveedor.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.exception.PresentationException;


public class EmpresaNoTieneRolProveedorPresentationException extends PresentationException { 
    
    public EmpresaNoTieneRolProveedorPresentationException(Integer idEmpresa) {
        super(crearMessageIdEmpresa(idEmpresa));
    }
    
    public EmpresaNoTieneRolProveedorPresentationException(EmpresaDto empresa) {
        super(crearMessageEmpresa(empresa));
    }
        
    private static String crearMessageIdEmpresa(Integer idEmpresa) {
        if (idEmpresa == null) {
            return "La empresa con Id \"null\" no tiene el rol proveedor.";
        }
        return "La empresa con Id \"" + idEmpresa.toString() + "\" no tiene el rol proveedor.";
    }
    
    private static String crearMessageEmpresa(EmpresaDto empresa) {
        if (empresa == null) {
            return "La empresa \"null\" no tiene el rol proveedor.";
        }
        return "La empresa \"" + empresa.getAliasNombre() + "\" no tiene el rol proveedor.";
    }
   
}