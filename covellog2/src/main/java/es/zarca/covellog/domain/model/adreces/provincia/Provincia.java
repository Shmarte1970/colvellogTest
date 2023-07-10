/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.adreces.provincia;

import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.pais.Pais;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author usuario
 */
@Entity
@Table(name = "provincia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincia.findAll", query = "SELECT p FROM Provincia p ORDER BY p.nom ASC")
    , @NamedQuery(name = "Provincia.findById", query = "SELECT p FROM Provincia p WHERE p.id = :id")
    , @NamedQuery(name = "Provincia.findByNom", query = "SELECT p FROM Provincia p WHERE p.nom = :nom")
    , @NamedQuery(name = "Provincia.findByCodi", query = "SELECT p FROM Provincia p WHERE p.codi = :codi")
    , @NamedQuery(name = "Provincia.findByCodiPostal", query = "SELECT p FROM Provincia p WHERE p.codiPostal = :codiPostal")})
public class Provincia implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codi")
    private String codi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codi_postal")
    private String codiPostal;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "provincia")
    private Collection<Poblacio> poblacioCollection;
    @JoinColumn(name = "pais_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pais pais;

    public Provincia() {
    }

    public Provincia(Integer id) {
        this.id = id;
    }

    public Provincia(Integer id, String nom, String codi, String codiPostal) {
        this.id = id;
        this.nom = nom;
        this.codi = codi;
        this.codiPostal = codiPostal;
    }
    
    public Provincia(String nom, String codi, String codiPostal, Pais pais) {
        this.nom = nom;
        this.codi = codi;
        this.codiPostal = codiPostal;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    @XmlTransient
    public Collection<Poblacio> getPoblacioCollection() {
        return poblacioCollection;
    }

    public void setPoblacioCollection(Collection<Poblacio> poblacioCollection) {
        this.poblacioCollection = poblacioCollection;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.adreces.model.Provincia[ id=" + id + " ]";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }
    
}
