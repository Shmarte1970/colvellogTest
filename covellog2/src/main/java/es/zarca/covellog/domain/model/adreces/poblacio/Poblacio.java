package es.zarca.covellog.domain.model.adreces.poblacio;

import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author usuario
 */
@Entity
@Table(name = "poblacio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poblacio.findAll", query = "SELECT p FROM Poblacio p")
    , @NamedQuery(name = "Poblacio.findById", query = "SELECT p FROM Poblacio p WHERE p.id = :id")
    , @NamedQuery(name = "Poblacio.findByNom", query = "SELECT p FROM Poblacio p WHERE p.nom = :nom")
    , @NamedQuery(name = "Poblacio.findByCodiMunicipi", query = "SELECT p FROM Poblacio p WHERE p.codiMunicipi = :codiMunicipi")
    , @NamedQuery(name = "Poblacio.findByDc", query = "SELECT p FROM Poblacio p WHERE p.dc = :dc")})
public class Poblacio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Provincia provincia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "codi_municipi")
    private String codiMunicipi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dc")
    private short dc;

    public Poblacio() {
    }

    public Poblacio(Integer id) {
        this.id = id;
    }

    public Poblacio(Integer id, String nom, String codiMunicipi, short dc) {
        this.id = id;
        this.nom = nom;
        this.codiMunicipi = codiMunicipi;
        this.dc = dc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public short getDc() {
        return dc;
    }

    public void setDc(short dc) {
        this.dc = dc;
    }
  

    public String getCodiMunicipi() {
        return codiMunicipi;
    }

    public void setCodiMunicipi(String codiMunicipi) {
        this.codiMunicipi = codiMunicipi;
    }


    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
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
        if (!(object instanceof Poblacio)) {
            return false;
        }
        Poblacio other = (Poblacio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.adreces.model.Poblacio[ id=" + id + " ]";
    }


}
