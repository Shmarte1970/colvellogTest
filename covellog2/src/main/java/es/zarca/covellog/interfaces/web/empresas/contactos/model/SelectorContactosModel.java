package es.zarca.covellog.interfaces.web.empresas.contactos.model;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionListener;

/**
 *
 * @author francisco
 */
public class SelectorContactosModel {
    private List<ContactoDto> items = new ArrayList<>();
    private List<ContactoDto> selecteds = new ArrayList<>();
    private ContactoDto selected;
    private ActionListener listener;
    private String update = "";
    private String updateId = "";
    private Integer empresaId;

    public SelectorContactosModel() {
    }

    public List<ContactoDto> getItems() {
        return items;
    }

    public void setItems(List<ContactoDto> items) {
        this.items = items;
    }

    public List<ContactoDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ContactoDto> selecteds) {
        this.selecteds = selecteds;
    }

    public ContactoDto getSelected() {
        if (selecteds.isEmpty()) {
            return null;
        }
        return selecteds.get(0);
    }

    public void setSelected(ContactoDto selected) {
        //this.selected = selected;
        List<ContactoDto> aux = new ArrayList<>();
        if (selected != null) {
            aux.add(selected);
        }
        setSelecteds(aux);
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

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    
}
