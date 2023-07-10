package es.zarca.covellog.interfaces.facade.stock.dto;

import java.io.Serializable;

public class IdCantidadDto implements Serializable {

    private String id;
    private Long cantidad;

    public IdCantidadDto() {
    }

    public IdCantidadDto(String id) {
        this.id = id;
    }

    public IdCantidadDto(String id, Long cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
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
        if (!(object instanceof IdCantidadDto)) {
            return false;
        }
        IdCantidadDto other = (IdCantidadDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
