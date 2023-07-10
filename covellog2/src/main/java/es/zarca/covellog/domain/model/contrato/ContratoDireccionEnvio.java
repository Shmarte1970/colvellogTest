package es.zarca.covellog.domain.model.contrato;

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
@Table(name = "contrato_direccion_envio")
@XmlRootElement
class ContratoDireccionEnvio extends UbicacionConContactosFijos implements Serializable {

    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Contrato contrato;

    public ContratoDireccionEnvio() {
    }

    public ContratoDireccionEnvio(Contrato contrato) {
        this.contrato = contrato;
    }
        
}