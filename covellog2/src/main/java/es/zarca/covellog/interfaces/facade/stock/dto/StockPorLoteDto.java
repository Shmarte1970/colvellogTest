package es.zarca.covellog.interfaces.facade.stock.dto;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.UbicacionEmpresaDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;

/**
 *
 * @author francisco
 */
public class StockPorLoteDto {

    private String id;
    private TipoProductoDto tipoProducto;
    private String propietario;
    private UbicacionDto ubicacion;
    private String lote;
    private FlotaDto flota;
    private EstadoDto estado;
    private CondicionDto condicion;
    private Integer cantidad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public UbicacionDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDto ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public FlotaDto getFlota() {
        return flota;
    }

    public void setFlota(FlotaDto flota) {
        this.flota = flota;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    public CondicionDto getCondicion() {
        return condicion;
    }

    public void setCondicion(CondicionDto condicion) {
        this.condicion = condicion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
