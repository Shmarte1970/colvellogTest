/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.comercials.divisiocomercial;

import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
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
@Table(name = "divisio_comercial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DivisioComercial.findAll", query = "SELECT t FROM DivisioComercial t")
    , @NamedQuery(name = "DivisioComercial.findById", query = "SELECT t FROM DivisioComercial t WHERE t.id = :id")
    , @NamedQuery(name = "DivisioComercial.findByNom", query = "SELECT t FROM DivisioComercial t WHERE t.nom = :nom")})
public class DivisioComercial implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "divisioComercial")
    private Collection<Oportunitat> oportunitatCollection;

    public DivisioComercial() {
    }

    public DivisioComercial(String id) {
        this.id = id;
    }

    public DivisioComercial(String id, String nom) {
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
        if (!(object instanceof DivisioComercial)) {
            return false;
        }
        DivisioComercial other = (DivisioComercial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.crm.oportunitat.DivisioComercial[ id=" + id + " ]";
    }
    
}
