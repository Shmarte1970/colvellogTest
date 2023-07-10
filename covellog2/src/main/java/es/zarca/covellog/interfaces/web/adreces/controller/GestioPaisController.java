package es.zarca.covellog.interfaces.web.adreces.controller;

import es.zarca.covellog.application.adreces.form.AltaPaisForm;
import es.zarca.covellog.application.adreces.form.BaixaPaisForm;
import es.zarca.covellog.application.adreces.form.EditarPaisForm;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;

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
import es.zarca.covellog.interfaces.facade.adreces.facade.PaisServiceFacade;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

@DeclareRoles("ADMIN")
@Named("gestioPaisController")
@SessionScoped
public class GestioPaisController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestioPaisController.class.getName());

    //@EJB
    @Inject
    private PaisServiceFacade ejbFacade;
    private PaisDTO selected;
    private PaisLazyDataModel items;
    private AltaPaisForm altaForm;
    private EditarPaisForm editarForm;
    
    public class PaisLazyDataModel extends LazyDataModel<PaisDTO> {
        private List<PaisDTO> list;
        
        public PaisLazyDataModel(){
          //  assertIsAdmin();
            this.setRowCount(getFacade().llistarPaisosTotalCount());
        }
        
        @RolesAllowed("ADMIN")
        @Override
        public List<PaisDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
          //  assertIsAdmin();
            list = getFacade().llistarPaisos(first, pageSize, filters);
            this.setRowCount(getFacade().llistarPaisosTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(PaisDTO pais) {
            return pais.getId();
	}

	@Override
	public PaisDTO getRowData(String rowKey) {
            for (PaisDTO pais : list) {
                if (pais.getId().equals(Integer.parseInt(rowKey))) return pais;
            }
            return null;
        }
        
    }  
    
    public GestioPaisController() {
    }

    public PaisDTO getSelected() {
        return selected;
    }

    public void setSelected(PaisDTO selected) {
        this.selected = selected;
    }

    public AltaPaisForm getAltaForm() {
        return altaForm;
    }

    public void setAltaForm(AltaPaisForm altaForm) {
        this.altaForm = altaForm;
    }

    public EditarPaisForm getEditarForm() {
        return editarForm;
    }

    public void setEditarForm(EditarPaisForm editarForm) {
        this.editarForm = editarForm;
    }

    private PaisServiceFacade getFacade() {
        return ejbFacade;
    }

    public PaisDTO prepareCreate() {
        altaForm = new AltaPaisForm();
        return selected;
    }
    
    public void prepareEdit() {
        editarForm = new EditarPaisForm();
        editarForm.setId(selected.getId());
        editarForm.setNom(selected.getNom());        
        editarForm.setCodiIso2(selected.getCodiIso2());
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
                getFacade().altaPais(altaForm);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PaisCreated"));
            } catch (EJBException ex) {          
                JsfUtil.validationFailed();
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
                JsfUtil.validationFailed();
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
                getFacade().editarPais(editarForm);
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PaisUpdated"));
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
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void destroy() {
        if (selected != null) {
            try {
                getFacade().baixaPais(new BaixaPaisForm(selected.getId()));
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("es.zarca.covellog.interfaces.web.AdrecesBundle").getString("PaisDeleted"));
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

    public PaisDTO getPais(java.lang.Integer id) {
        return getFacade().buscarPerId(id);
    }
    
    public PaisLazyDataModel getItems(){
        if (items == null) {
            items = new PaisLazyDataModel();
        }  
        return items;
    }

    @FacesConverter(forClass = PaisDTO.class)
    public static class GestioPaisControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GestioPaisController controller = (GestioPaisController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "gestioPaisController");
            return controller.getPais(getKey(value));
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
            if (object instanceof PaisDTO) {
                PaisDTO o = (PaisDTO) object;
                return getStringKey(o.getId());
            } else {
                LOGGER.log(Level.SEVERE, "Pais: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), PaisDTO.class.getName()});
                return null;
            }
        }

    }

}