package es.zarca.covellog.comercial.domain.oferta;

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
class OfertaContactoRelationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "oferta_id", insertable = false, updatable = false, nullable = false)
    private int ofertaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto_id", insertable = false, updatable = false, nullable = false)
    private int contactoId;

    public OfertaContactoRelationPK() {
    }

    public OfertaContactoRelationPK(int ofertaId, int contactoId) {
        this.ofertaId = ofertaId;
        this.contactoId = contactoId;
    }

    public int getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(int ofertaId) {
        this.ofertaId = ofertaId;
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
        hash += (int) ofertaId;
        hash += (int) contactoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OfertaContactoRelationPK)) {
            return false;
        }
        OfertaContactoRelationPK other = (OfertaContactoRelationPK) object;
        if (this.ofertaId != other.ofertaId) {
            return false;
        }
        if (this.contactoId != other.contactoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.OfertaContactoPK[ ofertaId=" + ofertaId + ", contactoId=" + contactoId + " ]";
    }
    
}
