package es.zarca.covellog.application.usuarios.internal;

import es.zarca.covellog.domain.model.usuarios.Usuario;
import es.zarca.covellog.domain.model.usuarios.UsuarioRepository;
import es.zarca.covellog.infrastructure.persistence.exception.PersistenceException;
import java.security.Principal;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidatorFactory;
import es.zarca.covellog.application.usuarios.UsuarioService;
import es.zarca.covellog.application.usuarios.exception.PasswordIncorrectoException;
import es.zarca.covellog.application.usuarios.exception.UsernameYaExisteException;
import es.zarca.covellog.application.usuarios.exception.UsuarioNotExistException;
import es.zarca.covellog.application.usuarios.form.PerfilUsuarioForm;
import es.zarca.covellog.domain.model.empresa.CanalesContacto;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultUsuarioService implements UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;
    @Resource 
    SessionContext ctx;
    @Inject
    private ValidatorFactory validatorFactory;

    @Override
    public void modificarPerfil(PerfilUsuarioForm form) {
        Principal callerPrincipal = ctx.getCallerPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(callerPrincipal.getName());
        if (usuario == null) {
            throw new UsuarioNotExistException(callerPrincipal.getName());
        }
        Usuario usuarioTemp = usuarioRepository.findByUsername(form.getUsername());
        if ( (usuarioTemp != null) && (!usuario.equals(usuarioTemp)) ) {
            throw new UsernameYaExisteException(form.getUsername());
        }
        usuario.setNombre(form.getNombre());
        usuario.setApellidos(form.getApellidos());
        usuario.setCanalesContacto(new CanalesContacto(null, null, form.getEmail()));
        usuario.setUsername(form.getUsername()); 
        Set<ConstraintViolation<Usuario>> violations = validatorFactory.getValidator().validate(usuario);
        if (violations.size()>0) {
            throw new PersistenceException(new ConstraintViolationException(violations));
        }
        usuarioRepository.store(usuario);
    }

    @Override
    public void cambiarPassword(String passwordViejo, String passwordNuevo) {
        Usuario usuario = getUsuario();
        if (!usuario.compararPassword(passwordViejo)) {
            throw new PasswordIncorrectoException();
        }
        usuario.setPassword(passwordNuevo);       
        usuarioRepository.store(usuario);
    }

    @Override
    public Usuario getUsuario() {
        Principal callerPrincipal = ctx.getCallerPrincipal();
        Usuario usuario = usuarioRepository.findByUsername(callerPrincipal.getName());
        if (usuario == null) {
            throw new UsuarioNotExistException(callerPrincipal.getName());
        }
        return usuario;
    }
    
}
