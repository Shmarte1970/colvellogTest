/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.clients.client.CodiClient;
import es.zarca.covellog.application.clients.exception.CodiIncorrecteException;
import es.zarca.covellog.application.clients.exception.CodiNotNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CodiClient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "codiclient", unique = true, updatable = false)
    private String codi;

    public CodiClient() {
    }

    public CodiClient(String codi) {
       if (codi == null) {
           throw new CodiNotNullException();
       }
       if ((codi.length() != 2) || (!codi.matches("[A-Z]+"))) {
           throw new CodiIncorrecteException(codi);
       }
       this.codi = codi;
    }

    public String getCodi() {
        return codi;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CodiClient other = (CodiClient) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return codi.hashCode();
    }

    boolean sameValueAs(CodiClient other) {
        return other != null && this.codi.equals(other.codi);
    }

    @Override
    public String toString() {
        return codi;
    }
    
}
