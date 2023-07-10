package es.zarca.covellog.interfaces.web.empresas.contactos.model;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionListener;

/**
 *
 * @author francisco
 */
public class EmpresaContactosModel implements Serializable {
    private Integer empresaId = 0;
    private List<ContactoDto> contactos = new ArrayList();
    private List<ContactoDto> contactosNoEditables = new ArrayList();
    private List<ContactoDto> contactosNoEliminables = new ArrayList();
    private String update;
    private ActionListener listener;
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public List<ContactoDto> getContactosNoEditables() {
        return contactosNoEditables;
    }

    public void setContactosNoEditables(List<ContactoDto> contactosNoEditables) {
        this.contactosNoEditables = contactosNoEditables;
    }

    public List<ContactoDto> getContactosNoEliminables() {
        return contactosNoEliminables;
    }

    public void setContactosNoEliminables(List<ContactoDto> contactosNoEliminables) {
        this.contactosNoEliminables = contactosNoEliminables;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
 
}