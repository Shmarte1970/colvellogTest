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
import javax.persistence.Column;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CifFine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "cif", unique = true, updatable = false)
    private String cif;

    public CifFine() {
    }

    public CifFine(String cif) {
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

        CifFine other = (CifFine) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return cif.hashCode();
    }

    boolean sameValueAs(CifFine other) {
        return other != null && this.cif.equals(other.cif);
    }

    @Override
    public String toString() {
        return cif;
    }
    
}
