package es.zarca.covellog.domain.model.idiomas.idioma;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "idioma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i ORDER BY i.id.codigo ASC"), 
    @NamedQuery(name = "Idioma.findById", query = "SELECT i FROM Idioma i WHERE i.id.codigo = :id")})
public class Idioma implements Serializable {
    private static final long serialVersionUID = 2L;
    
    @EmbeddedId
    @AttributeOverride(name="codigo", column=@Column(name="id"))
    private CodigoIdioma id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;

    public Idioma() {
    }

    public Idioma(CodigoIdioma id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CodigoIdioma getId() {
        return id;
    }

    public void setId(CodigoIdioma id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Idioma other = (Idioma) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Idioma{" + "id=" + id + ", nombre=" + nombre + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
}
