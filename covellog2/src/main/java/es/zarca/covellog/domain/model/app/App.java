/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.app;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "App.findAll", query = "SELECT a FROM App a")
    , @NamedQuery(name = "App.findById", query = "SELECT a FROM App a WHERE a.id = :id")
    , @NamedQuery(name = "App.findByNom", query = "SELECT a FROM App a WHERE a.nom = :nom")})
public class App implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nom")
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "app")
    private Collection<PropietatsApp> propietats;

    public App() {
    }

    public App(String id) {
        this.id = id;
    }

    public App(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
    private PropietatsApp filtrarPropietat(String id) {
        for(PropietatsApp propietat:propietats) {
            if (propietat.getPropietatsAppPK().getId() == null ? id == null : propietat.getPropietatsAppPK().getId().equals(id)) {
                return propietat;
            }
        }
        return null;
    }
    
    @XmlTransient
    public String getPropietat(String id) {
        PropietatsApp propietat = filtrarPropietat(id);
        if (propietat != null) return propietat.getValor();
        return null;
    }
    
    @XmlTransient
    public Collection<PropietatsApp> getPropietats() {
        return propietats;
    }

    public void setPropietats(Collection<PropietatsApp> propietats) {
        this.propietats = propietats;
    }
    
    public void setPropietat(String id, String valor) {
        if ((id != null) || (valor != null)) {
            PropietatsApp propietat = filtrarPropietat(id);
            if (propietat == null) {
                propietats.add(new PropietatsApp(this.id, id, valor));
            } else {
                propietat.setValor(valor);
            }
        }
        
    }
    
    
    
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof App)) {
            return false;
        }
        App other = (App) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.app.App[ id=" + id + " ]";
    }
    
}
