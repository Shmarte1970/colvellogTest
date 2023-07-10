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
public class PalabrasClave implements Serializable {

    private static final long serialVersionUID = 1L;    
    @Lob
    @Size(max = 65535)
    @Column(name = "palabras_clave")
    private String palabrasClave;
    
    public PalabrasClave() {
        palabrasClave = "";
    }

    public PalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public String getString() {
        return palabrasClave;
    }

    @Override
    public String toString() {
        return getString();
    }

    
}
