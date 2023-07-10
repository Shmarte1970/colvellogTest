package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.ubicacion.UbicacionConContactosFijos;
import java.io.Serializable;
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
@Table(name = "albaran_ubicacion_destino")
@XmlRootElement
class UbicacionDestino extends UbicacionConContactosFijos implements Serializable {

    @JoinColumn(name = "albaran_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Albaran albaran;

    public Albaran getAlbaran() {
        return albaran;
    }

    public void setAlbaran(Albaran albaran) {
        this.albaran = albaran;
    }
    
}