/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.email;

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
@Table(name = "pool_servidors_email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoolServidorsEmail.findAll", query = "SELECT p FROM PoolServidorsEmail p")
    , @NamedQuery(name = "PoolServidorsEmail.findById", query = "SELECT p FROM PoolServidorsEmail p WHERE p.id = :id")
    , @NamedQuery(name = "PoolServidorsEmail.findByNom", query = "SELECT p FROM PoolServidorsEmail p WHERE p.nom = :nom")
    , @NamedQuery(name = "PoolServidorsEmail.findByDescripcio", query = "SELECT p FROM PoolServidorsEmail p WHERE p.descripcio = :descripcio")})
public class PoolServidorsEmail implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "descripcio")
    private String descripcio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servidorEmail")
    private Collection<ServidorEmail> servidorEmailCollection;

    public PoolServidorsEmail() {
    }

    public PoolServidorsEmail(String id) {
        this.id = id;
    }

    public PoolServidorsEmail(String id, String nom, String descripcio) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
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

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @XmlTransient
    public Collection<ServidorEmail> getServidorEmailCollection() {
        return servidorEmailCollection;
    }

    public void setServidorEmailCollection(Collection<ServidorEmail> servidorEmailCollection) {
        this.servidorEmailCollection = servidorEmailCollection;
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
        if (!(object instanceof PoolServidorsEmail)) {
            return false;
        }
        PoolServidorsEmail other = (PoolServidorsEmail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "email.PoolServidorsEmail[ id=" + id + " ]";
    }
    
}
