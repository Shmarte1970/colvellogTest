package es.zarca.covellog.interfaces.facade.albaran.dto;

import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaMiniDto;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author francisco
 */
public class AlbaranLineaDto {
    private Integer id;
    private StockDto stock;
    private StockDto asignacionStock;
    private String booking;
    private String numSerie;
    private TipoProductoDto tipoProducto;
    private String lote;
    private String descripcion;
    private Date fechaEntrega;
    private Date fechaSalida;
    private Date fechaLlegada;
    private ReservaMiniDto reserva;
    private List<String> checkList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }

    public StockDto getAsignacionStock() {
        return asignacionStock;
    }

    public void setAsignacionStock(StockDto asignacionStock) {
        this.asignacionStock = asignacionStock;
    }
    
    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
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
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public ReservaMiniDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaMiniDto reserva) {
        this.reserva = reserva;
    }

    public List<String> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<String> checkList) {
        this.checkList = checkList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AlbaranLineaDto other = (AlbaranLineaDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
 
    
}