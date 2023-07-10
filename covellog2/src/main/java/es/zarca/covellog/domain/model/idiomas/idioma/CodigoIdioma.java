package es.zarca.covellog.domain.model.idiomas.idioma;

import es.zarca.covellog.domain.model.adreces.exception.CodiIso2IncorrecteException;
import es.zarca.covellog.domain.model.adreces.exception.CodiIso2NotNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CodigoIdioma implements Serializable {

    private static final long serialVersionUID = 2L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idioma_id")
    private String codigo;

    public CodigoIdioma() {
    }

    public CodigoIdioma(String id) {
       if (id == null) {
           throw new CodiIso2NotNullException();
       }
       if ((id.length() != 5) || (!id.matches("[a-z]{2}[_][A-Z]{2}"))) {
           throw new CodiIso2IncorrecteException(id);
       }
       this.codigo = id;
    }

    public String getString() {
        return codigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigo);
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
        final CodigoIdioma other = (CodigoIdioma) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
    
}
