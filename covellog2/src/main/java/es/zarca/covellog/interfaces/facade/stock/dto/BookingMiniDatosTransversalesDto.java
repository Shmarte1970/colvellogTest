package es.zarca.covellog.interfaces.facade.stock.dto;

import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranMiniDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoPagoEstadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import java.util.Date;

/**
 *
 * @author francisco
 */
public class BookingMiniDatosTransversalesDto {
    private Date fecha;
    private String booking;
    private TipoProductoDto tipoProducto;
    private String lote;
    private StockMiniDto stock;
    private ReservaDto reserva;
    private AlbaranMiniDto albaran;
    private Integer contratoId;
    private String contratoFriendlyId;    
    private ContratoPagoEstadoDto pagoEstado;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public StockMiniDto getStock() {
        return stock;
    }

    public void setStock(StockMiniDto stock) {
        this.stock = stock;
    }

    public ReservaDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }

    public AlbaranMiniDto getAlbaran() {
        return albaran;
    }

    public void setAlbaran(AlbaranMiniDto albaran) {
        this.albaran = albaran;
    }

    public Integer getContratoId() {
        return contratoId;
    }

    public void setContratoId(Integer contratoId) {
        this.contratoId = contratoId;
    }
    
    public String getContratoFriendlyId() {
        return contratoFriendlyId;
    }

    public void setContratoFriendlyId(String contratoFriendlyId) {
        this.contratoFriendlyId = contratoFriendlyId;
    }

    public ContratoPagoEstadoDto getPagoEstado() {
        return pagoEstado;
    }

    public void setPagoEstado(ContratoPagoEstadoDto pagoEstado) {
        this.pagoEstado = pagoEstado;
    }

}