package es.zarca.covellog.domain.model.empresa;

import es.zarca.covellog.domain.model.ubicacion.UbicacionConContactosRef;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_direccion_envio")
@XmlRootElement
public class EmpresaDireccionEnvio extends UbicacionConContactosRef implements Serializable {

    private static final long serialVersionUID = 1L;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "direccionEnvio", fetch = FetchType.LAZY, orphanRemoval = true)
    ////@OrderBy("nivel ASC")
    //private List<DireccionEnvioContactoRelation> contactosRelation = new ArrayList<>();
    
   /* @Override
    public void addContacto(Contacto contacto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeContacto(Contacto contacto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeContactos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

    /*@Override
    public void updateContacto(Contacto contacto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
    
    
   /* private DireccionEnvioContactoRelation getRelation(Contacto contacto) {
        for (DireccionEnvioContactoRelation contactoRelation : contactosRelation) {
            if (contactoRelation.getContacto().equals(contacto)) {
                return contactoRelation;
            }
        }
        return null;
    }

    @Override
    public List<Contacto> getContactos() {
        List<Contacto> contactos = new ArrayList<>();
        contactosRelation.sort(Comparator.comparing(DireccionEnvioContactoRelation::getNivel));
        contactosRelation.forEach(contactoRelation -> {
            contactos.add(contactoRelation.getContacto());
        });
        
        return contactos;
    }
    
    @Override
    public void setContactos(List<Contacto> contactos) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        int i = 0;
        for (Contacto contacto: contactos) {
            DireccionEnvioContactoRelation relation = getRelation(contacto);
            if (relation == null) {
                contactosRelation.add(new DireccionEnvioContactoRelation(this, contacto, i));
            } else {
                relation.setNivel(i);
            }
            i++;
        }
        List<DireccionEnvioContactoRelation> eliminar = new ArrayList<>();

        for (DireccionEnvioContactoRelation relation : contactosRelation) {
            if (!contactos.contains(relation.getContacto())) {
                eliminar.add(relation);
            }
        }
        for (DireccionEnvioContactoRelation relation : eliminar) {
            contactosRelation.remove(relation);
        }
        log.finish();
    }*/

}
