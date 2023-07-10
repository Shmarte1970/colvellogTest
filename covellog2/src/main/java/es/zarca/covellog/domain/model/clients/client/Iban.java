/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.adreces.exception.IbanNotNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class Iban implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iban")
    private String iban;

    public Iban() {
    }

    public Iban(String iban) {
        if (iban == null) {
            throw new IbanNotNullException();
        }
    }
   
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Iban other = (Iban) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return iban.hashCode();
    }

    boolean sameValueAs(Iban other) {
        return other != null && this.iban.equals(other.iban);
    }

    @Override
    public String toString() {
        return iban;
    }
    
}
