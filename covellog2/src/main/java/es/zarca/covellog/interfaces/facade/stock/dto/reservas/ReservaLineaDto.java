package es.zarca.covellog.interfaces.facade.stock.dto.reservas;

import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockFijadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReservaLineaDto implements Serializable {

    private Integer id;
    private String booking;
    private StockFijadoDto stockFijado;
    private StockMiniDto stock;
    private TipoProductoDto tipoProducto;
    private String lote;
    private String numSerie;
    private MovimientoDto movimiento;
    private String numSerieAsignado;
    private Date fechaEntrega;
    private List<String> checkList;
    
    public ReservaLineaDto() {
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

    public StockMiniDto getStock() {
        return stock;
    }

    public void setStock(StockMiniDto stock) {
        this.stock = stock;
    }

    public StockFijadoDto getStockFijado() {
        return stockFijado;
    }

    public void setStockFijado(StockFijadoDto stockFijado) {
        this.stockFijado = stockFijado;
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

    public MovimientoDto getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(MovimientoDto movimiento) {
        this.movimiento = movimiento;
    }

    public String getNumSerieAsignado() {
        return numSerieAsignado;
    }

    public void setNumSerieAsignado(String numSerieAsignado) {
        this.numSerieAsignado = numSerieAsignado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<String> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }
 
}