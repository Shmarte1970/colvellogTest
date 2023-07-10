package es.zarca.covellog.interfaces.web.empresas.empresas.model;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;

/**
 *
 * @author francisco
 */
public class EmpresaModel {
    private EmpresaDto empresa;
    
    public EmpresaModel() {
    }

    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }
    
}
