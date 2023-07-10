/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.pais;

import es.zarca.covellog.domain.model.adreces.exception.CodiIso2IncorrecteException;
import es.zarca.covellog.domain.model.adreces.exception.CodiIso2NotNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CodiIsoPais2 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "codiIso2", unique = true, updatable = false)
    private String codi;

    public CodiIsoPais2() {
    }

    public CodiIsoPais2(String codi) {
       if (codi == null) {
           throw new CodiIso2NotNullException();
       }
       if ((codi.length() != 2) || (!codi.matches("[A-Z]+"))) {
           throw new CodiIso2IncorrecteException(codi);
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

        CodiIsoPais2 other = (CodiIsoPais2) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return codi.hashCode();
    }

    boolean sameValueAs(CodiIsoPais2 other) {
        return other != null && this.codi.equals(other.codi);
    }

    @Override
    public String toString() {
        return codi;
    }
    
}
