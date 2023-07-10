package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.albaran.AlbaranEntrega;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface ContratoLineaRO {

    public Integer getId();
    public Integer getNumLinea();
    public String getBooking();
    public ContratoTipoOperacion getTipoOperacion();
    public TipoProducto getTipoProducto();
    public Ubicacion getUbicacion();
    public String getLote();
    public String getConcepto();
    public BigDecimal getImporte();
    public BigDecimal getImporteTotal();
    public Date getFechaEntregaPrevista();
    public Date getFechaEntrega();    
    public Date getFechaDevolucionPrevista();
    public Date getFechaDevolucion();
    public DobleObservacion getObservaciones();
    public TransporteConPrecio getTransporteEntregaConPrecio();
    public TransporteConPrecio getTransporteRecogidaConPrecio();
    public String getNumSerie();
    public Stock getProducto();
    public List<ContratoLineaComplementoRO> getComplementos();
    public BigDecimal getComplementosImporte();
    public List<ContratoLineaGastoAdicionalRO> getGastosAdicionales();
    public BigDecimal getGastosAdicionalesImporte();
    
    public AlbaranEntrega getAlbaranEntrega();
    public AlbaranRecogida getAlbaranRecogida(); 

    public ContratoLineaEstado getEstado();
}
