package es.zarca.covellog.interfaces.web.usuarios.controller;

import es.zarca.covellog.application.usuarios.exception.PasswordsNoCoincidenException;
import es.zarca.covellog.application.usuarios.form.AltaUsuarioForm;
import es.zarca.covellog.application.usuarios.form.CambiarPasswordForm;
import es.zarca.covellog.application.usuarios.form.EditarUsuarioForm;
import es.zarca.covellog.domain.model.usuarios.Grupo;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import es.zarca.covellog.interfaces.facade.usuarios.facade.UsuarioAdminServiceFacade;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author francisco
 */
@Named("usuarioAdminServiceController")
@SessionScoped
public class UsuarioAdminServiceController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(UsuarioAdminServiceController.class.getName());

    @Inject
    private UsuarioAdminServiceFacade ejbFacade;
    private Usuario selected;
    private UsuarioLazyDataModel items;
    private AltaUsuarioForm altaForm;
    private EditarUsuarioForm editarForm;
    private CambiarPasswordForm cambiarPasswordForm;
    private List<Grupo> gruposPosibles;
    
    public class UsuarioLazyDataModel extends LazyDataModel<Usuario> {
        private List<Usuario> list;
        
        public UsuarioLazyDataModel(){
            this.setRowCount(getFacade().listarUsuariosTotalCount());
        }
        
        @Override
        public List<Usuario> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            list = getFacade().listarUsuarios(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().listarUsuariosTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(Usuario usuario) {
            return usuario.getId();
	}

	@Override
	public Usuario getRowData(String rowKey) {
            for (Usuario usuario : list) {
                if (usuario.getId().equals(Integer.parseInt(rowKey))) return usuario;
            }
            return null;
        }        
    }  
    
    public UsuarioAdminServiceController() {
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    public AltaUsuarioForm getAltaForm() {
        return altaForm;
    }

    public void setAltaForm(AltaUsuarioForm altaForm) {
        this.altaForm = altaForm;
    }

    public EditarUsuarioForm getEditarForm() {
        return editarForm;
    }

    public void setEditarForm(EditarUsuarioForm editarForm) {
        this.editarForm = editarForm;
    }

    public List<Grupo> getGruposPosibles() {
        return gruposPosibles;
    }

    public void setGruposPosibles(List<Grupo> gruposPosibles) {
        this.gruposPosibles = gruposPosibles;
    }

    public CambiarPasswordForm getCambiarPasswordForm() {
        return cambiarPasswordForm;
    }

    public void setCambiarPasswordForm(CambiarPasswordForm cambiarPasswordForm) {
        this.cambiarPasswordForm = cambiarPasswordForm;
    }
    
    private UsuarioAdminServiceFacade getFacade() {
        return ejbFacade;
    }

    public Usuario prepareCreate() {
        altaForm = new AltaUsuarioForm();
        gruposPosibles = getFacade().listarGruposPosibles();
        return selected;
    }
    
    public void prepareEdit() {
        editarForm = new EditarUsuarioForm();
        editarForm.setId(selected.getId());
        editarForm.setUsername(selected.getUsername());
        editarForm.setNombre(selected.getNombre());
        editarForm.setApellidos(selected.getApellidos());
        if (selected.getCanalesContacto() != null) {
            editarForm.setEmail(selected.getCanalesContacto().getEmail());
        }
        
        for (Grupo grupo : selected.getGrupos()) {
            editarForm.addGrupo(grupo);
        }
        gruposPosibles = getFacade().listarGruposPosibles();
    }
    
    public void cancelCreate() {
        altaForm = null;
    }

    public void cancelEdit() {
        editarForm = null;
    }
    
    public void cancelCambioPassword() {
        editarForm = null;
    }
    
    public Usuario prepareCambioPassword() {
        cambiarPasswordForm = new CambiarPasswordForm();
        return selected;
    }
    
    public void create() {
        if (altaForm != null) {
            try {
                getFacade().altaUsuario(altaForm);
                JsfUtil.addSuccessMessage("Usuario creado correctamente.");
            } catch (EJBException ex) {          
                JsfUtil.validationFailed();
                ExceptionHandler.handleException(ex);
            } catch (Exception ex) {
                JsfUtil.validationFailed();
                ExceptionHandler.handleException(ex);
            }
        } else {
            JsfUtil.addErrorMessage("Se ha perdido su formulario.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        if (editarForm == null) {
            JsfUtil.addErrorMessage("Se ha perdido su formulario.");
        } else if (selected == null) { 
            JsfUtil.addErrorMessage("Seleccione un usuario.");
        } else {           
            try {
                getFacade().modificarUsuario(editarForm);
                JsfUtil.addSuccessMessage("Usuario modificado correctamente.");
            } catch (EJBException ex) {
                JsfUtil.validationFailed();
                ExceptionHandler.handleException(ex);
            } catch (Exception ex) {
                JsfUtil.validationFailed();
                ExceptionHandler.handleException(ex);
            }
        }  
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void baja() {
        if (selected != null) {
            try {
                getFacade().bajaUsuario(selected.getId());
                selected = getFacade().buscarPorId(selected.getId());
                JsfUtil.addSuccessMessage("Usuario dado de baja correctamente");
            } catch (EJBException ex) {
                ExceptionHandler.handleException(ex);
            } catch (Exception ex) {
                ExceptionHandler.handleException(ex);
            }
        } else {
            JsfUtil.addErrorMessage("Seleccione un usuario.");
        }   
    }
    
    public void reactivar() {
        if (selected != null) {
            try {
                getFacade().reactivarUsuario(selected.getId());
                selected = getFacade().buscarPorId(selected.getId());
                JsfUtil.addSuccessMessage("Usuario reactivado correctamente");
            } catch (EJBException ex) {
                ExceptionHandler.handleException(ex);
            } catch (Exception ex) {
                ExceptionHandler.handleException(ex);
            }
        } else {
            JsfUtil.addErrorMessage("Seleccione un usuario.");
        }
    }
    
    public void cambiarPassword() {
        if ((selected != null) && (cambiarPasswordForm != null)) {
            try {
                if (!cambiarPasswordForm.getPassword().equals(cambiarPasswordForm.getPassword2())) {
                    throw new PasswordsNoCoincidenException();
                }
                getFacade().cambiarPassword(selected.getId(), cambiarPasswordForm.getPassword());
                JsfUtil.addSuccessMessage("Contraseña modificada correctamente.");
            } catch (PasswordsNoCoincidenException ex) {
                JsfUtil.addValidationErrorMessage("password", "Las contraseñas no coinciden.");
                JsfUtil.validationFailed();
            } catch (EJBException ex) {
                JsfUtil.validationFailed();
                ExceptionHandler.handleException(ex);
            } catch (Exception ex) {
                JsfUtil.validationFailed();
                ExceptionHandler.handleException(ex);
            }
        } else {
            JsfUtil.addErrorMessage("Seleccione un usuario.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return getFacade().buscarPorId(id);
    }
    
    public Grupo getGrupo(java.lang.Integer id) {
        return getFacade().buscarGrupoPorId(id);
    }
    
    public UsuarioLazyDataModel getItems(){
        if (items == null) {
            items = new UsuarioLazyDataModel();
        }  
        return items;
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioAdminServiceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioAdminServiceController controller = (UsuarioAdminServiceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioAdminServiceController");
            return controller.getUsuario(getKey(value));
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
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getId());
            } else {
                LOGGER.log(Level.SEVERE, "Usuario: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }
    
    @FacesConverter(value="conversorGrupo", forClass = Grupo.class)
    public static class GrupoAdminServiceControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioAdminServiceController controller = (UsuarioAdminServiceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioAdminServiceController");
            return controller.getGrupo(getKey(value));
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
            if (object instanceof Grupo) {
                Grupo o = (Grupo) object;
                return getStringKey(o.getId());
            } else {
                LOGGER.log(Level.SEVERE, "Grupo: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), Grupo.class.getName()});
                return null;
            }
        }

    }

}