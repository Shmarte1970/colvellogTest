package es.zarca.covellog.interfaces.facade.stock.dto;

import java.io.Serializable;

public class EstadoDto implements Serializable {
    
    private String id;
    private String nombre;

    public EstadoDto() {
    }

    public EstadoDto(String id) {
        this.id = id;
    }

    public EstadoDto(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoDto)) {
            return false;
        }
        EstadoDto other = (EstadoDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
