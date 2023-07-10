package es.zarca.covellog.domain.model.ubicacion;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.ubicacion.exception.ContactoRepetidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "ubicacion_con_contactos_fijos")
@XmlRootElement
public abstract class UbicacionConContactosFijos extends Ubicacion implements Serializable {

    @OneToMany(
        fetch = FetchType.EAGER, 
        cascade = CascadeType.ALL, 
        mappedBy = "ubicacion", 
        orphanRemoval = true
    )
    @OrderBy("orden ASC")
    protected List<UbicacionContactoFijo> contactos = new ArrayList();

    @Override
    public List<? extends Contacto> getContactos() {
        return contactos;
    }

    @Override
    public void setContactos(List<Contacto> contactos) {
        removeContactos();
        contactos.forEach(contacto -> {
            this.contactos.add(new UbicacionContactoFijo(this, contacto));
        });
    }
    
    @Override
    public void addContacto(Contacto contacto) {
        int index = contactos.indexOf(contacto);
        if (index >= 0) {
            throw new ContactoRepetidoException(this, contacto);
        }
        contactos.add(new UbicacionContactoFijo(this, contacto));
    }

    @Override
    public void removeContacto(Contacto contacto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeContactos() {
        if (contactos != null) {
            while(contactos.size() > 0) {
                contactos.remove(0);
            }
        }
    }

   /* @Override
    public void updateContacto(Contacto contacto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}