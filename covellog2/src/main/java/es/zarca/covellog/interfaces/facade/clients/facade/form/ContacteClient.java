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
public class ContacteClient implements Serializable {

    private static final long serialVersionUID = 1L;
    //CLIENT
    private String nom;
    private String alias;
    
    //DADES FISCALS
    private DadesFiscalsClientForm dadesFiscals;
    
    //CONTACTE EMPRESA
    private DadesContacteClientForm contacteEmpresa;
    
    //CONTACTE PRINCIPAL
    private PersonaContacteForm personaContactePrincipal;

    public ContacteClient() {
        this.nom = "";
        this.alias = "";
        this.dadesFiscals = new DadesFiscalsClientForm();
        this.contacteEmpresa = new DadesContacteClientForm();
        this.personaContactePrincipal = new PersonaContacteForm();
    }
    
    public ContacteClient(String nom, String alias, DadesFiscalsClientForm dadesFiscals, DadesContacteClientForm contacteEmpresa, PersonaContacteForm personaContactePrincipal) {
        this.nom = nom;
        this.alias = alias;
        this.dadesFiscals = dadesFiscals;
        this.contacteEmpresa = contacteEmpresa;
        this.personaContactePrincipal = personaContactePrincipal;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public DadesFiscalsClientForm getDadesFiscals() {
        return dadesFiscals;
    }

    public void setDadesFiscals(DadesFiscalsClientForm dadesFiscals) {
        this.dadesFiscals = dadesFiscals;
    }

    public DadesContacteClientForm getContacteEmpresa() {
        return contacteEmpresa;
    }

    public void setContacteEmpresa(DadesContacteClientForm contacteEmpresa) {
        this.contacteEmpresa = contacteEmpresa;
    }

    public PersonaContacteForm getPersonaContactePrincipal() {
        return personaContactePrincipal;
    }

    public void setPersonaContactePrincipal(PersonaContacteForm personaContactePrincipal) {
        this.personaContactePrincipal = personaContactePrincipal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nom);
        hash = 71 * hash + Objects.hashCode(this.alias);
        hash = 71 * hash + Objects.hashCode(this.dadesFiscals);
        hash = 71 * hash + Objects.hashCode(this.contacteEmpresa);
        hash = 71 * hash + Objects.hashCode(this.personaContactePrincipal);
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
        final ContacteClient other = (ContacteClient) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.alias, other.alias)) {
            return false;
        }
        if (!Objects.equals(this.dadesFiscals, other.dadesFiscals)) {
            return false;
        }
        if (!Objects.equals(this.contacteEmpresa, other.contacteEmpresa)) {
            return false;
        }
        if (!Objects.equals(this.personaContactePrincipal, other.personaContactePrincipal)) {
            return false;
        }
        return true;
    }
    
}