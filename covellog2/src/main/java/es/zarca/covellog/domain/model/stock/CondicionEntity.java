package es.zarca.covellog.domain.model.stock;

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
@Table(name = "stock_condicion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CondicionEntity.findAll", query = "SELECT s FROM CondicionEntity s"),
    @NamedQuery(name = "CondicionEntity.findById", query = "SELECT s FROM CondicionEntity s WHERE s.id = :id"),
    @NamedQuery(name = "CondicionEntity.findByNombre", query = "SELECT s FROM CondicionEntity s WHERE s.nombre = :nombre")})
public class CondicionEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;

    public CondicionEntity() {
    }

    public CondicionEntity(String id) {
        this.id = id;
    }

    public CondicionEntity(String id, String nombre) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CondicionEntity)) {
            return false;
        }
        CondicionEntity other = (CondicionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.CondicionEntity[ id=" + id + " ]";
    }
    
}
