/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.provincia;

import es.zarca.covellog.domain.model.adreces.pais.*;
import es.zarca.covellog.domain.model.adreces.exception.CodiIso2IncorrecteException;
import es.zarca.covellog.domain.model.adreces.exception.CodiIso2NotNullException;
import es.zarca.covellog.domain.model.adreces.exception.CodiProvinciaIncorrecteException;
import es.zarca.covellog.domain.model.adreces.exception.CodiProvinciaNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CodiProvincia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "codigo", unique = true, updatable = false)
    private String codi;

    public CodiProvincia() {
    }

    public CodiProvincia(String codi) {
       if (codi == null) {
           throw new CodiProvinciaNullException();
       }
       if ((codi.length() != 2) || (!codi.matches("[A-Z]+"))) {
           throw new CodiProvinciaIncorrecteException(codi);
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

        CodiProvincia other = (CodiProvincia) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return codi.hashCode();
    }

    boolean sameValueAs(CodiProvincia other) {
        return other != null && this.codi.equals(other.codi);
    }

    @Override
    public String toString() {
        return codi;
    }
    
}
