package es.zarca.covellog.interfaces.web.empresas.proveedor.model;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ProveedorDto;
import java.util.List;

/**
 *
 * @author usuario
 */
public class ProveedorModel {
    private Integer empresaId;
    private ProveedorDto proveedor;
    private List<ContactoDto> contactosPosibles;

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public ProveedorDto getProveedor() {
        return proveedor;
    }

    public void setProveedor(ProveedorDto proveedor) {
        this.proveedor = proveedor;
    }

    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }

}
