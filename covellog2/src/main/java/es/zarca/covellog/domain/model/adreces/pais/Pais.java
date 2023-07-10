/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.adreces.pais;

import es.zarca.covellog.application.adreces.exception.ProvinciaCodiNotUniqueException;
import es.zarca.covellog.application.adreces.exception.ProvinciaNomNotUniqueException;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "pais")
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p ORDER BY p.nom ASC")
    , @NamedQuery(name = "Pais.findById", query = "SELECT p FROM Pais p WHERE p.id = :id")
    , @NamedQuery(name = "Pais.findByCodiIso2", query = "SELECT p FROM Pais p WHERE p.codiIso2 = :codiIso2")
    , @NamedQuery(name = "Pais.findByNom", query = "SELECT p FROM Pais p WHERE p.nom = :nom")})
public class Pais implements Serializable {


    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nom")
    private String nom;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    //@Basic(optional = false)
    //@NotNull
   // @Size(min = 1, max = 2)
    //@Column(name = "codiIso2")
    @Embedded
    private CodiIsoPais2 codiIso2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pais")
    private Collection<Provincia> provinciaCollection;

    public Pais() {
    }

    public Pais(Integer id) {
        this.id = id;
    }

    public Pais(CodiIsoPais2 codiIso2, String nom) {
        this.codiIso2 = codiIso2;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CodiIsoPais2 getCodiIso2() {
        return codiIso2;
    }

    public void setCodiIso2(CodiIsoPais2 codiIso2) {
        this.codiIso2 = codiIso2;
    }


    public Collection<Provincia> getProvinciaCollection() {
        return provinciaCollection;
    }

    public void setProvinciaCollection(Collection<Provincia> provinciaCollection) {
        this.provinciaCollection = provinciaCollection;
    }
    
    public void altaProvincia(String nom, String codi, String codiPostal) {
        Collection<Provincia> provincies = getProvinciaCollection();
        for(Provincia provincia : provincies) {
            if (provincia.getNom().equals(nom)) {
                throw new ProvinciaNomNotUniqueException(provincia);
            }
            if (provincia.getCodi().equals(codi)) { 
                throw new ProvinciaCodiNotUniqueException(provincia);
            }
            if (provincia.getCodiPostal().equals(codiPostal)) {
                throw new ProvinciaCodiNotUniqueException(provincia);
            }
        }
        this.provinciaCollection.add(new Provincia(nom, codi, codiPostal, this));
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
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.adreces.model.Pais[ id=" + id + " ]";
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}
