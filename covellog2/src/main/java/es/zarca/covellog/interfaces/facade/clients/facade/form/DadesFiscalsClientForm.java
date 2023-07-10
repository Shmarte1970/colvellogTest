/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.form;

import es.zarca.covellog.interfaces.facade.adreces.facade.form.AdreçaForm;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class DadesFiscalsClientForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cif;
    private Boolean exentIva;
    private String iban;
    private AdreçaForm adreça;

    public DadesFiscalsClientForm() {
        this.cif = "";
        this.exentIva = false;
        this.iban = "";
        this.adreça = new AdreçaForm();
    }
    
    public DadesFiscalsClientForm(String cif, Boolean exentIva, String iban, AdreçaForm adreça) {
        this.cif = cif;
        this.exentIva = exentIva;
        this.iban = iban;
        this.adreça = adreça;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public Boolean getExentIva() {
        return exentIva;
    }

    public void setExentIva(Boolean exentIva) {
        this.exentIva = exentIva;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public AdreçaForm getAdreça() {
        return adreça;
    }

    public void setAdreça(AdreçaForm adreça) {
        this.adreça = adreça;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.cif);
        hash = 97 * hash + Objects.hashCode(this.exentIva);
        hash = 97 * hash + Objects.hashCode(this.iban);
        hash = 97 * hash + Objects.hashCode(this.adreça);
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
        final DadesFiscalsClientForm other = (DadesFiscalsClientForm) obj;
        if (!Objects.equals(this.cif, other.cif)) {
            return false;
        }
        if (!Objects.equals(this.iban, other.iban)) {
            return false;
        }
        if (!Objects.equals(this.exentIva, other.exentIva)) {
            return false;
        }
        if (!Objects.equals(this.adreça, other.adreça)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DadesFiscalsClientForm{" + "cif=" + cif + ", exentIva=" + exentIva + ", iban=" + iban + ", adre\u00e7a=" + adreça + '}';
    }
      
}
