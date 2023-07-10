package es.zarca.covellog.domain.model.empresa;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.exception.PotencialEstaBloqueadoException;
import es.zarca.covellog.domain.model.empresa.exception.PotencialNoEstaBloqueadoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_potencial")
@XmlRootElement
public class Potencial extends EmpresaRol implements Serializable {
    private static final String CONTACTOS_KEY = "POTENCIAL";
    private static final long serialVersionUID = 1L;
    /*@ManyToOne(optional = true)
    @JoinColumn(name = "contacto_principal_id", referencedColumnName = "id")
    private Contacto contacto;*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "empresa_potencial_comerciales", 
        joinColumns = @JoinColumn(name = "empresa_id"), 
        inverseJoinColumns = @JoinColumn(name = "comercial_id")
    )
    private List<Comercial> comerciales;
    
    public Potencial() {
    }

    public List<Contacto> getContactos() {
        return empresa.getContactos(new ContactoRol(CONTACTOS_KEY));
    }

    public void setContactos(List<Contacto> contactos) {
        empresa.contactosModificarRoles(contactos, new ContactoRol(CONTACTOS_KEY));
    }
    
    public List<Comercial> getComerciales() {
        if (comerciales == null) {
            comerciales = new ArrayList<>();
        }
        return comerciales;
    }

    public void addComercial(Comercial comercial) {
        if (comerciales == null) {
            comerciales = new ArrayList<>();
        }
        this.comerciales.add(comercial);
    }

    public void removeComercial(Comercial comercial) {
        if (comerciales == null) {
            comerciales = new ArrayList<>();
        }
        this.comerciales.remove(comercial);
    }

    public void setComerciales(List<Comercial> comerciales) {
        this.comerciales = comerciales;
        /*if (comerciales == null) {
            comerciales = new ArrayList<>();
        }
        for (Comercial comercialNuevo : comerciales) {
            if (!this.comerciales.contains(comercialNuevo)) {
                addComercial(comercialNuevo);
            }
        }
        List<Comercial> comercialesEliminar = new ArrayList<>();
        for (Comercial comercialViejo : this.comerciales) {
            if (!comerciales.contains(comercialViejo)) {
                comercialesEliminar.add(comercialViejo);                   
            }
        }
        for (Comercial comercialEliminar : comercialesEliminar) {
            removeComercial(comercialEliminar);
        }*/
    }
    
    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.Potencial[ id=" + empresaId + " ]";
    }

    void bloquear() {
        if (fechaBloqueo != null) {
            throw new PotencialEstaBloqueadoException(empresa);
        }
        fechaBloqueo = new Date();
    }

    void desbloquear() {
        if (fechaBloqueo == null) {
            throw new PotencialNoEstaBloqueadoException(empresa);
        }
        fechaBloqueo = null;
    }

}
