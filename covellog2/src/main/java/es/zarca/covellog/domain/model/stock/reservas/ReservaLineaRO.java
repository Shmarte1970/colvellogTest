package es.zarca.covellog.domain.model.stock.reservas;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.movimientos.Movimiento;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface ReservaLineaRO {

    public Integer getId();
    public String getBooking();
    public Stock getStock();
    public TipoProducto getTipoProducto();
    public String getLote();
    public String getNumSerie();
    public Reserva getReserva();
    public Movimiento getMovimiento();
    public String getNumSerieAsignado();
    public List<String> getCheckList();

    public Date getFechaEntrega();

}
