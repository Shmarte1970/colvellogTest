package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.contratos.form.ContratoLineaComplementoForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaForm;
import es.zarca.covellog.application.contratos.form.ContratoLineaGastoAdicionalForm;
import es.zarca.covellog.application.contratos.form.TransporteConPrecioForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaComplementoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaGastoAdicionalDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoTipoOperacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockSeleccionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.facade.transporte.TransportesFacade;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaSelector;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.stock.controller.SelectorStockBean;
import es.zarca.covellog.interfaces.web.transportes.TransporteSelectorBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.jpa.jpql.Assert;
/**
 *
 * @author francisco
 */
@Named("contratoLineaModificarBean")
@ViewScoped
public class ContratoLineaModificarBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoLineaModificarBean.class.getName());
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private ContratoFacade facade;
    @Inject
    private SelectorStockBean selectorStockBean;
    @Inject
    private ContratoLineaComplementoBean contratoLineaComplementoBean;
    @Inject
    private ContratoLineaGastoAdicionalBean contratoLineaGastoAdicionalBean;
    
    private Integer contratoId;
    private Integer lineaId;
    private ContratoDto contrato;
    @NotNull
    private String tipoOperacion;
    private List<ContratoTipoOperacionDto> tiposOperacionPosibles;
    @Inject
    private TransporteSelectorBean transporteSelectorBean;
    @NotNull
    private TipoProductoDto tipoProducto;
    private UbicacionDto ubicacion;
    private String lote;
    private String numeroSerie;
    private String stockEstado;
    private Integer stockId;
    @NotNull
    private String concepto;
    @NotNull
    private BigDecimal importe;
    @NotNull
    private Date fechaEntregaPrevista;
    private TransporteDto entregaTransporte;
    private BigDecimal entregaImporte;
    private TransporteDto recogidaTransporte;
    private BigDecimal recogidaImporte;
    @NotNull
    private String obsInternas;
    @NotNull
    private String obsPublicas;
    private List<ContratoLineaComplementoDto> complementos;
    private List<ContratoLineaComplementoDto> complementosSelecteds;
    private List<ContratoLineaGastoAdicionalDto> gastosAdicionales;
    private List<ContratoLineaGastoAdicionalDto> gastosAdicionalesSelecteds;
    private BusquedaSelectorTransporteDto busquedaSelectorTransporte;
    @Inject
    private TransportesFacade transportesFacade;
    public class BusquedaSelectorTransporteDto extends BusquedaSelector<TransporteDto> {  

        @Override
        public Object getItemRowKey(TransporteDto item) {
            if (item == null) {
                return false;
            }
            return item.getId();
        }

        @Override
        public boolean hasRowKey(TransporteDto item, String rowKey) {
            if ( (item == null) || (item.getId() == null) ) {
                return false;
            }

            return (item.getId().toString().equals(rowKey));
        }

        @Override
        public BusquedaFacade<TransporteDto> getFacade() {
            System.err.println("COJONES Almenos pide la facade!!!!!!!!!!!!!!!!");
            return transportesFacade;
        }
    }

    public Integer getContratoId() {
        return contratoId;
    }

    public void setContratoId(Integer contratoId) {
        this.contratoId = contratoId;
    }

    public Integer getLineaId() {
        return lineaId;
    }

    public void setLineaId(Integer lineaId) {
        this.lineaId = lineaId;
    }
    
    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
        //return contrato;
    }
    
    public void setContrato(ContratoDto contrato) {
        //this.contrato = contrato;
        modificarContratoBean.setContrato(contrato);
    }
    
    public void viewAction() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contrato = facade.findById(contratoId);
            prepare(contrato.getLinea(lineaId));
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "No puede modificar una linea de contrato sin especificar el contrato.");
            contratoId = getContrato().getId();
            lineaId = null;
            tipoOperacion = "AL";
            tipoProducto = null;
            ubicacion = null;
            lote = null;            
            numeroSerie = null;
            stockId = null;
            concepto = "";
            importe = BigDecimal.ZERO;
            fechaEntregaPrevista = null;
            entregaTransporte = null;
            entregaImporte = null;
            recogidaTransporte = null;
            recogidaImporte = null;
            obsInternas = "";
            obsPublicas = "";
            complementos = new ArrayList();
            gastosAdicionales = new ArrayList();
            tiposOperacionPosibles = facade.getTiposOperacionPosibles();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public void prepare(ContratoLineaDto linea) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "No puede modificar una linea de contrato sin especificar el contrato.");
            Assert.isNotNull(linea, "No puede modificar una linea de contrato sin especificar la linea de contrato.");
            contratoId = getContrato().getId();
            lineaId = linea.getId();
            tipoOperacion = linea.getTipoOperacion().getId();
            tipoProducto = linea.getTipoProducto();
            ubicacion = linea.getUbicacion();
            lote = linea.getLote();            
            if (linea.getStock() != null) {
                numeroSerie = linea.getStock().getNumSerie();
                stockEstado = linea.getStock().getEstado().getNombre();
                stockId = linea.getStock().getId();
            } else {
                numeroSerie = null;
                stockEstado = "Sin asignar";
                stockId = null;
            }
            concepto = linea.getConcepto();
            importe = linea.getImporte();
            fechaEntregaPrevista = linea.getFechaEntregaPrevista();
            entregaTransporte = linea.getEntregaTransporte();
            entregaImporte = linea.getEntregaImporte();
            recogidaTransporte = linea.getRecogidaTransporte();
            recogidaImporte = linea.getRecogidaImporte();
            if (linea.getObservaciones() != null) {
                obsInternas = linea.getObservaciones().getObsInternas();
                obsPublicas = linea.getObservaciones().getObsPublicas();
            } else {
                obsInternas = "";
                obsPublicas = "";
            }
            complementos = linea.getComplementos();
            gastosAdicionales = linea.getGastosAdicionales();
            tiposOperacionPosibles = facade.getTiposOperacionPosibles();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
        
    public void prepareSelectStock(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorStockBean.setOnSeleccionarUpdate("@(.ModificarLinea-Producto)");
            selectorStockBean.setOnSeleccionarListener((ActionEvent event1) -> {
                StockSeleccionDto seleccion = selectorStockBean.getSelectedStock();
                tipoProducto = seleccion.getTipoProducto();
                ubicacion = seleccion.getUbicacion();
                lote = seleccion.getLote();
                numeroSerie = seleccion.getNumSerie();
                stockId = seleccion.getId();
            });
            selectorStockBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectEntregaTransporte(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
           /* transporteSelectorBean.setOnSeleccionarUpdate("@(.EntregaTransporteSeleccionable)");
            transporteSelectorBean.setOnSeleccionarListener((ActionEvent event1) -> {
                entregaTransporte = transporteSelectorBean.getSelected();
            });
            transporteSelectorBean.setOnCancelarListener((ActionEvent event1) -> {
            });*/
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectRecogidaTransporte(javax.faces.event.ActionEvent event) {
        /*FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            transporteSelectorBean.setOnSeleccionarUpdate("@(.RecogidaTransporteSeleccionable)");
            transporteSelectorBean.setOnSeleccionarListener((ActionEvent event1) -> {
                recogidaTransporte = transporteSelectorBean.getSelected();
            });
            transporteSelectorBean.setOnCancelarListener((ActionEvent event1) -> {
            });            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }*/
    }
    
    private void assertSomeComplementoSelected() {
        if ((complementosSelecteds == null) || (complementosSelecteds.isEmpty())) {
            throw new PresentationException("Seleccione algun complemento.");
        }
    }
    
    private void assertSomeGastoAdicionalSelected() {
        if ((gastosAdicionalesSelecteds == null) || (gastosAdicionalesSelecteds.isEmpty())) {
            throw new PresentationException("Seleccione algun gasto adicional.");
        }
    }
    
    public void assignarConceptoAutomatico() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(tipoProducto, "Asigne primero un tipo de producto.");        
            if ((tipoProducto.getDescripcion() != null) && (!tipoProducto.getDescripcion().isEmpty())) {
                concepto = tipoProducto.getDescripcion();
            } else if ((tipoProducto.getId() != null) && (!tipoProducto.getId().isEmpty())) {
                concepto = tipoProducto.getId();
            } else {
               JsfUtil.showErrorDialog("No se puede obtener la informacion del tipo de producto.");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void complementosEliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeComplementoSelected();
            complementos.removeAll(complementosSelecteds);
            complementosSelecteds = new ArrayList();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void complementosSubir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeComplementoSelected();
            for (ContratoLineaComplementoDto selected : complementosSelecteds) {
                Integer index = complementos.indexOf(selected);
                if (index > 0) {
                    ContratoLineaComplementoDto complemento = complementos.get(index - 1);
                    complementos.set(index - 1, complementos.get(index));
                    complementos.set(index, complemento);
                }
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void complementosBajar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeComplementoSelected();
            for (ContratoLineaComplementoDto selected : complementosSelecteds) {
                Integer index = complementos.indexOf(selected);
                if (index + 1 < complementos.size()) {
                    ContratoLineaComplementoDto complemento = complementos.get(index + 1);
                    complementos.set(index + 1, complementos.get(index));
                    complementos.set(index, complemento);
                }
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void complementosPrepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contratoLineaComplementoBean.prepareNuevo();
            contratoLineaComplementoBean.setOnGuardarListener((event) -> {
                ContratoLineaComplementoDto complemento = new ContratoLineaComplementoDto();
                if (contratoLineaComplementoBean.getComplemento() == null) {
                    JsfUtil.addValidationErrorMessage("ComplementoEditForm:complemento", "El tipo de complemento es obligatorio.");
                    JsfUtil.validationFailed();
                }
                complemento.setComplemento(contratoLineaComplementoBean.getComplemento());
                complemento.setConcepto(contratoLineaComplementoBean.getConcepto());
                complemento.setCantidad(contratoLineaComplementoBean.getCantidad());
                complemento.setImporte(contratoLineaComplementoBean.getImporte());
                complementos.add(complemento);
                complementosSelecteds = new ArrayList();
                complementosSelecteds.add(complemento);
            });
            contratoLineaComplementoBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void complementosPrepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeComplementoSelected();
            contratoLineaComplementoBean.prepareModificar(complementosSelecteds.get(0));
            contratoLineaComplementoBean.setOnGuardarListener( (event) -> {
                ContratoLineaComplementoDto complemento = complementosSelecteds.get(0);
                complemento.setComplemento(contratoLineaComplementoBean.getComplemento());
                complemento.setConcepto(contratoLineaComplementoBean.getConcepto());
                complemento.setCantidad(contratoLineaComplementoBean.getCantidad());
                complemento.setImporte(contratoLineaComplementoBean.getImporte());
            });
            contratoLineaComplementoBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public BigDecimal getComplementosImporteTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (complementos != null) {
            for (ContratoLineaComplementoDto complemento : complementos) {
                total = total.add(complemento.getImporteTotal());
            }
        }
        return total;
    }
    
    public void gastosAdicionalesEliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeGastoAdicionalSelected();
            gastosAdicionales.removeAll(gastosAdicionalesSelecteds);
            gastosAdicionalesSelecteds = new ArrayList();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void gastosAdicionalesPrepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contratoLineaGastoAdicionalBean.prepareNuevo();
            contratoLineaGastoAdicionalBean.setOnGuardarListener( (event) -> {
                ContratoLineaGastoAdicionalDto gastoAdicional = new ContratoLineaGastoAdicionalDto();
                if (contratoLineaGastoAdicionalBean.getGastoAdicional() == null) {
                    JsfUtil.addValidationErrorMessage("GastoAdicionalEditForm:gastoAdicional", "El tipo de gasto adicional es obligatorio.");
                    JsfUtil.validationFailed();
                }
                gastoAdicional.setGastoAdicional(contratoLineaGastoAdicionalBean.getGastoAdicional());
                gastoAdicional.setConcepto(contratoLineaGastoAdicionalBean.getConcepto());
                gastoAdicional.setCantidad(contratoLineaGastoAdicionalBean.getCantidad());
                gastoAdicional.setImporte(contratoLineaGastoAdicionalBean.getImporte());
                gastosAdicionales.add(gastoAdicional);
                gastosAdicionalesSelecteds = new ArrayList();
                gastosAdicionalesSelecteds.add(gastoAdicional);
            });
            contratoLineaGastoAdicionalBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void gastosAdicionalesPrepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeGastoAdicionalSelected();
            contratoLineaGastoAdicionalBean.prepareModificar(gastosAdicionalesSelecteds.get(0));
            contratoLineaGastoAdicionalBean.setOnGuardarListener( (event) -> {
                ContratoLineaGastoAdicionalDto gastoAdicional = gastosAdicionalesSelecteds.get(0);
                gastoAdicional.setGastoAdicional(contratoLineaGastoAdicionalBean.getGastoAdicional());
                gastoAdicional.setConcepto(contratoLineaGastoAdicionalBean.getConcepto());
                gastoAdicional.setCantidad(contratoLineaGastoAdicionalBean.getCantidad());
                gastoAdicional.setImporte(contratoLineaGastoAdicionalBean.getImporte());
            });
            contratoLineaComplementoBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public BigDecimal getGastosAdicionalesImporteTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (gastosAdicionales != null) {
            for (ContratoLineaGastoAdicionalDto gastoAdicional : gastosAdicionales) {
                total = total.add(gastoAdicional.getImporteTotal());
            }
        }
        return total;
    }
        
    public void PrepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeGastoAdicionalSelected();
            contratoLineaGastoAdicionalBean.prepareModificar(gastosAdicionalesSelecteds.get(0));
            contratoLineaGastoAdicionalBean.setOnGuardarListener( (event) -> {
                ContratoLineaGastoAdicionalDto gastoAdicional = gastosAdicionalesSelecteds.get(0);
                gastoAdicional.setGastoAdicional(contratoLineaGastoAdicionalBean.getGastoAdicional());
                gastoAdicional.setConcepto(contratoLineaGastoAdicionalBean.getConcepto());
                gastoAdicional.setCantidad(contratoLineaGastoAdicionalBean.getCantidad());
                gastoAdicional.setImporte(contratoLineaGastoAdicionalBean.getImporte());
            });
            contratoLineaGastoAdicionalBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public void gastosAdicionalesSubir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeGastoAdicionalSelected();
            for (ContratoLineaGastoAdicionalDto selected : gastosAdicionalesSelecteds) {
                Integer index = gastosAdicionales.indexOf(selected);
                if (index > 0) {
                    ContratoLineaGastoAdicionalDto gastoAdicional = gastosAdicionales.get(index - 1);
                    gastosAdicionales.set(index - 1, gastosAdicionales.get(index));
                    gastosAdicionales.set(index, gastoAdicional);
                }
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void gastosAdicionalesBajar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertSomeGastoAdicionalSelected();
            for (ContratoLineaGastoAdicionalDto selected : gastosAdicionalesSelecteds) {
                Integer index = gastosAdicionales.indexOf(selected);
                if (index < gastosAdicionales.size() - 1) {
                    ContratoLineaGastoAdicionalDto gastoAdicional = gastosAdicionales.get(index + 1);
                    gastosAdicionales.set(index + 1, gastosAdicionales.get(index));
                    gastosAdicionales.set(index, gastoAdicional);
                }
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
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
    
    public List<String> loteCompleteText(String filtro) {
        List<String> lotesList = new ArrayList<>();
        List<IdCantidadDto> dtos = facade.getLotesPosibles(filtro);
        for (IdCantidadDto dto : dtos) {
            lotesList.add(dto.getId());
        }
        return lotesList;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getStockEstado() {
        return stockEstado;
    }

    public void setStockEstado(String stockEstado) {
        this.stockEstado = stockEstado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
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
    
    private ContratoLineaForm createContratoLineaForm() {
        Assert.isNotNull(tipoProducto, "El tipo de producto es obligatorio.");
        Assert.isNotNull(ubicacion, "El almacen es obligatorio.");
        ContratoLineaForm form = new ContratoLineaForm();
        form.setTipoOperacion(tipoOperacion);
        form.setTipoProducto(tipoProducto.getId());
        form.setUbicacion(ubicacion.getId());
        form.setLote(lote);        
        //form.setNumeroSerie();
        form.setStock(stockId);
        form.setConcepto(concepto);
        form.setImporte(importe);
        form.setFechaEntregaPrevista(fechaEntregaPrevista);
        form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));        
        form.setComplementos(new ArrayList());
        if (complementos != null) {
            for (ContratoLineaComplementoDto complemento : complementos) {
                ContratoLineaComplementoForm contratoLineaComplementoForm = new ContratoLineaComplementoForm();
                contratoLineaComplementoForm.setComplementoId(complemento.getComplemento().getId());
                contratoLineaComplementoForm.setConcepto(complemento.getConcepto());
                contratoLineaComplementoForm.setCantidad(complemento.getCantidad());
                contratoLineaComplementoForm.setImporte(complemento.getImporte());
                form.getComplementos().add(contratoLineaComplementoForm);
            }
        }
        form.setGastosAdicionales(new ArrayList());
        if (gastosAdicionales != null) {
            for (ContratoLineaGastoAdicionalDto gastoAdicional : gastosAdicionales) {
                ContratoLineaGastoAdicionalForm contratoLineaGastoAdicionalForm = new ContratoLineaGastoAdicionalForm();
                contratoLineaGastoAdicionalForm.setId(gastoAdicional.getId());
                contratoLineaGastoAdicionalForm.setGastoAdicionalId(gastoAdicional.getGastoAdicional().getId());
                contratoLineaGastoAdicionalForm.setConcepto(gastoAdicional.getConcepto());
                contratoLineaGastoAdicionalForm.setCantidad(gastoAdicional.getCantidad());
                contratoLineaGastoAdicionalForm.setImporte(gastoAdicional.getImporte());
                form.getGastosAdicionales().add(contratoLineaGastoAdicionalForm);
                
            }
        }
        if (entregaTransporte != null) {
            form.setEntrega(new TransporteConPrecioForm(entregaTransporte.getId(), entregaImporte));
        } else {
            form.setEntrega(null);
        }
        if (recogidaTransporte != null) {
            form.setRecogida(new TransporteConPrecioForm(recogidaTransporte.getId(), recogidaImporte));
        } else {
            form.setRecogida(null);
        }
        return form;
    }

    @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ContratoLineaForm form = createContratoLineaForm();
            if (lineaId == null) {
                System.err.println("COJONES: EL id es nulll");
                
                lineaId = facade.crearLinea(contratoId, form);
                if (lineaId == null) {
                    System.err.println("COJONES: EL id es nulll otra vez");
                }else{
                    System.err.println("COJONES: EL id es "+ lineaId.toString());
                }
                setContrato(null);
               // if (getContrato().getLineas().size() > 0) {
                    JsfUtil.addSuccessMessage("Linea creada correctamente.");
               //     lineaId = getContrato().getLineas().get(getContrato().getLineas().size() - 1).getId();
              //  } else {
              //      JsfUtil.showErrorDialog("Error al crear la linea de contrato.");
              //  }
            } else {
                System.err.println("COJONES: EL id2 es "+ lineaId.toString());
                /*for (ContratoLineaGastoAdicionalForm gastoAdicional : form.getGastosAdicionales()) {
                    System.err.println("COJONES ENVIO FORM: " + gastoAdicional.getConcepto());
                }*/
                setContrato(facade.modificarLinea(contratoId, lineaId, form));
                /*System.err.println("COJONES CARGAR");
                for (ContratoLineaGastoAdicionalDto gastoAdicional : getContrato().getLinea(lineaId).getGastosAdicionales()) {
                    System.err.println("COJONES CARGAR: " + gastoAdicional.getConcepto());
                }
                System.err.println("COJONES CARGAR2");*/
                //setContrato(facade.findById(contratoId));
                /*if (getContrato().getLinea(lineaId).getGastosAdicionales().isEmpty()) {
                    System.err.println("COJONES CARGAR2 VACIA");
                }
                for (ContratoLineaGastoAdicionalDto gastoAdicional : getContrato().getLinea(lineaId).getGastosAdicionales()) {
                    System.err.println("COJONES CARGAR2: " + gastoAdicional.getConcepto());
                }*/
                JsfUtil.addSuccessMessage("Linea modificada correctamente.");
            }
            prepare(getContrato().getLinea(lineaId));
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        if (tipoOperacion.equals("VE")) {
            recogidaTransporte = null;
            recogidaImporte = null;
        }
        this.tipoOperacion = tipoOperacion;
    }

    public List<ContratoTipoOperacionDto> getTiposOperacionPosibles() {
        return tiposOperacionPosibles;
    }

    public void setTiposOperacionPosibles(List<ContratoTipoOperacionDto> tiposOperacionPosibles) {
        this.tiposOperacionPosibles = tiposOperacionPosibles;
    }

    public TransporteDto getEntregaTransporte() {
        return entregaTransporte;
    }

    public void setEntregaTransporte(TransporteDto entregaTransporte) {
        this.entregaTransporte = entregaTransporte;
    }

    public String getObsInternas() {
        return obsInternas;
    }

    public void setObsInternas(String obsInternas) {
        this.obsInternas = obsInternas;
    }

    public String getObsPublicas() {
        return obsPublicas;
    }

    public void setObsPublicas(String obsPublicas) {
        this.obsPublicas = obsPublicas;
    }

    public BigDecimal getEntregaImporte() {
        return entregaImporte;
    }

    public void setEntregaImporte(BigDecimal entregaImporte) {
        this.entregaImporte = entregaImporte;
    }

    public BusquedaSelectorTransporteDto getBusquedaSelectorTransporte() {
        if (busquedaSelectorTransporte == null) {
            busquedaSelectorTransporte = new BusquedaSelectorTransporteDto();
        }
        return busquedaSelectorTransporte;
    }

    public void setBusquedaSelectorTransporte(BusquedaSelectorTransporteDto busquedaSelectorTransporte) {
        this.busquedaSelectorTransporte = busquedaSelectorTransporte;
    }
    
    public TransporteDto getRecogidaTransporte() {
        return recogidaTransporte;
    }

    public void setRecogidaTransporte(TransporteDto recogidaTransporte) {
        this.recogidaTransporte = recogidaTransporte;
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

    public void setComplementos(List<ContratoLineaComplementoDto> complementos) {
        this.complementos = complementos;
    }

    public void testAction() {
        LOGGER.log(Level.INFO, "[");
        if (complementosSelecteds != null) {
           for (ContratoLineaComplementoDto complementosSelected : complementosSelecteds) {
                LOGGER.log(Level.INFO, "    " + complementosSelected.getConcepto());
            } 
        }
        LOGGER.log(Level.INFO, "]");
    }
    
    public List<ContratoLineaComplementoDto> getComplementosSelecteds() {
        return complementosSelecteds;
    }

    public void setComplementosSelecteds(List<ContratoLineaComplementoDto> complementosSelecteds) {
        this.complementosSelecteds = complementosSelecteds;
        LOGGER.log(Level.INFO, "{");
        for (ContratoLineaComplementoDto complementosSelected : complementosSelecteds) {
            LOGGER.log(Level.INFO, "    " + complementosSelected.getConcepto());
        }
        LOGGER.log(Level.INFO, "}");
        
    }
    
    /*public ContratoLineaComplementoDto getComplementoSelected() {
        if (complementosSelecteds == null) {
            return null;
        }
        return complementosSelecteds.get(0);
    }*/

    public List<ContratoLineaGastoAdicionalDto> getGastosAdicionales() {
        return gastosAdicionales;
    }

    public void setGastosAdicionales(List<ContratoLineaGastoAdicionalDto> gastosAdicionales) {
        this.gastosAdicionales = gastosAdicionales;
    }

    public List<ContratoLineaGastoAdicionalDto> getGastosAdicionalesSelecteds() {
        return gastosAdicionalesSelecteds;
    }

    public void setGastosAdicionalesSelecteds(List<ContratoLineaGastoAdicionalDto> gastosAdicionalesSelecteds) {
        this.gastosAdicionalesSelecteds = gastosAdicionalesSelecteds;
    }

    @FacesConverter(value="complementoDtoConverter")
    public static class ComplementoDtoConverter implements Converter {
        
        private ContratoLineaModificarBean getController(FacesContext facesContext) {
            return (ContratoLineaModificarBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "contratoLineaModificarBean");
        }

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            return getController(facesContext).getComplementos().get(Integer.parseInt(value));            
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ContratoLineaComplementoDto) {
                //ContratoLineaComplementoDto o = (ContratoLineaComplementoDto) object;
                return String.valueOf(getController(facesContext).getComplementos().indexOf(object));
            } else {
                return null;
            }
        }
    }
    
    @FacesConverter(value="gastoAdicionalDtoConverter")
    public static class GastoAdicionalDtoConverter implements Converter {
        
        private ContratoLineaModificarBean getController(FacesContext facesContext) {
            return (ContratoLineaModificarBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "contratoLineaModificarBean");
        }

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            return getController(facesContext).getGastosAdicionales ().get(Integer.parseInt(value));            
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ContratoLineaGastoAdicionalDto) {
                return String.valueOf(getController(facesContext).getGastosAdicionales().indexOf(object));
            } else {
                return null;
            }
        }
    }
}