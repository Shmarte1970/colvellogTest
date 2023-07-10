package es.zarca.covellog.domain.model.ubicacion;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.ContactoRol;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "ubicacion_contacto_fijo")
@XmlRootElement
public class UbicacionContactoFijo extends Contacto implements Serializable {
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ubicacion ubicacion;
    @Basic(optional = false)
    @Column(name = "orden")
    private Integer orden;

    public UbicacionContactoFijo() {
    }
    
    public UbicacionContactoFijo(UbicacionConContactosFijos ubicacion, Contacto contacto) {
        super(contacto);
        this.ubicacion = ubicacion;
        this.orden = ubicacion.getContactos().size();
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
}