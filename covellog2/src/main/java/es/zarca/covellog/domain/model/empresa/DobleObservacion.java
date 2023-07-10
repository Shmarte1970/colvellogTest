package es.zarca.covellog.domain.model.empresa;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DobleObservacion implements Serializable {

    private static final long serialVersionUID = 1L;    
    @Lob
    @Size(max = 10000)
    @Column(name = "obs_internas")
    private String obsInternas;
    @Lob
    @Size(max = 10000)
    @Column(name = "obs_publicas")
    private String obsPublicas;

    public DobleObservacion() {
    }

    public DobleObservacion(String obsInternas, String obsPublicas) {
        this.obsInternas = obsInternas;
        this.obsPublicas = obsPublicas;
    }

    public String getObsInternas() {
        return obsInternas;
    }

    public String getObsPublicas() {
        return obsPublicas;
    }

    @Override
    public String toString() {
        return "DobleObservacion{" + "obsInternas=" + obsInternas + ", obsPublicas=" + obsPublicas + '}';
    }

    
}
