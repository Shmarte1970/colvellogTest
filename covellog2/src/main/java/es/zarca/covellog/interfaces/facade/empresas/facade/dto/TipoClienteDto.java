package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import java.util.Objects;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class TipoClienteDto {
    private String id;
    @Size(max = 50)
    private String nombre;

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
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final TipoClienteDto other = (TipoClienteDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoClienteDto{" + "id=" + id + ", nombre=" + nombre + '}';
    }

}
