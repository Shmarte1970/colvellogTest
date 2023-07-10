package es.zarca.covellog.interfaces.web.empresas.contactos.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("contactoEditController")
@ViewScoped
public class ContactoEditController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContactoEditController.class.getName());
    
    private Integer empresaId;
    private ContactoDto contacto = new ContactoDto();
    private ActionListener listener;
    private String update = "";

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public ContactoDto getContacto() {
        return contacto;
    }

    public void setContacto(ContactoDto contacto) {
        this.contacto = contacto;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
    public void prepareEdit(Integer empresaId, ContactoDto contacto) {
        this.empresaId = empresaId;
        this.contacto = contacto;
    }
    
    public void prepareNew(Integer empresaId) {
        this.empresaId = empresaId;
        this.contacto = new ContactoDto();
    }
    
    public void guardar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (listener != null) {
                listener.processAction(null);
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }        
    }
    
    public void cancelar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contacto = null;
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }        
    }

}