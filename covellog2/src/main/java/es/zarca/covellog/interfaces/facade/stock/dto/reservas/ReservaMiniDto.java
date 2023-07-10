package es.zarca.covellog.interfaces.facade.stock.dto.reservas;

import java.io.Serializable;

public class ReservaMiniDto implements Serializable {

    private Integer id;
    private String friendlyId;
    private ReservaTipoDto tipo;
    private String booking;
    private ReservaEstadoDto estado;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        this.friendlyId = friendlyId;
    }
    
    public ReservaTipoDto getTipo() {
        return tipo;
    }

    public void setTipo(ReservaTipoDto tipo) {
        this.tipo = tipo;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public ReservaEstadoDto getEstado() {
        return estado;
    }

    public void setEstado(ReservaEstadoDto estado) {
        this.estado = estado;
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
        if (!(object instanceof ReservaMiniDto)) {
            return false;
        }
        ReservaMiniDto other = (ReservaMiniDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
