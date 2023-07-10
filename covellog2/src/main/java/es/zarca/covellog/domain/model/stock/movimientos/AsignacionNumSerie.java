package es.zarca.covellog.domain.model.stock.movimientos;

/**
 *
 * @author francisco
 */
public class AsignacionNumSerie {
    
    private final String booking;
    private final String numSerie;

    public AsignacionNumSerie(String booking, String numSerie) {
        this.booking = booking;
        this.numSerie = numSerie;
    }
    
    public String getBooking() {
        return booking;
    }

    public String getNumSerie() {
        return numSerie;
    }

}