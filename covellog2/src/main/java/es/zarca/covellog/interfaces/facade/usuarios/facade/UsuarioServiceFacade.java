package es.zarca.covellog.interfaces.facade.usuarios.facade;

import es.zarca.covellog.application.usuarios.form.PerfilUsuarioForm;


/**
 *
 * @author francisco
 */
public interface UsuarioServiceFacade {
    
    void modificarPerfil(PerfilUsuarioForm form);
    void cambiarPassword(String passwordViejo, String passwordNuevo);
    PerfilUsuarioForm verPerfilUsuario();
    
}
