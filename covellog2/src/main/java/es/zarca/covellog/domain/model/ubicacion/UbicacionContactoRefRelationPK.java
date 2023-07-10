package es.zarca.covellog.domain.model.ubicacion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco
 */
@Embeddable
public class UbicacionContactoRefRelationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ubicacion_id", insertable = false, updatable = false, nullable = false)
    private int ubicacionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto_id", insertable = false, updatable = false, nullable = false)
    private int contactoId;

    public UbicacionContactoRefRelationPK() {
    }

    public UbicacionContactoRefRelationPK(int direccionEnvioId, int contactoId) {
        this.ubicacionId = direccionEnvioId;
        this.contactoId = contactoId;
    }

    public int getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(int ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public int getContactoId() {
        return contactoId;
    }

    public void setContactoId(int contactoId) {
        this.contactoId = contactoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ubicacionId;
        hash += (int) contactoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UbicacionContactoRefRelationPK)) {
            return false;
        }
        UbicacionContactoRefRelationPK other = (UbicacionContactoRefRelationPK) object;
        if (this.ubicacionId != other.ubicacionId) {
            return false;
        }
        if (this.contactoId != other.contactoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.EmpresaDireccionEnvioContactoPK[ direccionEnvioId=" + ubicacionId + ", contactoId=" + contactoId + " ]";
    }
    
}
