/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.form;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class AdreçaForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private String adreça;
    private String codiPostal;
    private Integer poblacioId;
    private String poblacioNom;
    
    public AdreçaForm() {
        this.adreça = "";
        this.codiPostal = "";
        this.poblacioId = 0;
        this.poblacioNom = "";
    }

    public AdreçaForm(String adreça, String codiPostal, Integer poblacioId, String poblacioNom) {
        this.adreça = adreça;
        this.codiPostal = codiPostal;
        this.poblacioId = poblacioId;
        this.poblacioNom = poblacioNom;
    }

    public String getAdreça() {
        return adreça;
    }

    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    public void setPoblacioId(Integer poblacioId) {
        this.poblacioId = poblacioId;
    }

    public String getPoblacioNom() {
        return poblacioNom;
    }

    public void setPoblacioNom(String poblacioNom) {
        this.poblacioNom = poblacioNom;
    }
     
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AdreçaForm)) {
            return false;
        }
        AdreçaForm other = (AdreçaForm) object;
        return hashCode() == object.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.adreça);
        hash = 11 * hash + Objects.hashCode(this.codiPostal);
        hash = 11 * hash + Objects.hashCode(this.poblacioId);
        return hash;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.interfaces.adreces.facade.form.AdreçaForm[ " + this.adreça + " " + this.codiPostal + " " + this.poblacioId.toString() + " ]";
    }
    
}
