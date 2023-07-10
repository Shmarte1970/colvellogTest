/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.app;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "propietats_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropietatsApp.findAll", query = "SELECT p FROM PropietatsApp p")
    , @NamedQuery(name = "PropietatsApp.findByIdApp", query = "SELECT p FROM PropietatsApp p WHERE p.propietatsAppPK.idApp = :idApp")
    , @NamedQuery(name = "PropietatsApp.findById", query = "SELECT p FROM PropietatsApp p WHERE p.propietatsAppPK.id = :id")
    , @NamedQuery(name = "PropietatsApp.findByValor", query = "SELECT p FROM PropietatsApp p WHERE p.valor = :valor")})
public class PropietatsApp implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PropietatsAppPK propietatsAppPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "idApp", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private App app;

    public PropietatsApp() {
    }

    public PropietatsApp(PropietatsAppPK propietatsAppPK) {
        this.propietatsAppPK = propietatsAppPK;
    }

    public PropietatsApp(PropietatsAppPK propietatsAppPK, String valor) {
        this.propietatsAppPK = propietatsAppPK;
        this.valor = valor;
    }

    public PropietatsApp(String idApp, String id, String valor) {
        this.propietatsAppPK = new PropietatsAppPK(idApp, id);
        this.valor = valor;
    }

    public PropietatsAppPK getPropietatsAppPK() {
        return propietatsAppPK;
    }

    public void setPropietatsAppPK(PropietatsAppPK propietatsAppPK) {
        this.propietatsAppPK = propietatsAppPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (propietatsAppPK != null ? propietatsAppPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropietatsApp)) {
            return false;
        }
        PropietatsApp other = (PropietatsApp) object;
        if ((this.propietatsAppPK == null && other.propietatsAppPK != null) || (this.propietatsAppPK != null && !this.propietatsAppPK.equals(other.propietatsAppPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.app.PropietatsApp[ propietatsAppPK=" + propietatsAppPK + " ]";
    }
    
}
