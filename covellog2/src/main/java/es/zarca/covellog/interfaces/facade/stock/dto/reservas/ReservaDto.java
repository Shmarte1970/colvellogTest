package es.zarca.covellog.interfaces.facade.stock.dto.reservas;

import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranMiniDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReservaDto implements Serializable {

    private Integer id;
    private String friendlyId;
    private ReservaTipoDto tipo;
    private ReservaEstadoDto estado;
    private Date estadoFecha;
    private Date fecha;
    private UbicacionDto ubicacion;
    private String booking;
    private EmpresaMiniDto cliente;
    private Integer transportistaId;
    private String transportistaNombre;
    private String chofer;
    private String matricula;
    private String observaciones;
    private List<ReservaLineaDto> lineas;
    private boolean canActivar;
    private boolean canReabrir;
    private boolean canAnular;
    private boolean canModificar;
    private AlbaranMiniDto albaran;
    
    public ReservaDto() {
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

    public ReservaTipoDto getTipo() {
        return tipo;
    }

    public void setTipo(ReservaTipoDto tipo) {
        this.tipo = tipo;
    }

    public ReservaEstadoDto getEstado() {
        return estado;
    }

    public void setEstado(ReservaEstadoDto estado) {
        this.estado = estado;
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public void setEstadoFecha(Date estadoFecha) {
        this.estadoFecha = estadoFecha;
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

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
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

    public List<ReservaLineaDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<ReservaLineaDto> lineas) {
        this.lineas = lineas;
    }

    public boolean isCanActivar() {
        return canActivar;
    }

    public void setCanActivar(boolean canActivar) {
        this.canActivar = canActivar;
    }

    public boolean isCanReabrir() {
        return canReabrir;
    }

    public void setCanReabrir(boolean canReabrir) {
        this.canReabrir = canReabrir;
    }

    public boolean isCanAnular() {
        return canAnular;
    }

    public void setCanAnular(boolean canAnular) {
        this.canAnular = canAnular;
    }

    public boolean isCanModificar() {
        return canModificar;
    }

    public void setCanModificar(boolean canModificar) {
        this.canModificar = canModificar;
    }

    public AlbaranMiniDto getAlbaran() {
        return albaran;
    }

    public void setAlbaran(AlbaranMiniDto albaran) {
        this.albaran = albaran;
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
        if (!(object instanceof ReservaDto)) {
            return false;
        }
        ReservaDto other = (ReservaDto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
