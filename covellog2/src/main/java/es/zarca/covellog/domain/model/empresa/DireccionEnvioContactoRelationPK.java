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
public class DireccionEnvioContactoRelationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "direccion_envio_id", insertable = false, updatable = false, nullable = false)
    private int direccionEnvioId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto_id", insertable = false, updatable = false, nullable = false)
    private int contactoId;

    public DireccionEnvioContactoRelationPK() {
    }

    public DireccionEnvioContactoRelationPK(int direccionEnvioId, int contactoId) {
        this.direccionEnvioId = direccionEnvioId;
        this.contactoId = contactoId;
    }

    public int getDireccionEnvioId() {
        return direccionEnvioId;
    }

    public void setDireccionEnvioId(int direccionEnvioId) {
        this.direccionEnvioId = direccionEnvioId;
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
        hash += (int) direccionEnvioId;
        hash += (int) contactoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DireccionEnvioContactoRelationPK)) {
            return false;
        }
        DireccionEnvioContactoRelationPK other = (DireccionEnvioContactoRelationPK) object;
        if (this.direccionEnvioId != other.direccionEnvioId) {
            return false;
        }
        if (this.contactoId != other.contactoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.EmpresaDireccionEnvioContactoPK[ direccionEnvioId=" + direccionEnvioId + ", contactoId=" + contactoId + " ]";
    }
    
}
