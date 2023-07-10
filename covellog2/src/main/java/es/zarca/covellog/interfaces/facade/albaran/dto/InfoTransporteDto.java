package es.zarca.covellog.interfaces.facade.albaran.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import java.io.Serializable;

public class InfoTransporteDto implements Serializable {
    private Integer id;
    private String transportistaNombre;
    private String nombre;
    private Integer capacidad;
    private DobleObservacionDto observaciones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }
    
}
