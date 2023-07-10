package es.zarca.covellog.interfaces.facade.usuarios.facade.internal;

import es.zarca.covellog.application.usuarios.UsuarioService;
import es.zarca.covellog.application.usuarios.form.PerfilUsuarioForm;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import es.zarca.covellog.interfaces.facade.usuarios.facade.UsuarioServiceFacade;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultUsuarioServiceFacade implements UsuarioServiceFacade {

    @Inject
    private UsuarioService usuarioService;

    @Override
    public void modificarPerfil(PerfilUsuarioForm form) {
        try {
            usuarioService.modificarPerfil(form);
        } catch (ConstraintViolationException ex) {
            String msg = "";
            int i=1;
            for (ConstraintViolation actual : ex.getConstraintViolations()) {
                msg = msg + " " + i + ": " + actual.getPropertyPath().toString() + " " + actual.getMessage();
            }
        }
    }

    @Override
    public void cambiarPassword(String passwordViejo, String passwordNuevo) {
        usuarioService.cambiarPassword(passwordViejo, passwordNuevo);
    }

    @Override
    public PerfilUsuarioForm verPerfilUsuario() {
        Usuario usuario = usuarioService.getUsuario();
        PerfilUsuarioForm form = new PerfilUsuarioForm();
        form.setUsername(usuario.getUsername());
        form.setNombre(usuario.getNombre());
        form.setApellidos(usuario.getApellidos());
        if (usuario.getCanalesContacto() != null) {
            form.setEmail(usuario.getCanalesContacto().getEmail());
        }
        return form;
    }
    
}
