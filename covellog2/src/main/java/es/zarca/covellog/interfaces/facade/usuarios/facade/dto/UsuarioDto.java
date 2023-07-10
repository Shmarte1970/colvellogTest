package es.zarca.covellog.interfaces.facade.usuarios.facade.dto;

import es.zarca.covellog.domain.model.usuarios.Grupo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class UsuarioDto {
    Integer id;
    private String nombreCompleto;
    @Size(min = 3, max = 45)    
    private String username;
    @Size(min = 3, max = 45)
    private String nombre;
    @Size(min = 2, max = 45)
    private String Apellidos;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Email incorrecto")
    @Size(min = 4, max = 128)
    private String email;
    @Size(max = 50)
    private String telefono;
    @Size(max = 50)
    private String telefono2;
    private List<Grupo> grupos = new ArrayList();

    public UsuarioDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioDto other = (UsuarioDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioDto{" + "id=" + id + ", username=" + username + '}';
    }

}
