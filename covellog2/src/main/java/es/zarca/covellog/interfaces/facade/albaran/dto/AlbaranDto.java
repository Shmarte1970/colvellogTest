package es.zarca.covellog.interfaces.facade.albaran.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public class AlbaranDto {
    
    private Integer id;
    private String codigoAlbaran;
    private String booking;
    private Date fecha;
    private AlbaranTipoDto tipo;
    private EmpresaMiniDto transportista;
    private InfoTransporteDto transporte;
    private UbicacionDto origen;
    private UbicacionDto destino;
    private String textoAviso;
    private DobleObservacionDto observaciones;
    private Boolean canReabrir;
    private Boolean canActivar;
    private Boolean canAnular;
    private Boolean canModificar;
    private Boolean canFinalizar;
    private Boolean canCrearReserva;
    private String estado;
    private Date estadoFecha;
    private Integer clienteId;
    private String clienteFriendlyId;
    private String clienteCif;
    private String clienteNombre;
    private Integer contratoId;
    private String contratoFriendlyId;
    private String contratoCodigoPedidoCliente;
    private List<AlbaranLineaDto> lineas;

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

    public EmpresaMiniDto getTransportista() {
        return transportista;
    }

    public void setTransportista(EmpresaMiniDto transportista) {
        this.transportista = transportista;
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

    public String getTextoAviso() {
        return textoAviso;
    }

    public void setTextoAviso(String textoAviso) {
        this.textoAviso = textoAviso;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getCanReabrir() {
        return canReabrir;
    }

    public void setCanReabrir(Boolean canReabrir) {
        this.canReabrir = canReabrir;
    }

    public Boolean getCanActivar() {
        return canActivar;
    }

    public void setCanActivar(Boolean canActivar) {
        this.canActivar = canActivar;
    }

    public Boolean getCanAnular() {
        return canAnular;
    }

    public void setCanAnular(Boolean canAnular) {
        this.canAnular = canAnular;
    }

    public Boolean getCanModificar() {
        return canModificar;
    }

    public void setCanModificar(Boolean canModificar) {
        this.canModificar = canModificar;
    }

    public Boolean getCanFinalizar() {
        return canFinalizar;
    }

    public void setCanFinalizar(Boolean canFinalizar) {
        this.canFinalizar = canFinalizar;
    }

    public Boolean getCanCrearReserva() {
        return canCrearReserva;
    }

    public void setCanCrearReserva(Boolean canCrearReserva) {
        this.canCrearReserva = canCrearReserva;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public void setEstadoFecha(Date estadoFecha) {
        this.estadoFecha = estadoFecha;
    }
    
    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteFriendlyId() {
        return clienteFriendlyId;
    }

    public void setClienteFriendlyId(String clienteFriendlyId) {
        this.clienteFriendlyId = clienteFriendlyId;
    }

    public String getClienteCif() {
        return clienteCif;
    }

    public void setClienteCif(String clienteCif) {
        this.clienteCif = clienteCif;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
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

    public String getContratoCodigoPedidoCliente() {
        return contratoCodigoPedidoCliente;
    }

    public void setContratoCodigoPedidoCliente(String contratoCodigoPedidoCliente) {
        this.contratoCodigoPedidoCliente = contratoCodigoPedidoCliente;
    }

    public List<AlbaranLineaDto> getLineas() {
        return lineas;
    }

    public void setLineas(List<AlbaranLineaDto> lineas) {
        this.lineas = lineas;
    }
   
}