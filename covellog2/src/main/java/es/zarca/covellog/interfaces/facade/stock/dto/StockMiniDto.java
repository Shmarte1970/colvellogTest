package es.zarca.covellog.interfaces.facade.stock.dto;

/**
 *
 * @author francisco
 */
public class StockMiniDto {
    private Integer id;
    private String numSerie;
    private TipoProductoDto tipoProducto;
    private String lote;
    private FlotaDto flota;
    private EstadoDto estado;
    private CondicionDto condicion;
    private String albaranEntregaFriendlyId;
    private String albaranRecogidaFriendlyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAlbaranEntregaFriendlyId() {
        return albaranEntregaFriendlyId;
    }

    public void setAlbaranEntregaFriendlyId(String albaranEntregaFriendlyId) {
        this.albaranEntregaFriendlyId = albaranEntregaFriendlyId;
    }

    public String getAlbaranRecogidaFriendlyId() {
        return albaranRecogidaFriendlyId;
    }

    public void setAlbaranRecogidaFriendlyId(String albaranRecogidaFriendlyId) {
        this.albaranRecogidaFriendlyId = albaranRecogidaFriendlyId;
    }

}
