package es.zarca.covellog.interfaces.facade.albaran.dto;

import es.zarca.covellog.interfaces.facade.base.dto.EstadoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class AlbaranSmallDto {
    private Integer id;
    private String codigoAlbaran;
    private String booking;
    private Date fecha;
    private AlbaranTipoDto tipo;
    private int clienteId;
    private String clienteNombre;
    private String clienteCif;
    private InfoTransporteDto transporte;
    private UbicacionDto origen;
    private UbicacionDto destino;
    private DobleObservacionDto observaciones;
    private EstadoDto estado;
    private Date estadoFecha;
    private String productos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAlbaran() {
        return codigoAlbaran;
    }

    public void setCodigoAlbaran(String codigoAlbaran) {
        this.codigoAlbaran = codigoAlbaran;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public AlbaranTipoDto getTipo() {
        return tipo;
    }

    public void setTipo(AlbaranTipoDto tipo) {
        this.tipo = tipo;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteCif() {
        return clienteCif;
    }

    public void setClienteCif(String clienteCif) {
        this.clienteCif = clienteCif;
    }

    public InfoTransporteDto getTransporte() {
        return transporte;
    }

    public void setTransporte(InfoTransporteDto transporte) {
        this.transporte = transporte;
    }

    public UbicacionDto getOrigen() {
        return origen;
    }

    public void setOrigen(UbicacionDto origen) {
        this.origen = origen;
    }

    public UbicacionDto getDestino() {
        return destino;
    }

    public void setDestino(UbicacionDto destino) {
        this.destino = destino;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public void setEstadoFecha(Date estadoFecha) {
        this.estadoFecha = estadoFecha;
    }
    
    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }
    
       
}