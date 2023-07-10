/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.adreça;

import es.zarca.covellog.domain.model.adreces.exception.Adreça1NotNullException;
import es.zarca.covellog.domain.model.adreces.exception.CodiPostalNotNullException;
import es.zarca.covellog.domain.model.adreces.exception.PoblacioNotNullException;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class Adreça implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "adreca")
    protected String adreça;
    @Embedded
    protected CodigoPostal codigoPostal;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "poblacion_id")
    protected Poblacio poblacio;
    
    public Adreça() {
    }

    public Adreça(String adreça, CodigoPostal codigoPostal, Poblacio poblacio) {
        if (adreça == null) {
            throw new Adreça1NotNullException();
        }
        if (codigoPostal == null) {
            throw new CodiPostalNotNullException();
        }
        if (poblacio == null) {
            throw new PoblacioNotNullException();
        }
        this.adreça = adreça;
        this.codigoPostal = codigoPostal;
        this.poblacio = poblacio;
    }

    public String getAdreça() {
        return adreça;
    }

    public CodigoPostal getString() {
        return codigoPostal;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public Poblacio getPoblacio() {
        return poblacio;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adreça other = (Adreça) o;
        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adreça != null ? adreça.hashCode() : 0);
        hash += (codigoPostal != null ? codigoPostal.hashCode() : 0);
        hash += (poblacio != null ? poblacio.hashCode() : 0);
        return hash;
    }

    boolean sameValueAs(Adreça other) {
        return other != null && 
                this.adreça.equals(other.adreça) &&
                this.codigoPostal.equals(other.codigoPostal) &&
                this.poblacio.equals(other.poblacio);
    }

    @Override
    public String toString() {
        return this.adreça + "\n" + this.codigoPostal + " " + this.poblacio.getNom();
    }
    
}
