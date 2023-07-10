package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class ContactoRolDto {
    private static final long serialVersionUID = 1L;
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @NotNull
    @Size(min = 1, max = 80)
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

}
