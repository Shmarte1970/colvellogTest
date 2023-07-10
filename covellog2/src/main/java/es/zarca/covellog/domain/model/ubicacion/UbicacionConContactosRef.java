package es.zarca.covellog.domain.model.ubicacion;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.ubicacion.exception.ContactoRepetidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "ubicacion_con_contactos_ref")
@XmlRootElement
public  class UbicacionConContactosRef extends Ubicacion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion", fetch = FetchType.LAZY, orphanRemoval = true)
    //@OrderBy("orden ASC") //Parece que solo ordena cuando se lo coge de la BD, luego en cache no funciona. Por eso se ordena a mano
    private final List<UbicacionConContactoRefRelation> contactosRelation = new ArrayList<>();

    @Override
    public List<Contacto> getContactos() {
        List<Contacto> contactos = new ArrayList<>();
        contactosRelation.sort(Comparator.comparing(UbicacionConContactoRefRelation::getOrden));
        for (UbicacionConContactoRefRelation contactoRelation : contactosRelation) {
            contactos.add(contactoRelation.getContacto());
        }
        return contactos;
    }
    
    private UbicacionConContactoRefRelation getRelation(Contacto contacto) {
        for (UbicacionConContactoRefRelation contactoRelation : contactosRelation) {
            if (contactoRelation.getContacto().equals(contacto)) {
                return contactoRelation;
            }
        }
        return null;
    }

    @Override
    public void setContactos(List<Contacto> contactos) {
        int orden = 1;
        for (Contacto contacto: contactos) {
            UbicacionConContactoRefRelation relation = getRelation(contacto);
            if (relation == null) {
                contactosRelation.add(new UbicacionConContactoRefRelation(this, contacto, orden));
            } else {
                relation.setOrden(orden);
            }
            orden++;
        }
        List<UbicacionConContactoRefRelation> eliminar = new ArrayList<>();

        for (UbicacionConContactoRefRelation relation : contactosRelation) {
            if (!contactos.contains(relation.getContacto())) {
                eliminar.add(relation);
            }
        }
        for (UbicacionConContactoRefRelation relation : eliminar) {
            contactosRelation.remove(relation);
        }
    }
    
    @Override
    public void addContacto(Contacto contacto) {
        UbicacionConContactoRefRelation relation = getRelation(contacto);
        if (relation != null) {
            throw new ContactoRepetidoException(this, contacto);
        }
        contactosRelation.add(new UbicacionConContactoRefRelation(this, contacto, contactosRelation.size() + 1));
    }

    @Override
    public void removeContacto(Contacto contacto) {
        UbicacionConContactoRefRelation relation = getRelation(contacto);
        if (relation != null) {
            contactosRelation.remove(relation);
        }
    }

    @Override
    public void removeContactos() {
        if (contactosRelation != null) {
            while(contactosRelation.size() > 0) {
                contactosRelation.remove(0);
            }
        }
    }
    
}