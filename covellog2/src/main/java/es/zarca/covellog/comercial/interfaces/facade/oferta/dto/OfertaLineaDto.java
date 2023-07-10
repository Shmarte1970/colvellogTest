package es.zarca.covellog.comercial.interfaces.facade.oferta.dto;

import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranMiniDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.base.dto.EstadoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaComplementoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaGastoAdicionalDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoTipoOperacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author francisco
 */
public class OfertaLineaDto {
    private Integer id;
    private ContratoTipoOperacionDto tipoOperacion;
    private UbicacionDto ubicacion;
    private TipoProductoDto tipoProducto;
    private String lote;
    private String numSerie;
    private String concepto;
    private StockDto stock;
    private BigDecimal importe;
    private BigDecimal importeTotal;
    private Date fechaEntregaPrevista;
    private Date fechaEntrega;
    private AlbaranMiniDto entregaAlbaran;
    private Date fechaDevolucionPrevista;
    private Date fechaDevolucion;
    private AlbaranMiniDto recogidaAlbaran;
    private DobleObservacionDto observaciones;
    private TransporteDto entregaTransporte;
    private BigDecimal entregaImporte;
    private TransporteDto recogidaTransporte;
    private BigDecimal recogidaImporte;
    private List<ContratoLineaComplementoDto> complementos;
    private BigDecimal complementosImporte;
    private List<ContratoLineaGastoAdicionalDto> gastosAdicionales;
    private BigDecimal gastosAdicionalesImporte;
    private EstadoDto estado;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContratoTipoOperacionDto getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(ContratoTipoOperacionDto tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
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

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
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

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public AlbaranMiniDto getEntregaAlbaran() {
        return entregaAlbaran;
    }

    public void setEntregaAlbaran(AlbaranMiniDto entregaAlbaran) {
        this.entregaAlbaran = entregaAlbaran;
    }

    
    public Date getFechaDevolucionPrevista() {
        return fechaDevolucionPrevista;
    }

    public void setFechaDevolucionPrevista(Date fechaDevolucionPrevista) {
        this.fechaDevolucionPrevista = fechaDevolucionPrevista;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public void setFechaEntregaPrevista(Date fechaEntregaPrevista) {
        this.fechaEntregaPrevista = fechaEntregaPrevista;
    }

    public AlbaranMiniDto getRecogidaAlbaran() {
        return recogidaAlbaran;
    }

    public void setRecogidaAlbaran(AlbaranMiniDto recogidaAlbaran) {
        this.recogidaAlbaran = recogidaAlbaran;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public BigDecimal getEntregaImporte() {
        return entregaImporte;
    }

    public void setEntregaImporte(BigDecimal entregaImporte) {
        this.entregaImporte = entregaImporte;
    }

    public BigDecimal getRecogidaImporte() {
        return recogidaImporte;
    }

    public void setRecogidaImporte(BigDecimal recogidaImporte) {
        this.recogidaImporte = recogidaImporte;
    }

    public List<ContratoLineaComplementoDto> getComplementos() {
        return complementos;
    }

    public BigDecimal getComplementosImporte() {
        return complementosImporte;
    }

    public void setComplementosImporte(BigDecimal complementosImporte) {
        this.complementosImporte = complementosImporte;
    }
    
    public void setComplementos(List<ContratoLineaComplementoDto> complementos) {
        this.complementos = complementos;
    }

    public List<ContratoLineaGastoAdicionalDto> getGastosAdicionales() {
        return gastosAdicionales;
    }
    
    public void setGastosAdicionales(List<ContratoLineaGastoAdicionalDto> gastosAdicionales) {
        this.gastosAdicionales = gastosAdicionales;
    }

    public BigDecimal getGastosAdicionalesImporte() {
        return gastosAdicionalesImporte;
    }

    public void setGastosAdicionalesImporte(BigDecimal gastosAdicionalesImporte) {
        this.gastosAdicionalesImporte = gastosAdicionalesImporte;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    public TransporteDto getEntregaTransporte() {
        return entregaTransporte;
    }

    public void setEntregaTransporte(TransporteDto entregaTransporte) {
        this.entregaTransporte = entregaTransporte;
    }

    public TransporteDto getRecogidaTransporte() {
        return recogidaTransporte;
    }

    public void setRecogidaTransporte(TransporteDto recogidaTransporte) {
        this.recogidaTransporte = recogidaTransporte;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OfertaLineaDto other = (OfertaLineaDto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}