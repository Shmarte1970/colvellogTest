package es.zarca.covellog.application.stock.form;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author francisco
 */
public class ReservaLineaDomainForm {
    private String booking;
    private TipoProducto tipoProducto;
    private String lote;
    private String numSerie;
    private Stock stock;

    public ReservaLineaDomainForm() {
    }

    public ReservaLineaDomainForm(String booking, TipoProducto tipoProducto, String lote, String numSerie, Stock stock) {
        this.booking = booking;
        this.tipoProducto = tipoProducto;
        this.lote = lote;
        this.numSerie = numSerie;
        this.stock = stock;
    }
    
    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    

}
