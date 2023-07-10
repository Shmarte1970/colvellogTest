package es.zarca.covellog.interfaces.facade.stock.dto;

import java.io.Serializable;

public class MovimientoLineaDto implements Serializable {

    private Integer id;
    private String booking;
    private StockMiniDto stock;
    
    public MovimientoLineaDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public StockMiniDto getStock() {
        return stock;
    }

    public void setStock(StockMiniDto stock) {
        this.stock = stock;
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
        if (!(object instanceof MovimientoLineaDto)) {
            return false;
        }
        MovimientoLineaDto other = (MovimientoLineaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
