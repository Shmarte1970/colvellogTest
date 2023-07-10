package es.zarca.covellog.application.usuarios;

import es.zarca.covellog.application.usuarios.form.PerfilUsuarioForm;
import es.zarca.covellog.domain.model.usuarios.Usuario;

/**
 *
 * @author francisco
 */
public interface UsuarioService {
    void modificarPerfil(PerfilUsuarioForm form);
    void cambiarPassword(String passwordViejo, String passwordNuevo);

    Usuario getUsuario();
}
