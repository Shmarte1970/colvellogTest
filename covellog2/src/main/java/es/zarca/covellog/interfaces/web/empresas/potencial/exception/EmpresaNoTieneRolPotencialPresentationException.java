package es.zarca.covellog.interfaces.web.empresas.potencial.exception;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */

import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.exception.*;


public class EmpresaNoTieneRolPotencialPresentationException extends PresentationException { 
    
    public EmpresaNoTieneRolPotencialPresentationException(Integer idEmpresa) {
        super(crearMessageIdEmpresa(idEmpresa));
    }
    
    public EmpresaNoTieneRolPotencialPresentationException(EmpresaDto empresa) {
        super(crearMessageEmpresa(empresa));
    }
        
    private static String crearMessageIdEmpresa(Integer idEmpresa) {
        if (idEmpresa == null) {
            return "La empresa con Id \"null\" no tiene el rol potencial.";
        }
        return "La empresa con Id \"" + idEmpresa.toString() + "\" no tiene el rol potencial.";
    }
    
    private static String crearMessageEmpresa(EmpresaDto empresa) {
        if (empresa == null) {
            return "La empresa \"null\" no tiene el rol potencial.";
        }
        return "La empresa \"" + empresa.getAliasNombre() + "\" no tiene el rol potencial.";
    }
   
}