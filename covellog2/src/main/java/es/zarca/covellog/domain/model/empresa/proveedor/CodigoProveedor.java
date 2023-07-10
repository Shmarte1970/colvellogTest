package es.zarca.covellog.domain.model.empresa.proveedor;

import es.zarca.covellog.domain.model.adreces.exception.CodiPostalNotNullException;
import es.zarca.covellog.domain.model.empresa.exception.CodigoClienteIncorrectoException;
import es.zarca.covellog.domain.model.empresa.proveedor.exception.CodigoProveedorIncorrectoException;
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
public class CodigoProveedor implements Serializable {

    private static final long serialVersionUID = 1L;    
    @Basic(optional = false)
    @NotNull
    @Size(min = 8, max = 8)
    @Column(name = "codigo_proveedor")
    private String codigo;

    public CodigoProveedor() {
    }

    public CodigoProveedor(String codigo) {
       if (codigo == null) {
           throw new CodiPostalNotNullException();
       }
       if ((codigo.length() != 8) || (!codigo.matches("PR\\d+"))) { 
           throw new CodigoProveedorIncorrectoException(codigo);
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

        CodigoProveedor other = (CodigoProveedor) o;

        return sameValueAs(other);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    boolean sameValueAs(CodigoProveedor other) {
        return other != null && this.codigo.equals(other.codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
    
}
