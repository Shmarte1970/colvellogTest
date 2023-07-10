package es.zarca.covellog.comercial.interfaces.facade.oferta.dto;

import es.zarca.covellog.comercial.interfaces.facade.base.dto.NombreDto;
import es.zarca.covellog.interfaces.facade.base.dto.EstadoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;

/**
 *
 * @author francisco
 */
public class OfertaSmallDto {
    private Integer id;
    private String friendlyId;
    private int clienteId;
    private String clienteNombre;
    private String clienteCif;
    private String fecha;
    private String codigoPedidoCliente;
    private String codigoProveedor;
    private EstadoDto estado;
    private DobleObservacionDto observaciones;
    private String productos;
    
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
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigoPedidoCliente() {
        return codigoPedidoCliente;
    }

    public void setCodigoPedidoCliente(String codigoPedidoCliente) {
        this.codigoPedidoCliente = codigoPedidoCliente;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

}
