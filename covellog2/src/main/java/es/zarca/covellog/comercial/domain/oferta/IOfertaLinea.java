package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.comercial.domain.base.GastoTipoProducto;
import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import es.zarca.covellog.domain.model.contrato.TransporteConPrecio;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */

public interface IOfertaLinea {    
    Integer getId();
    Integer getNumLinea();
    String getBooking();
    ContratoTipoOperacion getTipoOperacion();
    TipoProducto getTipoProducto();
    Ubicacion getUbicacion();
    String getLote();
    String getConcepto();
    BigDecimal getImporte();
    BigDecimal getImporteTotal();
    BigDecimal getImporteTotalBase();
    BigDecimal getImporteTransportes();
    Date getFechaEntregaPrevista();
    DobleObservacion getObservaciones();
    TransporteConPrecio getTransporteEntregaConPrecio();
    TransporteConPrecio getTransporteRecogidaConPrecio();
    String getNumSerie();
    Stock getProducto();
    List<OfertaLineaComplemento> getComplementos();
    BigDecimal getComplementosImporte();
    List<GastoTipoProducto> getGastosAdicionales();
    
}
