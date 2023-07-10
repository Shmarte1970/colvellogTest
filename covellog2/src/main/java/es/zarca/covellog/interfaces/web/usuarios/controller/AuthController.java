package es.zarca.covellog.interfaces.web.usuarios.controller;

import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
/**
 *
 * @author francisco
 */
@Named("authController")
@SessionScoped
public class AuthController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());
    private String username;
    private String password;
    private Integer intentos = 0;
    
    private static HttpServletRequest getRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        return (HttpServletRequest) externalContext.getRequest();
    }
    
    public String login() {  
        HttpServletRequest request = getRequest();
        if (request.getUserPrincipal() != null) {
            password = "";
            return "/index.xhtml?faces-redirect=true";
        }
        try {
            request.login(username, password);
            password = "";
        } catch (ServletException e) {
            intentos++;
            LOGGER.log(Level.SEVERE, "Usuario o contrase\u00f1a incorrectos ({0} intentos): {1}", new Object[]{intentos.toString(), username});
            JsfUtil.addErrorMessage("Usuario o contraseña incorrectos.");
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            return "/auth/login.xhtml?faces-redirect=true";     
        }               
        Principal principal = request.getUserPrincipal();  
        LOGGER.log(Level.INFO, "Logged: {0}", principal.getName());
        return "/index.xhtml?faces-redirect=true";  
    }  
    
    public void loginAjax() {
        HttpServletRequest request = getRequest();
        if (request.getUserPrincipal() == null) {
            try {  
                request.login(username, password);
                password = "";
                Principal principal = request.getUserPrincipal();  
                LOGGER.log(Level.INFO, "Logged: {0}", principal.getName());
            } catch (ServletException e) {                
                intentos++;
                LOGGER.log(Level.SEVERE, "Usuario o contrase\u00f1a incorrectos ({0} intentos): {1}", new Object[]{intentos.toString(), username});
                JsfUtil.addErrorMessage("Usuario o contraseña incorrectos.");
                JsfUtil.validationFailed();
            }     
        }
    }
    
    public String logout() {
        String result="/auth/login.xhtml?faces-redirect=true";
        HttpServletRequest request = getRequest();
        try {
            request.logout();
        } catch (ServletException e) {
            LOGGER.log(Level.SEVERE, "El logout ha fallado.", e);
            JsfUtil.addErrorMessage("El logout ha fallado.");
            result = "/index.xhtml";
        }
        return result;
    }
   
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }  

    public String getPassword() {
        return password;
    }
   
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }
         
}
