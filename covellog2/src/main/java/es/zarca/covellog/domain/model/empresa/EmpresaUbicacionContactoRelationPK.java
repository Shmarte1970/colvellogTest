package es.zarca.covellog.domain.model.empresa;

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
public class EmpresaUbicacionContactoRelationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ubicacion_id", insertable = false, updatable = false, nullable = false)
    private int ubicacionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto_id", insertable = false, updatable = false, nullable = false)
    private int contactoId;

    public EmpresaUbicacionContactoRelationPK() {
    }

    public EmpresaUbicacionContactoRelationPK(int ubicacionId, int contactoId) {
        this.ubicacionId = ubicacionId;
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
        if (!(object instanceof EmpresaUbicacionContactoRelationPK)) {
            return false;
        }
        EmpresaUbicacionContactoRelationPK other = (EmpresaUbicacionContactoRelationPK) object;
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
        return "es.zarca.covellog.domain.model.empresa.EmpresaUbicacionContactoPK[ ubicacionId=" + ubicacionId + ", contactoId=" + contactoId + " ]";
    }
    
}
