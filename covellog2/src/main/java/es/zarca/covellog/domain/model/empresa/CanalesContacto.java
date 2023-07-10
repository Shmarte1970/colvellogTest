package es.zarca.covellog.domain.model.empresa;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CanalesContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Size(max = 50)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 50)
    @Column(name = "telefono2")
    private String telefono2;
    //@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="El email es incorrecto.")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 128)
    @Column(name = "email")
    private String email;

    public CanalesContacto() {
    }

    public CanalesContacto(String telefono, String telefono2, String email) {
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.email = email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public String getEmail() {
        return email;
    }

}