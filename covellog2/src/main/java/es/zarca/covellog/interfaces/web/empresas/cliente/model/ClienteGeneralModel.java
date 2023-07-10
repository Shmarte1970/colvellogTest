package es.zarca.covellog.interfaces.web.empresas.cliente.model;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoClienteDto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ClienteGeneralModel implements Serializable {

    private EmpresaDto empresa;
    private List<TipoClienteDto> tiposClientePosibles;

    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }

    public List<TipoClienteDto> getTiposClientePosibles() {
        return tiposClientePosibles;
    }

    public void setTiposClientePosibles(List<TipoClienteDto> tiposClientePosibles) {
        this.tiposClientePosibles = tiposClientePosibles;
    }

}