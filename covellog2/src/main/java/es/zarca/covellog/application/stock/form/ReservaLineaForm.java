package es.zarca.covellog.application.stock.form;

/**
 *
 * @author francisco
 */
public class ReservaLineaForm {
    
    private String booking;
    private Integer stockId;
    private String tipoProductoId;
    private String lote;
    private String numSerie;

    public ReservaLineaForm(String booking, Integer stockId, String tipoProductoId, String lote, String numSerie) {
        this.booking = booking;
        this.stockId = stockId;
        this.tipoProductoId = tipoProductoId;
        this.lote = lote;
        this.numSerie = numSerie;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }
    
    public String getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(String tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
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
