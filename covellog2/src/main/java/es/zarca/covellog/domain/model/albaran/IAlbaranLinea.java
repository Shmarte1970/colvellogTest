package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.stock.reservas.Reserva;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface IAlbaranLinea  {

    public Integer getId();
    public String getBooking();
    public String getNumSerie();
    public TipoProducto getTipoProducto();
    public String getLote();
    public Stock getStock();
    public String getDescripcion();
    public List<String> getCheckList();
    public Date getFechaEntrega();
    public Date getFechaSalida();
    public Date getFechaLlegada();
    public Reserva getReserva();
    public Stock getAsignacionStock();
}
