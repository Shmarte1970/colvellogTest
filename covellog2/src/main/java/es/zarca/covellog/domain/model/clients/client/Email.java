/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import com.aeat.valida.Validador;
import es.zarca.covellog.application.clients.exception.CifIncorrecteException;
import es.zarca.covellog.application.clients.exception.CifNotNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    //Email
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "email" )
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",  message="{invalid.email}")
    protected String email;

    public Email() {
    }
    
    public Email(String email) {
        this.email = email;
    }

    public String getEmailString() {
        return email;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Email other = (Email) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    boolean sameValueAs(Email other) {
        return other != null && this.email.equals(other.email);
    }

    @Override
    public String toString() {
        return email;
    }
    
}
