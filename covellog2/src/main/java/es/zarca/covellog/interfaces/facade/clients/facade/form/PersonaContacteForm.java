/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.form;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PersonaContacteForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nom;
    private String carrec;
    private String telefon;
    private String movil;
    private String fax;
    private String email;
    private String observacions;

    public PersonaContacteForm() {
        this.nom = "";
        this.carrec = "";
        this.telefon = "";
        this.movil = "";
        this.fax = "";
        this.email = "";
        this.observacions = "";
    }

    public PersonaContacteForm(String nom, String carrec, String telefon, String movil, String fax, String email, String observacions) {
        this.nom = nom;
        this.carrec = carrec;
        this.telefon = telefon;
        this.movil = movil;
        this.fax = fax;
        this.email = email;
        this.observacions = observacions;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCarrec() {
        return carrec;
    }

    public void setCarrec(String carrec) {
        this.carrec = carrec;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nom);
        hash = 71 * hash + Objects.hashCode(this.carrec);
        hash = 71 * hash + Objects.hashCode(this.telefon);
        hash = 71 * hash + Objects.hashCode(this.movil);
        hash = 71 * hash + Objects.hashCode(this.fax);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.observacions);
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
        final PersonaContacteForm other = (PersonaContacteForm) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.carrec, other.carrec)) {
            return false;
        }
        if (!Objects.equals(this.telefon, other.telefon)) {
            return false;
        }
        if (!Objects.equals(this.movil, other.movil)) {
            return false;
        }
        if (!Objects.equals(this.fax, other.fax)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.observacions, other.observacions)) {
            return false;
        }
        return true;
    }
    
    
}