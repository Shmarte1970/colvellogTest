/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.crm.oportunitat;

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
@Table(name = "origen_oportunitat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrigenOportunitat.findAll", query = "SELECT o FROM OrigenOportunitat o")
    , @NamedQuery(name = "OrigenOportunitat.findById", query = "SELECT o FROM OrigenOportunitat o WHERE o.id = :id")
    , @NamedQuery(name = "OrigenOportunitat.findByNom", query = "SELECT o FROM OrigenOportunitat o WHERE o.nom = :nom")})
public class OrigenOportunitat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nom")
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "origen")
    private Collection<Oportunitat> oportunitatCollection;

    public OrigenOportunitat() {
    }

    public OrigenOportunitat(String id) {
        this.id = id;
    }

    public OrigenOportunitat(String id, String nom) {
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

    @XmlTransient
    public Collection<Oportunitat> getOportunitatCollection() {
        return oportunitatCollection;
    }

    public void setOportunitatCollection(Collection<Oportunitat> oportunitatCollection) {
        this.oportunitatCollection = oportunitatCollection;
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
        if (!(object instanceof OrigenOportunitat)) {
            return false;
        }
        OrigenOportunitat other = (OrigenOportunitat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat[ id=" + id + " ]";
    }
    
}
