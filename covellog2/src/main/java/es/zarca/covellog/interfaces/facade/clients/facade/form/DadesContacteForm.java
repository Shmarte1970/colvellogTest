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
public class DadesContacteForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String telefon;
    private String movil;
    private String fax;
    private String email;

    public DadesContacteForm() {
        this.telefon = "";
        this.movil = "";
        this.fax = "";
        this.email = "";
    }

    public DadesContacteForm(String telefon, String movil, String fax, String email) {
        this.telefon = telefon;
        this.movil = movil;
        this.fax = fax;
        this.email = email;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.telefon);
        hash = 19 * hash + Objects.hashCode(this.movil);
        hash = 19 * hash + Objects.hashCode(this.fax);
        hash = 19 * hash + Objects.hashCode(this.email);
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
        final DadesContacteForm other = (DadesContacteForm) obj;
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
        return true;
    }
    
    

    @Override
    public String toString() {
        return "DadesContacteForm{" + "telefon=" + telefon + ", movil=" + movil + ", fax=" + fax + ", email=" + email + '}';
    }
    
    
    
}
