package es.zarca.covellog.domain.model.empresa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_ubicacion_contactos_relation")
@XmlRootElement
public class EmpresaUbicacionContactoRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EmpresaUbicacionContactoRelationPK id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ubicacion_id", insertable = true, updatable = true, nullable = true)
    private EmpresaUbicacion ubicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="contacto_id", insertable = true, updatable = true, nullable = true)
    private EmpresaContacto contacto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;      

    public EmpresaUbicacionContactoRelation() {
        id = new EmpresaUbicacionContactoRelationPK();
    }

    public EmpresaUbicacionContactoRelation(EmpresaUbicacion ubicacion, EmpresaContacto contacto, Integer nivel) {
        this.ubicacion = ubicacion;
        this.contacto = contacto;
        this.nivel = nivel;
    }
    
    public EmpresaUbicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(EmpresaUbicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public EmpresaContacto getContacto() {
        return contacto;
    }

    public void setContacto(EmpresaContacto contacto) {
        this.contacto = contacto;
        id.setContactoId(contacto.getId());
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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
        if (!(object instanceof EmpresaUbicacionContactoRelation)) {
            return false;
        }
        EmpresaUbicacionContactoRelation other = (EmpresaUbicacionContactoRelation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.EmpresaUbicacionContacto[ id=" + id + " ]";
    }
    
}
