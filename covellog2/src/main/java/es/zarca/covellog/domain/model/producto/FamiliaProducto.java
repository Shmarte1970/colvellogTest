package es.zarca.covellog.domain.model.producto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "familia_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FamiliaProducto.findAll", query = "SELECT f FROM FamiliaProducto f"),
    @NamedQuery(name = "FamiliaProducto.findById", query = "SELECT f FROM FamiliaProducto f WHERE f.id = :id"),
    @NamedQuery(name = "FamiliaProducto.findByNombre", query = "SELECT f FROM FamiliaProducto f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "FamiliaProducto.findByObservaciones", query = "SELECT f FROM FamiliaProducto f WHERE f.observaciones = :observaciones")})
public class FamiliaProducto implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familia")
    private List<TipoProducto> tipoProductoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    
    public FamiliaProducto() {
    }

    public FamiliaProducto(Integer id) {
        this.id = id;
    }

    public FamiliaProducto(Integer id, String nombre, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void baja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public void anularBaja() {
        this.fechaBaja = null;
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
        if (!(object instanceof FamiliaProducto)) {
            return false;
        }
        FamiliaProducto other = (FamiliaProducto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.producto.FamiliaProducto[ id=" + id + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @XmlTransient
    public List<TipoProducto> getTipoProductoList() {
        return tipoProductoList;
    }

    public void setTipoProductoList(List<TipoProducto> tipoProductoList) {
        this.tipoProductoList = tipoProductoList;
    }
    
}
