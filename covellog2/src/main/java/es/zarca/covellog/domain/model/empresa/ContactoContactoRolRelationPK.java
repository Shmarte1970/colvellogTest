package es.zarca.covellog.domain.model.empresa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@Embeddable
public class ContactoContactoRolRelationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "contacto_id", insertable = false, updatable = false, nullable = false)
    private int contactoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "rol_id", insertable = false, updatable = false, nullable = false)
    private String rolId;

    public ContactoContactoRolRelationPK() {
    }

    public ContactoContactoRolRelationPK(int contactoId, String rolId) {
        this.contactoId = contactoId;
        this.rolId = rolId;
    }

    public int getContactoId() {
        return contactoId;
    }

    public void setContactoId(int contactoId) {
        this.contactoId = contactoId;
    }

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contactoId;
        hash += (rolId != null ? rolId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactoContactoRolRelationPK)) {
            return false;
        }
        ContactoContactoRolRelationPK other = (ContactoContactoRolRelationPK) object;
        if (this.contactoId != other.contactoId) {
            return false;
        }
        if ((this.rolId == null && other.rolId != null) || (this.rolId != null && !this.rolId.equals(other.rolId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.ContactoContactoRolRelationPK[ contactoId=" + contactoId + ", rolId=" + rolId + " ]";
    }
    
}
