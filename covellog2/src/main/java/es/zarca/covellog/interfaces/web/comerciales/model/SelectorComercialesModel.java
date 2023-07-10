package es.zarca.covellog.interfaces.web.comerciales.model;

import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionListener;

/**
 *
 * @author francisco
 */
public class SelectorComercialesModel {
    private List<ComercialDto> items;
    private List<ComercialDto> selecteds = new ArrayList<>();
    private ComercialDto selected;
    private ActionListener listener;
    private String update;
    
    public List<ComercialDto> getItems() {
        return items;
    }

    public void setItems(List<ComercialDto> items) {
        this.items = items;
    }

    public List<ComercialDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ComercialDto> selecteds) {
        this.selecteds = selecteds;
    }

    public ComercialDto getSelected() {
        return selected;
    }

    public void setSelected(ComercialDto selected) {
        this.selected = selected;
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
    
}