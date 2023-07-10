package es.zarca.covellog.interfaces.facade.stock.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaMiniDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MovimientoDto implements Serializable {

    private Integer id;
    private String friendlyId;
    private MovimientoTipoDto tipo;
    private ReservaMiniDto reserva;
    private String booking;
    private MovimientoEstadoDto estado;
    private Date fecha;
    private UbicacionDto ubicacion;
    private EmpresaMiniDto cliente;
    private Integer transportistaId;
    private String transportistaNombre;
    private String chofer;
    private String matricula;
    private String observaciones;
    private List<MovimientoLineaDto> lineas;
    
    public MovimientoDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        this.friendlyId = friendlyId;
    }
    
    public MovimientoTipoDto getTipo() {
        return tipo;
    }

    public void setTipo(MovimientoTipoDto tipo) {
        this.tipo = tipo;
    }

    public ReservaMiniDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaMiniDto reserva) {
        this.reserva = reserva;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public MovimientoEstadoDto getEstado() {
        return estado;
    }

    public void setEstado(MovimientoEstadoDto estado) {
        this.estado = estado;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UbicacionDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDto ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public EmpresaMiniDto getCliente() {
        return cliente;
    }

    public void setCliente(EmpresaMiniDto cliente) {
        this.cliente = cliente;
    }

    public Integer getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(Integer transportistaId) {
        this.transportistaId = transportistaId;
    }
    
    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<MovimientoLineaDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<MovimientoLineaDto> lineas) {
        this.lineas = lineas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientoDto)) {
            return false;
        }
        MovimientoDto other = (MovimientoDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
