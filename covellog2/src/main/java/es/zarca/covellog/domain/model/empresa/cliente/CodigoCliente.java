package es.zarca.covellog.domain.model.empresa.cliente;

import es.zarca.covellog.domain.model.adreces.exception.CodiPostalNotNullException;
import es.zarca.covellog.domain.model.empresa.exception.CodigoClienteIncorrectoException;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CodigoCliente implements Serializable {

    private static final long serialVersionUID = 1L;    
    @Basic(optional = false)
    @NotNull
    @Size(min = 8, max = 8)
    @Column(name = "codigo_cliente")
    private String codigo;

    public CodigoCliente() {
    }

    public CodigoCliente(String codigo) {
       if ((codigo.length() != 8) || (!codigo.matches("CL\\d+"))) { 
           throw new CodigoClienteIncorrectoException(codigo);
       }
       this.codigo = codigo;
    }

    public String getCodiString() {
        return codigo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CodigoCliente other = (CodigoCliente) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    boolean sameValueAs(CodigoCliente other) {
        return other != null && this.codigo.equals(other.codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
    
}
