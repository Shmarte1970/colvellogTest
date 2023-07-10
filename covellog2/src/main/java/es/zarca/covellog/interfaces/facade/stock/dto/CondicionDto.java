package es.zarca.covellog.interfaces.facade.stock.dto;

import java.io.Serializable;

public class CondicionDto implements Serializable {
    private String id;
    private String nombre;

    public CondicionDto() {
    }

    public CondicionDto(String id) {
        this.id = id;
    }

    public CondicionDto(String id, String nombre) {
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
        if (!(object instanceof CondicionDto)) {
            return false;
        }
        CondicionDto other = (CondicionDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.stock.Condicion[ id=" + id + " ]";
    }
    
}
