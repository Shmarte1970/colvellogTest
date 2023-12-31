package es.zarca.covellog.domain.model.empresa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "empresa_tipo_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpresaTipoRol.findAll", query = "SELECT e FROM EmpresaTipoRol e"),
    @NamedQuery(name = "EmpresaTipoRol.findById", query = "SELECT e FROM EmpresaTipoRol e WHERE e.id = :id"),
    @NamedQuery(name = "EmpresaTipoRol.findByNombre", query = "SELECT e FROM EmpresaTipoRol e WHERE e.nombre = :nombre")})
public class EmpresaTipoRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    public EmpresaTipoRol() {
    }

    public EmpresaTipoRol(Integer id) {
        this.id = id;
    }

    public EmpresaTipoRol(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof EmpresaTipoRol)) {
            return false;
        }
        EmpresaTipoRol other = (EmpresaTipoRol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.EmpresaTipoRol[ id=" + id + " ]";
    }
    
}
