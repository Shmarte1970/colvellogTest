package es.zarca.covellog.interfaces.web.empresas.potencial.model;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.PotencialDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;

/**
 *
 * @author francisco
 */
public class PotencialModel implements Serializable {
    private Integer empresaId;
    private PotencialDto potencial;
    private List<ContactoDto> contactosPosibles;
    private UIComponent contactoUI;
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
    public PotencialDto getPotencial() {
        return potencial;
    }

    public void setPotencial(PotencialDto potencial) {
        this.potencial = potencial;
    }

   
    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }

    public UIComponent getContactoUI() {
        return contactoUI;
    }

    public void setContactoUI(UIComponent contactoUI) {
        this.contactoUI = contactoUI;
    }
    
}