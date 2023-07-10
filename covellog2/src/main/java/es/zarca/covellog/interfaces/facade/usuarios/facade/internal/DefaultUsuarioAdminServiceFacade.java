package es.zarca.covellog.interfaces.facade.usuarios.facade.internal;

import es.zarca.covellog.application.usuarios.UsuarioAdminService;
import es.zarca.covellog.application.usuarios.form.AltaUsuarioForm;
import es.zarca.covellog.application.usuarios.form.EditarUsuarioForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.usuarios.Grupo;
import es.zarca.covellog.domain.model.usuarios.GrupoRepository;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import es.zarca.covellog.domain.model.usuarios.UsuarioRepository;
import es.zarca.covellog.interfaces.facade.usuarios.facade.UsuarioAdminServiceFacade;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultUsuarioAdminServiceFacade implements UsuarioAdminServiceFacade {
    @Inject
    private UsuarioRepository usuarioRepository;
    @Inject
    private GrupoRepository grupoRepository;
    @Inject
    private UsuarioAdminService usuarioAdminService;
    
    @Override
    public void altaUsuario(AltaUsuarioForm form) {
        if (null != usuarioAdminService) {
            usuarioAdminService.altaUsuario(form);
        }
    }

    @Override
    public void modificarUsuario(EditarUsuarioForm form) {
       usuarioAdminService.modificarUsuario(form);
    }

    @Override
    public void bajaUsuario(Integer idUsuario) {
        usuarioAdminService.bajaUsuario(idUsuario);
    }

    @Override
    public void reactivarUsuario(Integer idUsuario) {
        usuarioAdminService.reactivarUsuario(idUsuario);
    }

    @Override
    public void cambiarPassword(Integer idUsuario, String password) {
        usuarioAdminService.cambiarPassword(idUsuario, password);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();        
    }

    @Override
    public List<Usuario> listarUsuarios(int start, int size) {
        return usuarioRepository.findAll(start, size);    
    }

    @Override
    public int listarUsuariosTotalCount() {
        return usuarioRepository.findAllTotalCount();
    }

    @Override
    public List<Usuario> listarUsuarios(Ordre ordre, Map<String, Object> filters) {
        return usuarioRepository.findAll(ordre, filters);
    }

    @Override
    public List<Usuario> listarUsuarios(int start, int size, Ordre ordre, Map<String, Object> filters) {
        return usuarioRepository.findAll(start, size, ordre, filters);
    }

    @Override
    public int listarUsuariosTotalCount(Map<String, Object> filters) {
        return usuarioRepository.findAllTotalCount(filters);
    }

    @Override
    public List<Grupo> listarGruposPosibles() {
        return usuarioAdminService.getGruposPosibles();
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.find(id);
    }
    
    @Override
    public Grupo buscarGrupoPorId(Integer id) {
        return grupoRepository.find(id);
    }
    
}
