package es.zarca.covellog.interfaces.web.usuarios.controller;

import es.zarca.covellog.application.usuarios.exception.PasswordsNoCoincidenException;
import es.zarca.covellog.interfaces.facade.usuarios.facade.UsuarioServiceFacade;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@Named("modificarPasswordController")
@SessionScoped
public class ModificarPasswordController implements Serializable {
    @Inject
    private UsuarioServiceFacade ejbFacade;    
    @Size(min = 4, max = 40)
    private String passwordViejo;
    @Size(min = 4, max = 40)
    private String password;
    @Size(min = 4, max = 40)
    private String password2;
    
    public ModificarPasswordController() {
       
    }

    public String getPasswordViejo() {
        return passwordViejo;
    }

    public void setPasswordViejo(String passwordViejo) {
        this.passwordViejo = passwordViejo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    private UsuarioServiceFacade getFacade() {
        return ejbFacade;
    }
    
    private void resetForm() {
        passwordViejo = "";
        password = "";
        password2 = "";
    }

    public void cambiarPassword() {
        try {
            if (!password.equals(password2)) {
                resetForm();
                throw new PasswordsNoCoincidenException();
            }
            getFacade().cambiarPassword(passwordViejo, password);
            resetForm();
            JsfUtil.addSuccessMessage("Contraseña modificada correctamente.");
        } catch (PasswordsNoCoincidenException ex) {
            JsfUtil.addValidationErrorMessage("password", "Las contraseñas no coinciden.");
            JsfUtil.validationFailed();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        }
    }

}