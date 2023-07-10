/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.crm.repartidoroportunitats;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
@Table(name = "repartidor_oportunitats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepartidorOportunitats.findAll", query = "SELECT r FROM RepartidorOportunitats r")
    , @NamedQuery(name = "RepartidorOportunitats.findById", query = "SELECT r FROM RepartidorOportunitats r WHERE r.id = :id")
    , @NamedQuery(name = "RepartidorOportunitats.findByNom", query = "SELECT r FROM RepartidorOportunitats r WHERE r.nom = :nom")})
public class RepartidorOportunitats implements Serializable {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "repartidorOportunitats")
    private Collection<ReglaReparticioOportunitats> regles;

    public RepartidorOportunitats() {
    }

    public RepartidorOportunitats(String id) {
        this.id = id;
    }

    public RepartidorOportunitats(String id, String nom) {
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
    public Collection<ReglaReparticioOportunitats> getRegles() {
        return regles;
    }

    public void setRegles(Collection<ReglaReparticioOportunitats> regles) {
        this.regles = regles;
    }
        
    private List<Comercial> getCandidats() {
        List<Comercial> candidats = new ArrayList();
        for (ReglaReparticioOportunitats regla : regles) {
            if (!regla.esPlena()) {
                candidats.add(regla.getComercial());
            }
        }
        return candidats;
    }
    
    private void resetRegles() {
        for (ReglaReparticioOportunitats regla : regles) {
            regla.setQuantitat(0);
        }
    }
    
    public void repartirOportunitat(Oportunitat oportunitat) {
        List<Comercial> candidats = getCandidats();
        if (candidats.isEmpty()) {
            resetRegles();
        }
        candidats = getCandidats();
        System.out.println("candidats " + candidats.size());
        System.out.println("candidats " + candidats.size());
        System.out.println("candidats " + candidats.size());
        System.out.println("candidats " + candidats.size());
        System.out.println("candidats " + candidats.size());
        System.out.println("candidats " + candidats.size());
        System.out.println("candidats " + candidats.size());
        
        
        if (!candidats.isEmpty()) {
            int pos = (int) (Math.random() * candidats.size());
            System.out.println("SELECCIONADO EL " + String.valueOf(pos));
            Comercial seleccionat = candidats.get(pos);
            System.out.println("QUE ES EL " + seleccionat.getNombreCompleto());
            oportunitat.setComercial(seleccionat);
            for (ReglaReparticioOportunitats regla : regles) {
                if (regla.getComercial() == seleccionat) {
                    regla.consumir();
                }
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
        if (!(object instanceof RepartidorOportunitats)) {
            return false;
        }
        RepartidorOportunitats other = (RepartidorOportunitats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.crm.repartidoroportunitats.RepartidorOportunitats[ id=" + id + " ]";
    }
    
}
