package es.zarca.covellog.interfaces.web.empresas.cliente.model;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author francisco
 */
 public class ClienteFacturacionModel implements Serializable {
    private Integer empresaId;
    private DetalleFacturacionDto detalleFacturacion;
    private List<ContactoDto> contactosPosibles;

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public DetalleFacturacionDto getDetalleFacturacion() {
        return detalleFacturacion;
    }

    public void setDetalleFacturacion(DetalleFacturacionDto detalleFacturacion) {
        this.detalleFacturacion = detalleFacturacion;
    }

    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }

}