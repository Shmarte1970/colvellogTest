package es.zarca.covellog.interfaces.facade.contactos.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ContactoRolDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.IdiomaDto;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class ContactoDto {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull
    @Size(min = 1, max = 50)
    private String nombre;
    private String apellidos;
    @Size(max = 200)
    private String descripcion;
    @Size(max = 50)
    private String telefono;
    @Size(max = 50)
    private String telefono2;
    //@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    private String email;
    private IdiomaDto idioma;
    @Size(max = 200)
    private String horario;
    @Size(max = 10000)
    private String observaciones;
    private List<ContactoRolDto> roles;    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IdiomaDto getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaDto idioma) {
        this.idioma = idioma;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<ContactoRolDto> getRoles() {
        return roles;
    }

    public void setRoles(List<ContactoRolDto> roles) {
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        return "ContactoDto{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final ContactoDto other = (ContactoDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
