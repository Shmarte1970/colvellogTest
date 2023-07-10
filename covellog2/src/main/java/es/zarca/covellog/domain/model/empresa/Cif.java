package es.zarca.covellog.domain.model.empresa;

import com.aeat.valida.Validador;
import es.zarca.covellog.application.clients.exception.CifIncorrecteException;
import es.zarca.covellog.application.clients.exception.CifNotNullException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class Cif implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cif")
    @Size(max = 30)
    private String cif;

    public Cif() {
    }

    public Cif(String cif) {
        if (cif == null) {
            throw new CifNotNullException();
        }
        cif = cif.toUpperCase();
        Validador val = new Validador(); 
        int ret = val.checkNif(cif); 
        if(ret <= 0) {
            throw new CifIncorrecteException(cif);
        }
        this.cif = cif;
    }

    public String getCifString() {
        return cif;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cif other = (Cif) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return cif.hashCode();
    }

    boolean sameValueAs(Cif other) {
        return other != null && this.cif.equals(other.cif);
    }

    @Override
    public String toString() {
        return cif;
    }
    
}
