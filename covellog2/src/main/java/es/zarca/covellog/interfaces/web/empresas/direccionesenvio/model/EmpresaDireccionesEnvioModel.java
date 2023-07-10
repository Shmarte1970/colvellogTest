package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.model;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class EmpresaDireccionesEnvioModel implements Serializable {
    private Integer empresaId = 0;
    private List<DireccionEnvioDto> direccionesEnvio = new ArrayList();
    private List<DireccionEnvioDto> direccionesEnvioNoEditables = new ArrayList();
    private List<DireccionEnvioDto> direccionesEnvioNoEliminables = new ArrayList();
    private String update;
    private List<ContactoDto> contactosPosibles = new ArrayList();

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public List<DireccionEnvioDto> getDireccionesEnvio() {
        return direccionesEnvio;
    }

    public void setDireccionesEnvio(List<DireccionEnvioDto> direccionesEnvio) {
        this.direccionesEnvio = direccionesEnvio;
    }

    public List<DireccionEnvioDto> getDireccionesEnvioNoEditables() {
        return direccionesEnvioNoEditables;
    }

    public void setDireccionesEnvioNoEditables(List<DireccionEnvioDto> direccionesEnvioNoEditables) {
        this.direccionesEnvioNoEditables = direccionesEnvioNoEditables;
    }

    public List<DireccionEnvioDto> getDireccionesEnvioNoEliminables() {
        return direccionesEnvioNoEliminables;
    }

    public void setDireccionesEnvioNoEliminables(List<DireccionEnvioDto> direccionesEnvioNoEliminables) {
        this.direccionesEnvioNoEliminables = direccionesEnvioNoEliminables;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }
    
}