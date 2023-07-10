package es.zarca.covellog.interfaces.facade.usuarios.facade;

import es.zarca.covellog.application.usuarios.form.AltaUsuarioForm;
import es.zarca.covellog.application.usuarios.form.EditarUsuarioForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.usuarios.Grupo;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import java.util.List;
import java.util.Map;

/**
 *
 * @author francisco
 */
public interface UsuarioAdminServiceFacade {
    void altaUsuario(AltaUsuarioForm form);
    void modificarUsuario(EditarUsuarioForm form);
    void bajaUsuario(Integer idUsuario);
    void reactivarUsuario(Integer idUsuario);
    void cambiarPassword(Integer idUsuario, String password);
    List<Usuario> listarUsuarios();
    List<Usuario> listarUsuarios(int start, int size);    
    int listarUsuariosTotalCount();
    List<Usuario> listarUsuarios(Ordre ordre, Map<String, Object> filters);
    List<Usuario> listarUsuarios(int start, int size, Ordre ordre, Map<String, Object> filters);
    int listarUsuariosTotalCount(Map<String, Object> filters);
    List<Grupo> listarGruposPosibles();
    Usuario buscarPorId(Integer id);

    Grupo buscarGrupoPorId(Integer id);
}
