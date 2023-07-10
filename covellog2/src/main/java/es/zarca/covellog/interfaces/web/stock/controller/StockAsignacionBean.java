package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.application.exception.PartialBussinesException;
import es.zarca.covellog.application.stock.form.AsignacionStockForm;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.ReservaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockAsignacionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaLineaDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 */
@Named("stockAsignacionBean")
@ViewScoped
public class StockAsignacionBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(StockAsignacionBean.class.getName());
    @Inject
    private ReservaFacade facade;
    private StockAsignacionDto selected;
    @NotNull
    private Date fecha;
    @Size(max = 10000)
    private String observaciones;
    @Inject
    private SelectorStockBean selectorStockBean;
    private List<StockAsignacionDto> asignaciones;
    private ReservaDto reserva;

    public StockAsignacionBean() {
    }

    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            fecha = new Date();
            observaciones = "";
            selected = null;
            asignaciones = new ArrayList();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(ReservaDto reserva) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            ArgumentValidator.isNotNull(reserva, "La reserva no puede ser null.");
            this.reserva = reserva;
            fecha = new Date();
            observaciones = "";
            selected = null;
            asignaciones = new ArrayList();
            for (ReservaLineaDto pendiente : reserva.getLineas()) {
                asignaciones.add(new StockAsignacionDto(
                    pendiente.getBooking(), 
                    pendiente.getStock(),
                    pendiente.getTipoProducto(), 
                    reserva.getUbicacion(),
                    pendiente.getLote(), 
                    pendiente.getNumSerie())
                );
            }
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public ReservaDto getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }
    
    @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
 
            List<AsignacionStockForm> asignacionesForm = new ArrayList();
            for (StockAsignacionDto asignacion : asignaciones) {
                ArgumentValidator.isNotNull(asignacion.getStock(), "Faltan productor por asignar.");
                //ids.add(asignacion.getStock().getId());
                asignacionesForm.add(new AsignacionStockForm(asignacion.getBooking(), asignacion.getStock().getId()));
            }
            facade.generarMovimiento(reserva.getId(), fecha, asignacionesForm);
            JsfUtil.addSuccessMessage("Movimiento creado correctamente.");
            
            /*if ("S".equals(reserva.getTipo().getId())) {
                SalidaAlmacenForm form = new SalidaAlmacenForm();
                List<Integer> ids = new ArrayList();
                for (StockAsignacionDto asignacion : asignaciones) {
                    ArgumentValidator.isNotNull(asignacion.getStock(), "Faltan productor por asignar.");
                    ids.add(asignacion.getStock().getId());
                }
                form.setStockIds(ids);
                form.setFecha(fecha);
                form.setClienteId(reserva.getCliente().getId());
                form.setTransportistaId(reserva.getTransportistaId());
                form.setTransportistaNombre(reserva.getTransportistaNombre());
                form.setChofer(reserva.getChofer());
                form.setMatricula(reserva.getMatricula());
                form.setObservaciones(observaciones);
                facade.salida(reserva.getUbicacion().getId(), form);
            } else
            {
                EntradaAlmacenForm form = new EntradaAlmacenForm();
                List<Integer> ids = new ArrayList();
                for (StockAsignacionDto asignacion : asignaciones) {
                    ArgumentValidator.isNotNull(asignacion.getStock(), "Faltan productor por asignar.");
                    ids.add(asignacion.getStock().getId());
                }
                form.setStockIds(ids);
                form.setFecha(fecha);
                form.setClienteId(reserva.getCliente().getId());
                form.setTransportistaId(reserva.getTransportistaId());
                form.setTransportistaNombre(reserva.getTransportistaNombre());
                form.setChofer(reserva.getChofer());
                form.setMatricula(reserva.getMatricula());
                form.setObservaciones(observaciones);
                facade.entrada(reserva.getUbicacion().getId(), form);
            }*/
            
            notificarGuardar(event);
        } catch (PartialBussinesException ex) {
            notificarGuardar(event);
            ExceptionHandler.handleException(ex);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void cancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            notificarCancelar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 

    public StockAsignacionDto getSelected() {
        return selected;
    }

    public void setSelected(StockAsignacionDto selected) {
        this.selected = selected;
    }

    public StockAsignacionDto getAsignacion(String booking) {
        if (asignaciones != null) {
            for (StockAsignacionDto asignacion : asignaciones) {
                if (Objects.equals(asignacion.getBooking(), booking)) {
                    return asignacion;
                }
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
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void prepareSelectStock() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(selected, "Seleccione alguna linea para asignar.s");
            ArgumentValidator.isNotNull(selected.getTipoProducto(), "La linea no tiene tipo de producto.");
            Map<String, Object> defaultFilters = new HashMap();
            System.err.println("COJONES COJONES selected.getTipoProducto().getId() " + selected.getTipoProducto().getId());
            defaultFilters.put("tipoProducto.id", selected.getTipoProducto().getId());
            selectorStockBean.setDefaultFilters(defaultFilters);
            selectorStockBean.setOnSeleccionarUpdate("@(.StockAsignacion-Lineas)");
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
    
    @FacesConverter(value="StockAsignacionConverter")
    public static class StockAsignacionConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            StockAsignacionBean bean = (StockAsignacionBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "stockAsignacionBean");
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