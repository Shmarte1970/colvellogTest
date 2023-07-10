/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.adreça;

import es.zarca.covellog.domain.model.adreces.exception.Adreça1NotNullException;
import es.zarca.covellog.domain.model.adreces.exception.Adreça2NotNullException;
import es.zarca.covellog.domain.model.adreces.exception.CodiPostalNotNullException;
import es.zarca.covellog.domain.model.adreces.exception.PoblacioNotNullException;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
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
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion")
    protected String direccion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion2")
    protected String direccion2;
    @Embedded
    protected CodigoPostal codigoPostal;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "poblacion_id")
    protected Poblacio poblacion;
    
    public Direccion() {
    }

    public Direccion(String direccion, String direccion2, CodigoPostal codigoPostal, Poblacio poblacion) {
        if (direccion == null) {
            throw new Adreça1NotNullException();
        }
        if (direccion2 == null) {
            throw new Adreça2NotNullException();
        }
        if (codigoPostal == null) {
            throw new CodiPostalNotNullException();
        }
        if (poblacion == null) {
            throw new PoblacioNotNullException();
        }
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.codigoPostal = codigoPostal;
        this.poblacion = poblacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public Poblacio getPoblacion() {
        return poblacion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.direccion);
        hash = 59 * hash + Objects.hashCode(this.direccion2);
        hash = 59 * hash + Objects.hashCode(this.codigoPostal);
        hash = 59 * hash + Objects.hashCode(this.poblacion);
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
        final Direccion other = (Direccion) obj;
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.direccion2, other.direccion2)) {
            return false;
        }
        if (!Objects.equals(this.codigoPostal, other.codigoPostal)) {
            return false;
        }
        if (!Objects.equals(this.poblacion, other.poblacion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Direccion{" + 
            "direccion=" + (direccion == null ? "null" : direccion) + 
            ", direccion2=" + (direccion2 == null ? "null" : direccion2) + 
            ", codigoPostal=" + (codigoPostal == null ? "null" : codigoPostal) + 
            ", poblacion=" + (poblacion == null ? "null" : poblacion) + '}';
    }
    
    
    
}