package es.zarca.covellog.application.usuarios;

import es.zarca.covellog.application.usuarios.form.AltaUsuarioForm;
import es.zarca.covellog.application.usuarios.form.EditarUsuarioForm;
import es.zarca.covellog.domain.model.usuarios.Grupo;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface UsuarioAdminService {
    void altaUsuario(AltaUsuarioForm form);
    void modificarUsuario(EditarUsuarioForm form);
    void bajaUsuario(Integer idUsuario);
    void reactivarUsuario(Integer idUsuario);
    void cambiarPassword(Integer idUsuario, String password);
    List<Grupo> getGruposPosibles();
}
