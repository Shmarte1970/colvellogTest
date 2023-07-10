package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class InfoTransporte implements Serializable {
    @Basic(optional = true)
    @Column(name = "transporte_transportista_nombre")
    private String transportistaNombre;
    @Basic(optional = true)
    @Column(name = "transporte_nombre")
    private String nombre;
    @Basic(optional = true)
    @Column(name = "transporte_capacidad")
    private Integer capacidad;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="obsInternas",column=@Column(name="transporte_obs_internas")),
        @AttributeOverride(name="obsPublicas",column=@Column(name="transporte_obs_publicas"))
    })
    private DobleObservacion observaciones;

    public InfoTransporte() {
    }

    public InfoTransporte(String nombre) {
        this.transportistaNombre = null;
        this.nombre = nombre;
        this.capacidad = null;
        this.observaciones = new DobleObservacion();
    }
    
    public InfoTransporte(String transportistaNombre, String nombre, Integer capacidad, DobleObservacion observaciones) {
        this.transportistaNombre = transportistaNombre;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.observaciones = observaciones;
    }
    
    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.transportistaNombre);
        hash = 83 * hash + Objects.hashCode(this.nombre);
        hash = 83 * hash + Objects.hashCode(this.capacidad);
        hash = 83 * hash + Objects.hashCode(this.observaciones);
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
        final InfoTransporte other = (InfoTransporte) obj;
        if (!Objects.equals(this.transportistaNombre, other.transportistaNombre)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.capacidad, other.capacidad)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        return true;
    }

    
}