package es.zarca.covellog.domain.model.ubicacion;

import es.zarca.covellog.domain.model.contactos.Contacto;
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
@Table(name = "ubicacion_con_contactos_ref_relation")
@XmlRootElement
public class UbicacionConContactoRefRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UbicacionContactoRefRelationPK id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ubicacion_id", insertable = true, updatable = true, nullable = true)
    private UbicacionConContactosRef ubicacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="contacto_id", insertable = true, updatable = true, nullable = true)
    private Contacto contacto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private int orden;

    public UbicacionConContactoRefRelation() {
        id = new UbicacionContactoRefRelationPK();
    }

    public UbicacionConContactoRefRelation(UbicacionConContactosRef ubicacion, Contacto contacto, Integer nivel) {
       //id = new UbicacionContactoRelationPK(ubicacion.getId(), contacto.getId());
        this.ubicacion = ubicacion;
        this.contacto = contacto;
        this.orden = nivel;
    }
    
    public UbicacionConContactosRef getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionConContactosRef ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
        id.setContactoId(contacto.getId());
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
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
        if (!(object instanceof UbicacionConContactoRefRelation)) {
            return false;
        }
        UbicacionConContactoRefRelation other = (UbicacionConContactoRefRelation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
