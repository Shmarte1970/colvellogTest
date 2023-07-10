/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.clients.client.DadesContacte;
import es.zarca.covellog.domain.model.clients.client.PersonaContacte;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
//@Embeddable
public class PersonaContacte implements Serializable {
    private static final long serialVersionUID = 1L;
   
    //Nom
    /*@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nom" )*/
    protected String nom;
    //Carrec
   /* @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "carrec" )*/
    protected String carrec;
    //Dades Contacte
    //@Embedded
    protected String telefon;
    protected String movil;
    protected String fax;
    protected String email;
    //Carrec
   /* @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "observacions" )*/
    protected String observacions;

    public PersonaContacte() {
        this.nom = "";
        this.carrec = "";
        this.telefon = "";
        this.movil = "";
        this.fax = "";
        this.email = "";
        this.observacions = "";
    }

    public PersonaContacte(String nom, String carrec, String telefon, String movil, String fax, String email, String observacions) {
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

    public String getCarrec() {
        return carrec;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getMovil() {
        return movil;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getObservacions() {
        return observacions;
    }

    @Override
    public String toString() {
        return "PersonaContacte{" + "nom=" + nom + ", carrec=" + carrec + ", telefon=" + telefon + ", movil=" + movil + ", fax=" + fax + ", email=" + email + ", observacions=" + observacions + '}';
    }
    
    

    

    
}
