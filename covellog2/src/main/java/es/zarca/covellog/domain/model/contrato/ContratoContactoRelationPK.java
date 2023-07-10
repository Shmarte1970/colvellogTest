package es.zarca.covellog.domain.model.contrato;

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
class ContratoContactoRelationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "contrato_id", insertable = false, updatable = false, nullable = false)
    private int contratoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto_id", insertable = false, updatable = false, nullable = false)
    private int contactoId;

    public ContratoContactoRelationPK() {
    }

    public ContratoContactoRelationPK(int contratoId, int contactoId) {
        this.contratoId = contratoId;
        this.contactoId = contactoId;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
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
        hash += (int) contratoId;
        hash += (int) contactoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ContratoContactoRelationPK)) {
            return false;
        }
        ContratoContactoRelationPK other = (ContratoContactoRelationPK) object;
        if (this.contratoId != other.contratoId) {
            return false;
        }
        if (this.contactoId != other.contactoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.ContratoContactoPK[ contratoId=" + contratoId + ", contactoId=" + contactoId + " ]";
    }
    
}
