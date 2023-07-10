package es.zarca.covellog.interfaces.web.movimiento;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranLineaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockAsignacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaMiniDto;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.stock.controller.SelectorStockBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.primefaces.PrimeFaces;
/**
 *
 * @author francisco
 */
@Named("reservarBookingAlbaranBean")
@ViewScoped
public class ReservarBookingAlbaranBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ReservarBookingAlbaranBean.class.getName());
    @Inject
    private AlbaranFacade facade;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;    
    private Integer transportistaId;
    @NotNull
    private Date fecha;
    @NotNull
    @Size(min = 1, max = 80)
    private String transportistaNombre;
    @Size(min = 1, max = 80)
    private String chofer;
    @Size(max = 20)
    private String matricula;
    private String observaciones;
    private AlbaranDto albaran;
    private Integer albaranId;
    @Inject
    private SelectorStockBean selectorStockBean;
    private List<StockAsignacionDto> asignaciones;
    private StockAsignacionDto selected;
    private Boolean loaded;
    
    public ReservarBookingAlbaranBean() {
    }

    public Boolean getLoaded() {
        return albaran != null;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Integer getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(Integer transportistaId) {
        this.transportistaId = transportistaId;
    }

    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void prepare() {
        fecha = new Date();
        transportistaId = null;
        transportistaNombre = "";
        chofer = "";
        matricula = "";
        observaciones = "";
    }
    
    public void prepareSelectTransportista(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarUpdate("@(.ReservaEditPanel)");
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                  transportistaId = selectorEmpresaController.getSelected().getId();
                  transportistaNombre = selectorEmpresaController.getSelected().getNombre();
            });
            selectorEmpresaController.setOnCancelarListener((ActionEvent event1) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public AlbaranDto getAlbaran() {
        ArgumentValidator.isNotNull(albaranId, "Debe especificar el id de albaran");
        if (albaran == null) {
            albaran = facade.findById(albaranId);
        }
        return albaran;
    }

    public void setAlbaran(AlbaranDto albaran) {
        this.albaran = albaran;
    }

    private AlbaranLineaDto getLineaAlbaranByBooking(String booking) {
        for (AlbaranLineaDto albaranLinea : getAlbaran().getLineas()) {
            System.err.println("*************************************************COJONES " +  albaranLinea.getBooking() + " != " + booking);
            if (booking.equals(albaranLinea.getBooking())) {
                return albaranLinea;
            }
        }
        return null;
    }
    
    void prepare(Integer albaranId, String booking) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            this.albaranId = albaranId;
            albaran = null;
            asignaciones = new ArrayList();
            AlbaranLineaDto linea = getLineaAlbaranByBooking(booking);
            ArgumentValidator.isNotNull(linea, "No se ha podido cargar la linea e albara con booking \"" + booking + "\"");
            fecha = getAlbaran().getFecha();
            if (linea != null) {
                asignaciones.add(new StockAsignacionDto(
                    booking, 
                    linea.getStock() != null ? linea.getStock() : null,
                    linea.getTipoProducto(), 
                    getAlbaran().getOrigen(),
                    linea.getLote(), 
                    linea.getNumSerie())
                );                
            }
            setTransportistaId(getAlbaran().getTransportista() == null ? null : getAlbaran().getTransportista().getId());
            setTransportistaNombre(getAlbaran().getTransportista() == null ? "Sus medios" : getAlbaran().getTransportista().getNombre());
            setChofer("chofer");
            setMatricula("");
            setObservaciones("");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public SelectorStockBean getSelectorStockBean() {
        return selectorStockBean;
    }

    public void setSelectorStockBean(SelectorStockBean selectorStockBean) {
        this.selectorStockBean = selectorStockBean;
    }

    public StockAsignacionDto getAsignacion(String booking) {
        for (StockAsignacionDto asignacion : asignaciones) {
            if (Objects.equals(asignacion.getBooking(), booking)) {
                return asignacion;
            }
        }
        return null;
    }
    
    public List<StockAsignacionDto> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<StockAsignacionDto> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public StockAsignacionDto getSelected() {
        return selected;
    }

    public void setSelected(StockAsignacionDto selected) {
        this.selected = selected;
    }

    public void prepareSelectStock() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(selected, "Seleccione alguna linea para asignara.");
            ArgumentValidator.isNotNull(selected.getTipoProducto(), "La linea no tiene tipo de producto.");
            Map<String, Object> defaultFilters = new HashMap();
            defaultFilters.put("tipoProducto.id", selected.getTipoProducto().getId());
            defaultFilters.put("estado.nombre", "DISPONIBLE");
            selectorStockBean.setDefaultFilters(defaultFilters);
            selectorStockBean.setOnSeleccionarUpdate("@(.EntregaBookingAlbaran-Lineas)");
            selectorStockBean.setOnSeleccionarListener((ActionEvent event1) -> {
                StockMiniDto seleccionado = selectorStockBean.getSelectedMini();
                ArgumentValidator.isNotNull(seleccionado, "No se ha seleccionado ninguna linea.");
                selected.setStock(seleccionado);
                /*selected.setTipoProducto(seleccionado.getTipoProducto());
                selected.setLote(seleccionado.getLote());
                selected.setNumSerie(seleccionado.getNumSerie());*/
            });
            selectorStockBean.setOnCancelarListener((ActionEvent event1) -> {
            });            
        } catch (Exception ex) {
            JsfUtil.showErrorDialog(ex);
        } finally {
            log.finish();
        }
    }

    @Override
    protected void guardarImpl() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotEmpty(asignaciones, "Tiene que asignar el producto.");
            List<AsignacionStockForm> asignacionesForm = new ArrayList();
            for (StockAsignacionDto asignacion : asignaciones) {
                asignacionesForm.add(
                    new AsignacionStockForm(
                        asignacion.getBooking(), 
                        asignacion.getStock() == null ? null : asignacion.getStock().getId()
                    )
                );
            }
            Integer reservaId;
            if ("E".equals(albaran.getTipo().getId())) {
                reservaId = facade.crearEntreguese(
                    albaranId, 
                    true,
                    fecha,
                    asignacionesForm, 
                    transportistaId, 
                    transportistaNombre, 
                    chofer, 
                    matricula, 
                    observaciones
                );
            } else {
                reservaId = facade.crearAdmitase(
                    albaranId, 
                    true,
                    fecha,
                    asignacionesForm, 
                    transportistaId, 
                    transportistaNombre, 
                    chofer, 
                    matricula, 
                    observaciones
                );
            }
            ArgumentValidator.isNotNull(reservaId, "No se ha podido recuperar la reserva creada.");
            PrimeFaces.current().executeScript("printWindow=window.open('../reservas/pdf.xhtml?id=" + reservaId.toString() + "');printWindow.onload = setTimeout(() => { printWindow.print(); },1000);");
        } catch (Exception ex) {
            JsfUtil.validationFailed();
            JsfUtil.showErrorDialog(ex);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="ReservarBookingAsignacionConverter")
    public static class ReservarBookingAsignacionConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReservarBookingAlbaranBean bean = (ReservarBookingAlbaranBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "reservarBookingAlbaranBean");
            return bean.getAsignacion(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof StockAsignacionDto) {
                StockAsignacionDto f = (StockAsignacionDto) object;
                return f.getBooking();
            } else {
                return null;
            }
        }
    }
    
}