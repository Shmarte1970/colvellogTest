package es.zarca.covellog.domain.model.adreces.adreça;

import es.zarca.covellog.domain.model.adreces.exception.CodiPostalIncorrecteException;
import es.zarca.covellog.domain.model.adreces.exception.CodiPostalNotNullException;
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
public class CodigoPostal implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_postal")
    private String codigo;

    public CodigoPostal() {
    }

    public CodigoPostal(String codi) {
       if (codi == null) {
           throw new CodiPostalNotNullException();
       }
       /*if ((codi.length() != 5) || (!codi.matches("\\d+"))) {
           throw new CodiPostalIncorrecteException(codi);
       }*/
       this.codigo = codi;
    }

    public String getString() {
        return codigo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CodigoPostal other = (CodigoPostal) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    boolean sameValueAs(CodigoPostal other) {
        return other != null && this.codigo.equals(other.codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
    
}
