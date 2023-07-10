package es.zarca.covellog.interfaces.web.empresas.cliente.model;

import java.io.Serializable;

/**
 *
 * @author francisco
 */
public class ClienteModel implements Serializable {
    private Integer empresaId;

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
}