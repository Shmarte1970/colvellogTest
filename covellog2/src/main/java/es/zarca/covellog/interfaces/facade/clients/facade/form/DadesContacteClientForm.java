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
public class DadesContacteClientForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String horari;
    private String idioma;
    private DadesContacteForm dadesContacte;
    private String web;
    private AdreçaForm adreça;

    public DadesContacteClientForm() {
        this.horari = "";
        this.idioma = "";
        this.dadesContacte = new DadesContacteForm();
        this.web = "";
        this.adreça = new AdreçaForm();
    }
    
    public DadesContacteClientForm(String horari, String idioma, DadesContacteForm dadesContacte, String web, AdreçaForm adreça) {
        this.horari = horari;
        this.idioma = idioma;
        this.dadesContacte = dadesContacte;
        this.web = web;
        this.adreça = adreça;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public DadesContacteForm getDadesContacte() {
        return dadesContacte;
    }

    public void setDadesContacte(DadesContacteForm dadesContacte) {
        this.dadesContacte = dadesContacte;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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
        hash = 67 * hash + Objects.hashCode(this.horari);
        hash = 67 * hash + Objects.hashCode(this.idioma);
        hash = 67 * hash + Objects.hashCode(this.dadesContacte);
        hash = 67 * hash + Objects.hashCode(this.web);
        hash = 67 * hash + Objects.hashCode(this.adreça);
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
        final DadesContacteClientForm other = (DadesContacteClientForm) obj;
        if (!Objects.equals(this.horari, other.horari)) {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        if (!Objects.equals(this.web, other.web)) {
            return false;
        }
        if (!Objects.equals(this.dadesContacte, other.dadesContacte)) {
            return false;
        }
        if (!Objects.equals(this.adreça, other.adreça)) {
            return false;
        }
        return true;
    }
    
    
    
}
