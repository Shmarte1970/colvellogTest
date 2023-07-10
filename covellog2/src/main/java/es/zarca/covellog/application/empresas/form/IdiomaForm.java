package es.zarca.covellog.application.empresas.form;

import java.util.Objects;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class IdiomaForm {
    @Size(min = 5, max = 5)
    private String id;
    @Size(min = 1, max = 45)
    private String nombre;
    
    public IdiomaForm() {
    }

    public IdiomaForm(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nombre);
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
        final IdiomaForm other = (IdiomaForm) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IdiomaForm{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
}
