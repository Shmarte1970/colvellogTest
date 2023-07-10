package es.zarca.covellog.application.usuarios.internal;

import es.zarca.covellog.application.usuarios.UsuarioAdminService;
import es.zarca.covellog.application.usuarios.exception.UsernameYaExisteException;
import es.zarca.covellog.application.usuarios.exception.UsuarioAltaFormNullException;
import es.zarca.covellog.application.usuarios.exception.UsuarioNotExistException;
import es.zarca.covellog.application.usuarios.form.AltaUsuarioForm;
import es.zarca.covellog.application.usuarios.form.EditarUsuarioForm;
import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.usuarios.Grupo;
import es.zarca.covellog.domain.model.usuarios.GrupoRepository;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import es.zarca.covellog.domain.model.usuarios.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@DeclareRoles({"ADMIN"})
@Stateless
public class DefaultUsuarioAdminService implements UsuarioAdminService {

    @Inject
    private UsuarioRepository usuarioRepository;
    @Inject
    private GrupoRepository grupoRepository;
    
    @Override
    public void altaUsuario(AltaUsuarioForm form) {
        if (null == form) {
            throw new UsuarioAltaFormNullException();
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(form.getNombre());
        usuario.setApellidos(form.getApellidos());
        usuario.setCanalesContacto(new CanalesContacto(null, null, form.getEmail()));
        usuario.setUsername(form.getUsername());
        usuario.setPassword(form.getPassword());
        Usuario usuarioTemp = usuarioRepository.findByUsername(form.getUsername());
        if (usuarioTemp != null) {
            throw new UsernameYaExisteException(form.getUsername());
        }
        for (Grupo grupo : form.getGrupos()) {
            if (!usuario.perteneceAGrupo(grupo)) {
                usuario.addGrupo(grupo);
            }
        }
        usuarioRepository.store(usuario);
    }

    @Override
    public void modificarUsuario(EditarUsuarioForm form) {
        Usuario usuario = usuarioRepository.find(form.getId());
        if (usuario == null) {
            throw new UsuarioNotExistException(form.getId());
        }
        Usuario usuarioTemp = usuarioRepository.findByUsername(form.getUsername());
        if ( (usuarioTemp != null) && (!usuario.equals(usuarioTemp)) ) {
            throw new UsernameYaExisteException(form.getUsername());
        }
        usuario.setUsername(form.getUsername());
        usuario.setNombre(form.getNombre());
        usuario.setApellidos(form.getApellidos());
        usuario.setCanalesContacto(new CanalesContacto(null, null, form.getEmail()));
        List<Grupo> gruposParaEliminar = new ArrayList<>();
        for (Grupo grupo : usuario.getGrupos()) {
            if (!form.getGrupos().contains(grupo)) {
                gruposParaEliminar.add(grupo);                
            }
        }
        for (Grupo grupo : gruposParaEliminar) {
            usuario.removeGrupo(grupo);
        }
        for (Grupo grupo : form.getGrupos()) {
            if (!usuario.perteneceAGrupo(grupo)) {
                usuario.addGrupo(grupo);
            }
        }
        usuarioRepository.store(usuario);
    }

    @Override
    public void bajaUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.find(idUsuario);
        usuario.baja();
        usuarioRepository.store(usuario);
    }

    @Override
    public void reactivarUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.find(idUsuario);
        usuario.reactivar();
        usuarioRepository.store(usuario);
    }

    @Override
    public void cambiarPassword(Integer idUsuario, String password) {
        Usuario usuario = usuarioRepository.find(idUsuario);
        usuario.setPassword(password);
        usuarioRepository.store(usuario);
    }

    @Override
    public List<Grupo> getGruposPosibles() {
        return grupoRepository.findAll();
    }
    
}
