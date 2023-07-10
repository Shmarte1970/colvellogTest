package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.ubicacion.UbicacionConContactosFijos;
import java.io.Serializable;
import javax.persistence.CascadeType;
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
@Table(name = "albaran_ubicacion_origen")
@XmlRootElement
class UbicacionOrigen extends UbicacionConContactosFijos implements Serializable {

    @JoinColumn(name = "albaran_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Albaran albaran;

    public UbicacionOrigen() {
    }

    public UbicacionOrigen(Albaran albaran) {
        this.albaran = albaran;
    }
        
}