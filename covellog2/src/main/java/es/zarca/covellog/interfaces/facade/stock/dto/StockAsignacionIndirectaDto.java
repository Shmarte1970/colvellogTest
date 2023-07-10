package es.zarca.covellog.interfaces.facade.stock.dto;

import java.io.Serializable;

public class StockAsignacionIndirectaDto implements Serializable {

    private String booking;
    private TipoProductoDto tipoProducto;
    private String lote;
    private String numSerie;
    
    public StockAsignacionIndirectaDto() {
    }

    public StockAsignacionIndirectaDto(String booking, TipoProductoDto tipoProducto, String lote, String numSerie) {
        this.booking = booking;
        this.tipoProducto = tipoProducto;
        this.lote = lote;
        this.numSerie = numSerie;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }
    
}
