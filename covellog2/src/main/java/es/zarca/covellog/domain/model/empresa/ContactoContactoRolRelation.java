package es.zarca.covellog.domain.model.empresa;

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
@Table(name = "contacto_contacto_rol_relation")
@XmlRootElement
public class ContactoContactoRolRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContactoContactoRolRelationPK id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="contacto_id", insertable = true, updatable = true, nullable = true)
    private Contacto contacto;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="rol_id", insertable = true, updatable = true, nullable = true)
    private ContactoRol contactoRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;

    public ContactoContactoRolRelation() {
        id = new ContactoContactoRolRelationPK();
    }

    public ContactoContactoRolRelation(ContactoContactoRolRelationPK contactoContactoRolRelationPK) {
        this.id = contactoContactoRolRelationPK;
    }

    public ContactoContactoRolRelation(ContactoContactoRolRelationPK contactoContactoRolRelationPK, int nivel) {
        this.id = contactoContactoRolRelationPK;
        this.nivel = nivel;
    }

    public ContactoContactoRolRelation(int contactoId, String rolId) {
        this.id = new ContactoContactoRolRelationPK(contactoId, rolId);
    }

    public ContactoContactoRolRelation(Contacto contacto, ContactoRol rol) {
        this.id = new ContactoContactoRolRelationPK(contacto.getId(), rol.getId());
        this.contacto = contacto;
        this.contactoRol = rol;
    }
    
    public ContactoContactoRolRelation(Contacto contacto, ContactoRol rol, int nivel) {
        this.id = new ContactoContactoRolRelationPK(contacto.getId(), rol.getId());
        this.contacto = contacto;
        this.contactoRol = rol;
        this.nivel = nivel;
    }

    public ContactoContactoRolRelationPK getId() {
        return id;
    }

    public void setId(ContactoContactoRolRelationPK id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
        this.id.setContactoId(contacto.getId());
    }

    public ContactoRol getContactoRol() {
        return contactoRol;
    }

    public void setContactoRol(ContactoRol contactoRol) {
        this.contactoRol = contactoRol;
        this.id.setRolId(contactoRol.getId());
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
        if (!(object instanceof ContactoContactoRolRelation)) {
            return false;
        }
        ContactoContactoRolRelation other = (ContactoContactoRolRelation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.ContactoContactoRolRelation[ contactoContactoRolRelationPK=" + id + " ]";
    }
    
}
