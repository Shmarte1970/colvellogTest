package es.zarca.covellog.interfaces.web.adreces.controller;

import es.zarca.covellog.application.adreces.form.AltaPoblacioForm;
import es.zarca.covellog.application.adreces.form.BaixaPoblacioForm;
import es.zarca.covellog.application.adreces.form.EditarPoblacioForm;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PoblacioDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import es.zarca.covellog.interfaces.facade.adreces.facade.PoblacioServiceFacade;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.util.ArrayList;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

@Named("gestioPoblacioController")
@SessionScoped
public class GestioPoblacioController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestioPoblacioController.class.getName());
    
    //@EJB
    @Inject
    private PoblacioServiceFacade ejbFacade;
    private PoblacioDTO selected;
    private PoblacioLazyDataModel items;
    private AltaPoblacioForm altaForm;
    private EditarPoblacioForm editarForm;
    private String selectAction="prova";
    private ActionListener actionListener;

    public ActionListener getActionListener() {
        return actionListener;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }
    
    public String getSelectAction() {
        return selectAction;
    }

    public void setSelectAction(String selectAction) {
        this.selectAction = selectAction;
    }

    
    public class PoblacioLazyDataModel extends LazyDataModel<PoblacioDTO> {
        private List<PoblacioDTO> list;
        
        public PoblacioLazyDataModel(){
            LOGGER.log(Level.INFO, "{0}.PoblacioLazyDataModel() => Es crea el PoblacioLazyDataModel", getClass().getName());
            this.setRowCount(getFacade().llistarPoblacionsTotalCount());
        }
        
        @Override
        public List<PoblacioDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            LOGGER.log(Level.INFO, "{0}.load({1},{2})", new Object[]{getClass().getName(), String.valueOf(first), String.valueOf(pageSize)});
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
    
    public GestioPoblacioController() {
    }

    public PoblacioDTO getSelected() {
        return selected;
    }

    public void setSelected(PoblacioDTO selected) {
        this.selected = selected;
    }
    
    public AltaPoblacioForm getAltaForm() {
        return altaForm;
    }

    public void setAltaForm(AltaPoblacioForm altaForm) {
        this.altaForm = altaForm;
    }

    public EditarPoblacioForm getEditarForm() {
        return editarForm;
    }

    public void setEditarForm(EditarPoblacioForm editarForm) {
        this.editarForm = editarForm;
    }

    private PoblacioServiceFacade getFacade() {
        return ejbFacade;
    }

    public PoblacioDTO prepareCreate() {
        altaForm = new AltaPoblacioForm();
        return selected;
    }
    
    public void cancelCreate() {
        altaForm = null;
    }
    
    public void prepareEdit() {
        editarForm = new EditarPoblacioForm();
        editarForm.setId(selected.getId());
        editarForm.setNom(selected.getNom());        
        editarForm.setIdPais(selected.getProvincia().getPais().getId());
        editarForm.setIdProvincia(selected.getProvincia().getId());
    }

    public void cancelEdit() {
        editarForm = null;
    }

    public void create() {
        LOGGER.log(Level.SEVERE, "CREATE");
        if (altaForm != null) {
            try {
                getFacade().altaPoblacio(altaForm);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PoblacioCreated"));
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                LOGGER.log(Level.SEVERE, msg);
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
            }
        } else {
            JsfUtil.addErrorMessage("Se ha perdido su formulario de alta.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
        
    public void update() {
        if (selected != null) {
            try {
                getFacade().editarPoblacio(editarForm);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PoblacioUpdated"));
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                LOGGER.log(Level.SEVERE, msg);
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
            }
        } else {
            JsfUtil.addErrorMessage("Seleccione una población.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroy() {
        if (selected != null) {
            try {
                getFacade().baixaPoblacio(new BaixaPoblacioForm(selected.getId()));
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PoblacioDeleted"));
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                LOGGER.log(Level.SEVERE, msg);
                if ((msg != null) && (msg.length() > 0)) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage());
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PersistenceErrorOccured"));
            }
        } else {
            JsfUtil.addErrorMessage("Seleccione una población.");
        }
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public PoblacioDTO getPoblacio(java.lang.Integer id) {
        return getFacade().buscarPerId(id);
    }
    
    public PoblacioLazyDataModel getItems(){
        if (items == null) {
            items = new PoblacioLazyDataModel();
        }  
        return items;
    }
    
    private List<ProvinciaDTO> getProvinciesPosibles(Integer selectedPais) {
        if (selectedPais == null) {
            return new ArrayList<>();
        }
        List<ProvinciaDTO> lista = getFacade().getProvinciesPosibles(selectedPais);
        LOGGER.log(Level.INFO, "GestioPoblacioController:List<ProvinciaDTO> getProvinciesPosibles() selectedPais={0} Records: {1}", new Object[]{selectedPais, lista.size()});
        return lista;
    }
    
    public List<ProvinciaDTO> getProvinciesPosiblesAlta() {
        return getProvinciesPosibles(altaForm.getIdPais());
    }
    
    public List<ProvinciaDTO> getProvinciesPosiblesEdit() {
        return getProvinciesPosibles(editarForm.getIdPais());
    }
    
    public List<PaisDTO> getPaisosPosibles() {
        List<PaisDTO> lista = getFacade().getPaisosPosibles();
        LOGGER.log(Level.INFO, "GestioPoblacioController:List<PaisDTO> getPaisosPosibles() Records: {0}", lista.size());
        return lista;
    }

    @FacesConverter(forClass = PoblacioDTO.class)
    public static class GestioPoblacioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioPoblacioController controller = (GestioPoblacioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioPoblacioController");
            return controller.getPoblacio(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PoblacioDTO) {
                PoblacioDTO o = (PoblacioDTO) object;
                return getStringKey(o.getId());
            } else {
                LOGGER.log(Level.SEVERE, "Poblacio: object {0} is of type {1}; expected type: {2}", 
                        new Object[]{object, object.getClass().getName(), PoblacioDTO.class.getName()});
                return null;
            }
        }

    }

}
