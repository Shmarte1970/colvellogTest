/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.app;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@Embeddable
public class PropietatsAppPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "idApp")
    private String idApp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "id")
    private String id;

    public PropietatsAppPK() {
    }

    public PropietatsAppPK(String idApp, String id) {
        this.idApp = idApp;
        this.id = id;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idApp != null ? idApp.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropietatsAppPK)) {
            return false;
        }
        PropietatsAppPK other = (PropietatsAppPK) object;
        if ((this.idApp == null && other.idApp != null) || (this.idApp != null && !this.idApp.equals(other.idApp))) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.app.PropietatsAppPK[ idApp=" + idApp + ", id=" + id + " ]";
    }
    
}
