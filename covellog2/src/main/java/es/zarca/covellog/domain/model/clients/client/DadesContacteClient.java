/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.clients.client.DadesContacte;
import es.zarca.covellog.domain.model.clients.client.DadesContacteClient;
import es.zarca.covellog.domain.model.adreces.adreça.Adreça;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DadesContacteClient implements Serializable {

    private static final long serialVersionUID = 1L;
    //Horari
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "horari" )
    protected String horari;
    //Idioma
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "idioma" )
    protected String idioma;
    //Dades Contacte
    @Embedded
    protected DadesContacte dadesContacte;
    //Web
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "web" )
    protected String web;
    //Adreça
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "adreca" )
    protected Adreça adreça;

    public DadesContacteClient() {
    }

    public DadesContacteClient(String horari, String idioma, DadesContacte dadesContacte, String web, Adreça adreça) {
        this.horari = horari;
        this.idioma = idioma;
        this.dadesContacte = dadesContacte;
        this.web = web;
        this.adreça = adreça;
    }

    public String getHorari() {
        return horari;
    }

    public String getIdioma() {
        return idioma;
    }

    public DadesContacte getDadesContacte() {
        return dadesContacte;
    }

    public String getWeb() {
        return web;
    }

    public Adreça getAdreça() {
        return adreça;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.horari);
        hash = 97 * hash + Objects.hashCode(this.idioma);
        hash = 97 * hash + Objects.hashCode(this.dadesContacte);
        hash = 97 * hash + Objects.hashCode(this.web);
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
        final DadesContacteClient other = (DadesContacteClient) obj;
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

    @Override
    public String toString() {
        return "DadesContacteClient{" + "horari=" + horari + ", idioma=" + idioma + ", dadesContacte=" + dadesContacte + ", web=" + web + ", adre\u00e7a=" + adreça + '}';
    }
    
}
