package es.zarca.covellog.application.contratos.form;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco
 */
public class ContratoLineaForm {    
    private Integer id;
    @NotNull
    private String tipoOperacion;
    private String tipoProducto;
    private Integer ubicacion;
    private String lote;
    private String concepto;
    private Integer stock;
    private BigDecimal importe;
    @NotNull
    private Date fechaEntregaPrevista;
    private DobleObservacionForm observaciones;
    private TransporteConPrecioForm entrega;
    private TransporteConPrecioForm recogida;
    private List<ContratoLineaComplementoForm> complementos;
    private List<ContratoLineaGastoAdicionalForm> gastosAdicionales;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Date getFechaEntregaPrevista() {
        return fechaEntregaPrevista;
    }

    public void setFechaEntregaPrevista(Date fechaEntregaPrevista) {
        this.fechaEntregaPrevista = fechaEntregaPrevista;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }

    public TransporteConPrecioForm getEntrega() {
        return entrega;
    }

    public void setEntrega(TransporteConPrecioForm entrega) {
        this.entrega = entrega;
    }

    public TransporteConPrecioForm getRecogida() {
        return recogida;
    }

    public void setRecogida(TransporteConPrecioForm recogida) {
        this.recogida = recogida;
    }

    public List<ContratoLineaComplementoForm> getComplementos() {
        return complementos;
    }

    public void setComplementos(List<ContratoLineaComplementoForm> complementos) {
        this.complementos = complementos;
    }

    public List<ContratoLineaGastoAdicionalForm> getGastosAdicionales() {
        return gastosAdicionales;
    }

    public void setGastosAdicionales(List<ContratoLineaGastoAdicionalForm> gastosAdicionales) {
        this.gastosAdicionales = gastosAdicionales;
    }
    
}