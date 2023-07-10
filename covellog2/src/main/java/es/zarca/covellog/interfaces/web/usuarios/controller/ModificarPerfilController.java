package es.zarca.covellog.interfaces.web.usuarios.controller;

import es.zarca.covellog.application.usuarios.form.PerfilUsuarioForm;
import es.zarca.covellog.interfaces.facade.usuarios.facade.UsuarioServiceFacade;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("modificarPerfilController")
@SessionScoped
public class ModificarPerfilController implements Serializable {
    @Inject
    private UsuarioServiceFacade ejbFacade;    
    private PerfilUsuarioForm perfilUsuarioForm = new PerfilUsuarioForm();
     
    public ModificarPerfilController() {
       
    }

    public PerfilUsuarioForm getPerfilUsuarioForm() {
        return perfilUsuarioForm;
    }

    public void setPerfilUsuarioForm(PerfilUsuarioForm perfilUsuarioForm) {
        this.perfilUsuarioForm = perfilUsuarioForm;
    }

    private UsuarioServiceFacade getFacade() {
        return ejbFacade;
    }
    
    public void prepareUpdate() {
        perfilUsuarioForm = getFacade().verPerfilUsuario();
    }

    public void guardar() {
        try {
            getFacade().modificarPerfil(perfilUsuarioForm);
            JsfUtil.addSuccessMessage("Perfil modificado correctamente.");
        } catch (EJBException ex) {
            JsfUtil.validationFailed();
            ExceptionHandler.handleException(ex);
        } catch (Exception ex) {
            JsfUtil.validationFailed();
            ExceptionHandler.handleException(ex);
        }
    }

}