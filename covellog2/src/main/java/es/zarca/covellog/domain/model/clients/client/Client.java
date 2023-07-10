/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.adreces.adreça.Adreça;
import es.zarca.covellog.domain.model.clients.potencial.Potencial2;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(referencedColumnName="id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c ORDER BY c.id ASC")
    , @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id")})

public class Client extends Potencial2 {
    private static final long serialVersionUID = 1L;
    //Nom
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "client_alias" )
    protected String alias;
    /////////////////////
    //Dades Fiscals
    /////////////////////
    //Cif
    @Embedded
    protected Cif cif;
    //ExentIva
    /*@Basic(optional = false)
    @NotNull
    @Column(name = "exent_iva")*/
    @Transient
    protected Boolean exentIva;
    //Iban
    /*@Basic(optional = false)
    @NotNull
    @Column(name = "iban")*/
    @Transient
    protected String iban;
    //Adreça Fiscal
    /*@Embedded
    @AttributeOverrides({
        @AttributeOverride(name="adreça",column=@Column(name="adr_fiscal_adreça")),
        @AttributeOverride(name="codigo",column=@Column(name="adr_fiscal_codi_postal"))
    })
    @AssociationOverrides({  
        @AssociationOverride(name = "poblacio", joinColumns = @JoinColumn(name = "adr_fiscal_poblacio_id"))
    })*/
    @Transient
    protected Adreça adreçaFiscal;
    
    ////////////////////
    //Dades Contacte 
    ////////////////////
    //Horari
    /*@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "horari" )*/
    @Transient
    protected String horari;
    //Idioma
    /*@JoinColumn(name = "idioma_id", referencedColumnName = "id")
    @ManyToOne(optional = false)*/
    @Transient
    protected Idioma idioma;
    //Adreça Contacte
    /*@Embedded
    @AttributeOverrides({
        @AttributeOverride(name="adreça",column=@Column(name="adr_contacte_adreça")),
        @AttributeOverride(name="codigo",column=@Column(name="adr_contacte_codi_postal"))
    })
    @AssociationOverrides({  
        @AssociationOverride(name = "poblacio", joinColumns = @JoinColumn(name = "adr_contacte_poblacio_id"))
    })*/
    @Transient
    protected Adreça adreçaContacte;
    //Web
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 80)
    //@Column(name = "web" )
    @Transient
    protected String web;
    //Telefon
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 3, max = 20)
    //@Column(name = "telefon" )
    @Transient
    protected String telefon;
    //Movil
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 3, max = 20)
    //@Column(name = "movil" )
    @Transient
    protected String movil;
    //FAX
    //@Basic(optional = false)
    //@NotNull
    //@Size(min = 3, max = 20)
    //@Column(name = "fax" )
    @Transient
    protected String fax;
    //Email
    //@Embedded
    @Transient
    protected Email email;
    
    ////////////////////
    //Persones Contactes Principal 
    ////////////////////
    //@Embedded
    @Transient
    protected PersonaContacte personaContactePrincipal;
    //Persones Contactes Client
    /*@ElementCollection
    @CollectionTable(
        name="personaContacteClient",
        joinColumns=@JoinColumn(name="client_id")
    )*/
    @Transient
    protected List<PersonaContacte> personesContactes;

    public Client() {
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Cif getCif() {
        return cif;
    }

    public void setCif(Cif cif) {
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

    public Adreça getAdreçaFiscal() {
        return adreçaFiscal;
    }

    public void setAdreçaFiscal(Adreça adreçaFiscal) {
        this.adreçaFiscal = adreçaFiscal;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Adreça getAdreçaContacte() {
        return adreçaContacte;
    }

    public void setAdreçaContacte(Adreça adreçaContacte) {
        this.adreçaContacte = adreçaContacte;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public PersonaContacte getPersonaContactePrincipal() {
        return personaContactePrincipal;
    }

    public void setPersonaContactePrincipal(PersonaContacte personaContactePrincipal) {
        this.personaContactePrincipal = personaContactePrincipal;
    }

    public List<PersonaContacte> getPersonesContactes() {
        return personesContactes;
    }

    public void setPersonesContactes(List<PersonaContacte> personesContactes) {
        this.personesContactes = personesContactes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.alias);
        hash = 83 * hash + Objects.hashCode(this.cif);
        hash = 83 * hash + Objects.hashCode(this.exentIva);
        hash = 83 * hash + Objects.hashCode(this.iban);
        hash = 83 * hash + Objects.hashCode(this.adreçaFiscal);
        hash = 83 * hash + Objects.hashCode(this.horari);
        hash = 83 * hash + Objects.hashCode(this.idioma);
        hash = 83 * hash + Objects.hashCode(this.adreçaContacte);
        hash = 83 * hash + Objects.hashCode(this.web);
        hash = 83 * hash + Objects.hashCode(this.telefon);
        hash = 83 * hash + Objects.hashCode(this.movil);
        hash = 83 * hash + Objects.hashCode(this.fax);
        hash = 83 * hash + Objects.hashCode(this.email);
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
        final Client other = (Client) obj;
        if (!Objects.equals(this.alias, other.alias)) {
            return false;
        }
        if (!Objects.equals(this.iban, other.iban)) {
            return false;
        }
        if (!Objects.equals(this.horari, other.horari)) {
            return false;
        }
        if (!Objects.equals(this.web, other.web)) {
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
        if (!Objects.equals(this.cif, other.cif)) {
            return false;
        }
        if (!Objects.equals(this.exentIva, other.exentIva)) {
            return false;
        }
        if (!Objects.equals(this.adreçaFiscal, other.adreçaFiscal)) {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        if (!Objects.equals(this.adreçaContacte, other.adreçaContacte)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }

   

    
    
    
}
