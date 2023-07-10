package es.zarca.covellog.interfaces.web.adreces.controller;

import es.zarca.covellog.application.adreces.form.AltaProvinciaForm;
import es.zarca.covellog.application.adreces.form.BaixaProvinciaForm;
import es.zarca.covellog.application.adreces.form.EditarProvinciaForm;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;

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
import es.zarca.covellog.interfaces.facade.adreces.facade.ProvinciaServiceFacade;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;

@Named("gestioProvinciaController")
@SessionScoped
public class GestioProvinciaController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestioProvinciaController.class.getName());
    
    //@EJB
    @Inject
    private ProvinciaServiceFacade ejbFacade;
    private ProvinciaDTO selected;
    private ProvinciaLazyDataModel items;
    private AltaProvinciaForm altaForm;
    private EditarProvinciaForm editarForm;
    
    public class ProvinciaLazyDataModel extends LazyDataModel<ProvinciaDTO> {
        private List<ProvinciaDTO> list;
        
        public ProvinciaLazyDataModel(){
            this.setRowCount(getFacade().llistarProvinciesTotalCount());
        }
        
        @Override
        public List<ProvinciaDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            list = getFacade().llistarProvincies(first, pageSize, filters);
            this.setRowCount(getFacade().llistarProvinciesTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(ProvinciaDTO provincia) {
            return provincia.getId();
	}

	@Override
	public ProvinciaDTO getRowData(String rowKey) {
            for (ProvinciaDTO provincia : list) {
                if (provincia.getId().equals(Integer.parseInt(rowKey))) return provincia;
            }
            return null;
        }
        
    }
    
    public GestioProvinciaController() {
    }

    public ProvinciaDTO getSelected() {
        return selected;
    }

    public void setSelected(ProvinciaDTO selected) {
        this.selected = selected;
    }

    public AltaProvinciaForm getAltaForm() {
        return altaForm;
    }

    public void setAltaForm(AltaProvinciaForm altaForm) {
        this.altaForm = altaForm;
    }

    public EditarProvinciaForm getEditarForm() {
        return editarForm;
    }

    public void setEditarForm(EditarProvinciaForm editarForm) {
        this.editarForm = editarForm;
    }

    private ProvinciaServiceFacade getFacade() {
        return ejbFacade;
    }

    public ProvinciaDTO prepareCreate() {
        altaForm = new AltaProvinciaForm();
        return selected;
    }
    
    public void prepareEdit() {
        editarForm = new EditarProvinciaForm();
        editarForm.setId(selected.getId());
        editarForm.setNom(selected.getNom());
        editarForm.setCodi(selected.getCodi());
        editarForm.setCodiPostal(selected.getCodiPostal());
        editarForm.setIdPais(selected.getPais().getId());
    }
    
    public void cancelCreate() {
        altaForm = null;
    }

    public void cancelEdit() {
        editarForm = null;
    }
    
    public void create() {
        if (altaForm != null) {
            try {
                getFacade().altaProvincia(altaForm);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("ProvinciaCreated"));
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
                getFacade().editarProvincia(editarForm);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("ProvinciaUpdated"));
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
            JsfUtil.addErrorMessage("Seleccione una provincia.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroy() {
        if (selected != null) {
            try {
                getFacade().baixaProvincia(new BaixaProvinciaForm(selected.getId()));
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("ProvinciaDeleted"));
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
            JsfUtil.addErrorMessage("Seleccione un país.");
        }
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public ProvinciaDTO getProvincia(java.lang.Integer id) {
        return getFacade().buscarPerId(id);
    }
    
    public ProvinciaLazyDataModel getItems(){
        if (items == null) {
            items = new ProvinciaLazyDataModel();
        }  
        return items;
    }
    
    public List<PaisDTO> getPaisosPosibles() {
        return getFacade().getPaisosPosibles();
    }

    @FacesConverter(forClass = ProvinciaDTO.class)
    public static class GestioProvinciaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioProvinciaController controller = (GestioProvinciaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioProvinciaController");
            return controller.getProvincia(getKey(value));
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
            if (object instanceof ProvinciaDTO) {
                ProvinciaDTO o = (ProvinciaDTO) object;
                return getStringKey(o.getId());
            } else {
                LOGGER.log(Level.SEVERE, "Provincia: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), ProvinciaDTO.class.getName()});
                return null;
            }
        }

    }

}