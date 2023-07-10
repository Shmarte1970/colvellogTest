package es.zarca.covellog.interfaces.facade.stock.dto;

import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.io.Serializable;

public class StockAsignacionDto implements Serializable {

    private String booking;
    private StockMiniDto stock;
    private TipoProductoDto tipoProducto;
    private UbicacionDto ubicacion;
    private String lote;
    private String numSerie;
    
    public StockAsignacionDto() {
    }

    public StockAsignacionDto(String booking, StockMiniDto stock, TipoProductoDto tipoProducto, UbicacionDto ubicacion, String lote, String numSerie) {
        this.booking = booking;
        this.stock = stock;
        this.tipoProducto = tipoProducto;
        this.ubicacion = ubicacion;
        this.lote = lote;
        this.numSerie = numSerie;
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

    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public UbicacionDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDto ubicacion) {
        this.ubicacion = ubicacion;
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
