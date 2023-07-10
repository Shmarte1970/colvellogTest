package es.zarca.covellog.domain.model.producto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "tipo_producto_clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProductoClase.findAll", query = "SELECT t FROM TipoProductoClase t"),
    @NamedQuery(name = "TipoProductoClase.findById", query = "SELECT t FROM TipoProductoClase t WHERE t.id = :id"),
    @NamedQuery(name = "TipoProductoClase.findByNombre", query = "SELECT t FROM TipoProductoClase t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoProductoClase.findByClase", query = "SELECT t FROM TipoProductoClase t WHERE t.clase = :clase")})
public class TipoProductoClase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 200)
    @Column(name = "clase")
    private String clase;

    public TipoProductoClase() {
    }

    public TipoProductoClase(String id) {
        this.id = id;
    }

    public TipoProductoClase(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
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
        if (!(object instanceof TipoProductoClase)) {
            return false;
        }
        TipoProductoClase other = (TipoProductoClase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.producto.TipoProductoClase[ id=" + id + " ]";
    }
    
}
