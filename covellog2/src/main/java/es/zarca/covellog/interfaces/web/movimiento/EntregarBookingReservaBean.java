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
import es.zarca.covellog.interfaces.facade.stock.ReservaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockAsignacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaLineaDto;
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
@Named("entregarBookingReservaBean")
@ViewScoped
public class EntregarBookingReservaBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EntregarBookingReservaBean.class.getName());
    @Inject
    private ReservaFacade facade;
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
    private ReservaDto reserva;
    private Integer reservaId;
    @Inject
    private SelectorStockBean selectorStockBean;
    private List<StockAsignacionDto> asignaciones;
    private StockAsignacionDto selected;
    
    public EntregarBookingReservaBean() {
    }

    public Boolean getLoaded() {
        return reserva != null;
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

    public ReservaDto getReserva() {
        ArgumentValidator.isNotNull(reservaId, "Debe especificar el id de reserva");
        if (reserva == null) {
            reserva = facade.findById(reservaId);
        }
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }

    private ReservaLineaDto getLineaReservaByBooking(String booking) {
        for (ReservaLineaDto reservaLinea : getReserva().getLineas()) {
            System.err.println("*************************************************COJONES " +  reservaLinea.getBooking() + " != " + booking);
            if (booking.equals(reservaLinea.getBooking())) {
                return reservaLinea;
            }
        }
        return null;
    }
    
    void prepare(Integer reservaId, String booking) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            this.reservaId = reservaId;
            reserva = null;
            asignaciones = new ArrayList();
            ReservaLineaDto linea = getLineaReservaByBooking(booking);
            fecha = new Date();
            if (linea != null) {
                asignaciones.add(new StockAsignacionDto(
                    booking, 
                    linea.getStock() != null ? linea.getStock() : null,
                    linea.getTipoProducto(), 
                    getReserva().getUbicacion(),
                    linea.getLote(), 
                    linea.getNumSerie())
                );                
            }
            setTransportistaId(getReserva().getTransportistaId());
            setTransportistaNombre(getReserva().getTransportistaNombre());
            setChofer(getReserva().getChofer());
            setMatricula(getReserva().getMatricula());
            setObservaciones(getObservaciones());
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
            selectorStockBean.setOnSeleccionarUpdate("@(.EntregaBookingReserva-Lineas)");
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
                ArgumentValidator.isNotNull(asignacion.getStock(), "Faltan productor por asignar.");
                ArgumentValidator.isNotNull(asignacion.getStock().getId(), "El Producto asignado \"" + asignacion.getStock().getNumSerie() + "\" no tiene id.");
                //ids.add(asignacion.getStock().getId());
                asignacionesForm.add(new AsignacionStockForm(asignacion.getBooking(), asignacion.getStock().getId()));
            }
            facade.generarMovimiento(reservaId, fecha, asignacionesForm, transportistaId, transportistaNombre, chofer, matricula, observaciones);
            ArgumentValidator.isNotNull(reserva, "No se ha podido recuperar la reserva creada.");
            PrimeFaces.current().executeScript("printWindow=window.open('../reservas/pdf.xhtml?id=" + reserva.getId().toString() + "');printWindow.onload = setTimeout(() => { printWindow.print(); },1000);");
        } catch (Exception ex) {
            JsfUtil.validationFailed();
            JsfUtil.showErrorDialog(ex);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="EntregarBookingReservaAsignacionConverter")
    public static class EntregarBookingReservaAsignacionConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EntregarBookingReservaBean bean = (EntregarBookingReservaBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entregarBookingReservaBean");
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