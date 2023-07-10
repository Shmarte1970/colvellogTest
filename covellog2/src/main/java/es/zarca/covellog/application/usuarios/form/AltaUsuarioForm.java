package es.zarca.covellog.application.usuarios.form;

import es.zarca.covellog.domain.model.usuarios.Grupo;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class AltaUsuarioForm {
    @Size(min = 3, max = 45)
    private String username;
    @Size(min = 3, max = 45)
    private String password;
    @Size(min = 3, max = 45)
    private String nombre;
    @Size(min = 2, max = 45)
    private String Apellidos;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Email incorrecto")
    @Size(min = 4, max = 128)
    private String email;
    private List<Integer> grupsId = new ArrayList();
    private List<Grupo> grupos = new ArrayList();

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getGrupsId() {
        return grupsId;
    }

    public void setGrupsId(List<Integer> grupsId) {
        this.grupsId = grupsId;
    }

    public void addGrupId(Integer id) {
        grupsId.add(id);
    }
    
    public void removeGrupId(Integer id) {
        grupsId.remove(id);
    }

    
    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
    
    public void addGrupo(Grupo grupo) {
        grupos.add(grupo);
    }
    
    public void removeGrupo(Grupo grupo) {
        grupos.remove(grupo);
    }
    
}
