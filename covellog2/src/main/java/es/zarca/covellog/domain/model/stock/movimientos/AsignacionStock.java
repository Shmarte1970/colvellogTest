package es.zarca.covellog.domain.model.stock.movimientos;

import es.zarca.covellog.domain.model.stock.Stock;

/**
 *
 * @author francisco
 */
public class AsignacionStock {
    
    private final String booking;
    private final Stock stock;

    public AsignacionStock(String booking, Stock stock) {
        this.booking = booking;
        this.stock = stock;
    }
    
    public String getBooking() {
        return booking;
    }

    public Stock getStock() {
        return stock;
    }

}