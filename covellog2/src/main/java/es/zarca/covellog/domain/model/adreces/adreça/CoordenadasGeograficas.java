package es.zarca.covellog.domain.model.adreces.adreça;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class CoordenadasGeograficas implements Serializable {

    @Column(name = "latitud")
    private BigDecimal latitud;
    @Column(name = "longitud")
    private BigDecimal longitud;

    protected CoordenadasGeograficas() {
    }

    public CoordenadasGeograficas(BigDecimal latitud, BigDecimal longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    
    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.latitud);
        hash = 67 * hash + Objects.hashCode(this.longitud);
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
        final CoordenadasGeograficas other = (CoordenadasGeograficas) obj;
        if (!Objects.equals(this.latitud, other.latitud)) {
            return false;
        }
        if (!Objects.equals(this.longitud, other.longitud)) {
            return false;
        }
        return true;
    }

    
}