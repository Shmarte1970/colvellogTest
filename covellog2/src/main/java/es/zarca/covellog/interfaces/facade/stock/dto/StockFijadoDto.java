package es.zarca.covellog.interfaces.facade.stock.dto;

import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;


/**
 *
 * @author francisco
 */
public class StockFijadoDto {
    private UbicacionDto ubicacion;
    private TipoProductoDto tipoProducto;
    private String lote;
    private String numSerie;
    private String flota;

    public StockFijadoDto(String numSerie, UbicacionDto ubicacion, TipoProductoDto tipoProducto, String lote, String flota) {
        this.ubicacion = ubicacion;
        this.tipoProducto = tipoProducto;
        this.lote = lote;
        this.numSerie = numSerie;
        this.flota = flota;
    }
    
    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public UbicacionDto getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionDto ubicacion) {
        this.ubicacion = ubicacion;
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

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getFlota() {
        return flota;
    }

    public void setFlota(String flota) {
        this.flota = flota;
    }
    
}
