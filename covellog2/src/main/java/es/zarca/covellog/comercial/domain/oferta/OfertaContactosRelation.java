package es.zarca.covellog.comercial.domain.oferta;

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
@Table(name = "oferta_contactos_relation")
@XmlRootElement
class OfertaContactoRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OfertaContactoRelationPK id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="oferta_id", insertable = true, updatable = true, nullable = true)
    private Oferta oferta;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="contacto_id", insertable = true, updatable = true, nullable = true)
    private Contacto contacto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nivel")
    private int nivel;      

    public OfertaContactoRelation() {
        id = new OfertaContactoRelationPK(); 
    }

    public OfertaContactoRelation(Oferta oferta, Contacto contacto, Integer nivel) {
        this.oferta = oferta;
        this.contacto = contacto;
        this.nivel = nivel;
    }
    
    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
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
        if (!(object instanceof OfertaContactoRelation)) {
            return false;
        }
        OfertaContactoRelation other = (OfertaContactoRelation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.OfertaContacto[ id=" + id + " ]";
    }
    
}
