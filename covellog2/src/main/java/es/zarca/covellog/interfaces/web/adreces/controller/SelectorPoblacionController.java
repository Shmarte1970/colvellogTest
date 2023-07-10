package es.zarca.covellog.interfaces.web.adreces.controller;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PoblacioDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import es.zarca.covellog.interfaces.facade.adreces.facade.PoblacioServiceFacade;
import es.zarca.covellog.interfaces.web.app.controller.DialogosController;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionListener;

@Named("selectorPoblacionController")
@SessionScoped
public class SelectorPoblacionController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorPoblacionController.class.getName());
    
    //@EJB
    @Inject
    private PoblacioServiceFacade facade;
    @Inject
    private DialogosController dialogosController;
    private PoblacioLazyDataModel items;
    private PoblacioDTO selected;
    private ActionListener listener;
    private String update;
    
    public SelectorPoblacionController() {
    }
    
    private PoblacioServiceFacade getFacade() {
        return facade;
    }
    
    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
    
    public PoblacioLazyDataModel getItems(){
        if (items == null) {
            items = new PoblacioLazyDataModel();
        }  
        return items;
    }
    
    public class PoblacioLazyDataModel extends LazyDataModel<PoblacioDTO> {
        private List<PoblacioDTO> list;
        
        public PoblacioLazyDataModel(){
            this.setRowCount(getFacade().llistarPoblacionsTotalCount());
        }
        
        @Override
        public List<PoblacioDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            list = getFacade().llistarPoblacions(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().llistarPoblacionsTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(PoblacioDTO poblacio) {
            return poblacio.getId();
	}

	@Override
	public PoblacioDTO getRowData(String rowKey) {
            for (PoblacioDTO poblacio : list) {
                if (poblacio.getId().equals(Integer.parseInt(rowKey))) return poblacio;
            }
            return null;
        }
    }
    
    public PoblacioDTO getSelected() {
        return selected;
    }

    public void setSelected(PoblacioDTO selected) {
        this.selected = selected;
    }
    
    public void seleccionar(javax.faces.event.ActionEvent event) {
        if (listener != null) {
            listener.processAction(event);
        }
    }
    
    public void cancelar() {
        System.out.println("CANCELAR");
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
    public void prepareUpdate(PoblacioDTO poblacion, String prefijo, String id, String id2) {
       this.selected = poblacion;
       this.update = prefijo + ":" + id + ":" + id2;
     /*  String id = dialogosController.nuevoDialogo("/empresas/ajax/direccion-edit.xhtml", direccion);
        direccionEditController.addAceptarListener(
            new DireccionEditAceptarListener() {
                @Override
                public void onAceptar(DireccionDto direccion) {
                    updateDireccionFiscal(); 
                }
            },
            direccion, 
            "Pestanas:EmpresaBean:direccionFiscal:direccion:direccion"
        );*/
    }
    
    public void prepareUpdate() {
       this.selected = null;
    }
 
}
