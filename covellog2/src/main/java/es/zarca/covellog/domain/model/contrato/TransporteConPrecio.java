package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.transporte.Transporte;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author francisco
 */
public class TransporteConPrecio {
    private final Transporte transporte;
    private final BigDecimal importe;

    public TransporteConPrecio(Transporte transporte, BigDecimal importe) {
        this.transporte = transporte;
        this.importe = importe;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.transporte);
        hash = 19 * hash + Objects.hashCode(this.importe);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransporteConPrecio other = (TransporteConPrecio) obj;
        if (!Objects.equals(this.transporte, other.transporte)) {
            return false;
        }
        if (!Objects.equals(this.importe, other.importe)) {
            return false;
        }
        return true;
    }
    
    

}
