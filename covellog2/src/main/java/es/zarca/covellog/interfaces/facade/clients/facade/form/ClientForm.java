/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.form;

import es.zarca.covellog.interfaces.facade.adreces.facade.form.AdreçaForm;
import java.io.Serializable;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ClientForm implements Serializable {

    private static final long serialVersionUID = 1L;
   
    //CLIENT
    private Integer id;
    private String nom;
    private String alias;
    private String cif;
    private AdreçaForm adreçaFiscal;
    private Boolean exentIva;
    private String iban;
    private AdreçaForm adreçaContacte;
    private String horari;
    private String email;
    private PersonaContacteForm personaContactePrincipal;
    private String web;
    private String telefon;
    private String movil;
    private String fax;
    private Integer idIdioma;
    private String idioma;
    
    public ClientForm() {
        this.id = null;
        this.nom = "";
        this.alias = "";
        this.cif = "";
        this.adreçaFiscal = new AdreçaForm();
        this.exentIva = false;
        this.iban = "";
        this.personaContactePrincipal = new PersonaContacteForm();
        this.adreçaContacte = new AdreçaForm();
        this.horari = "";
        this.email = "";
        this.web = "";
        this.telefon = "";
        this.movil = "";
        this.fax = "";
        this.idIdioma = null;
        this.idioma = "";
    }


    public ClientForm(Integer id, String nom, String alias, String cif, AdreçaForm adreçaFiscal, Boolean exentIva, String iban, PersonaContacteForm personaContactePrincipal, AdreçaForm adreçaContacte, String horari, String email, String web, String telefon, String movil, String fax, Integer idIdioma, String idioma) {
        this.id = id;
        this.nom = nom;
        this.alias = alias;
        this.cif = cif;
        this.adreçaFiscal = adreçaFiscal;
        this.exentIva = exentIva;
        this.iban = iban;
        this.personaContactePrincipal =  new PersonaContacteForm();
        this.adreçaContacte = adreçaContacte;
        this.horari = horari;
        this.email = email;
        this.web = web;
        this.telefon = telefon;
        this.movil = movil;
        this.fax = fax;
        this.idIdioma = idIdioma;
        this.idioma = idioma;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public AdreçaForm getAdreçaFiscal() {
        return adreçaFiscal;
    }

    public void setAdreçaFiscal(AdreçaForm adreçaFiscal) {
        this.adreçaFiscal = adreçaFiscal;
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

    public AdreçaForm getAdreçaContacte() {
        return adreçaContacte;
    }

    public void setAdreçaContacte(AdreçaForm adreçaContacte) {
        this.adreçaContacte = adreçaContacte;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public PersonaContacteForm getPersonaContactePrincipal() {
        return personaContactePrincipal;
    }

    public void setPersonaContactePrincipal(PersonaContacteForm personaContactePrincipal) {
        this.personaContactePrincipal = personaContactePrincipal;
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

    public Integer getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    

    
}