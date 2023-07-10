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
@Table(name = "estat_oportunitat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstatOportunitat.findAll", query = "SELECT e FROM EstatOportunitat e")
    , @NamedQuery(name = "EstatOportunitat.findById", query = "SELECT e FROM EstatOportunitat e WHERE e.id = :id")
    , @NamedQuery(name = "EstatOportunitat.findByNom", query = "SELECT e FROM EstatOportunitat e WHERE e.nom = :nom")})
public class EstatOportunitat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom")
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estat")
    private Collection<Oportunitat> oportunitatCollection;

    public EstatOportunitat() {
    }

    public EstatOportunitat(String id) {
        this.id = id;
    }

    public EstatOportunitat(String id, String nom) {
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
        if (!(object instanceof EstatOportunitat)) {
            return false;
        }
        EstatOportunitat other = (EstatOportunitat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat[ id=" + id + " ]";
    }
    
}
