package es.zarca.covellog.interfaces.web.reservas;

import es.zarca.covellog.application.stock.exception.ReservaNotExistException;
import es.zarca.covellog.application.stock.form.ReservaForm;
import es.zarca.covellog.application.stock.form.ReservaLineaForm;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.ReservaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaLineaDto;
import es.zarca.covellog.interfaces.web.almacenes.controller.AlmacenBusquedaTransversalBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.HtmlToPdf;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.stock.controller.SelectorStockBean;
import es.zarca.covellog.interfaces.web.stock.controller.StockAsignacionBean;
import es.zarca.covellog.interfaces.web.transportes.TransporteSelectorBean;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
/**
 *
 * @author francisco
 */
@Named("reservaModificarBean")
@ViewScoped
public class ReservaModificarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ReservaModificarBean.class.getName());
    @Inject
    private ReservaFacade facade;
    @Inject
    private TransporteSelectorBean transporteSelectorBean;
    @Inject
    private AlmacenBusquedaTransversalBean almacenBusquedaTransversalBean;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    @Inject
    private SelectorStockBean selectorStockBean;
    @Inject
    private StockAsignacionBean stockAsignacionBean;
    private List<ReservaLineaDto> selecteds;
    private Integer id;
    private ReservaDto reserva;
    private String reservaTipoId ;
    private List<ReservaLineaDto> pendientesAsignar;
    
    public ReservaModificarBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReservaDto getReserva() {
        if (reserva == null) {
            if (id != null) {
                reserva = facade.findById(id);
            } 
            if (reserva == null) {
                reserva = new ReservaDto();
                reserva.setLineas(new ArrayList());
            }
        }
        return reserva;
    }

    public void setReserva(ReservaDto reserva) {
        this.reserva = reserva;
    }

    public List<ReservaLineaDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ReservaLineaDto> selecteds) {
        this.selecteds = selecteds;
    }

    public String getReservaTipoId() {
        return reservaTipoId;
    }

    public void setReservaTipoId(String reservaTipoId) {
        this.reservaTipoId = reservaTipoId;
    }
    
    public String getEstadoLowerCase() {
        if ((getReserva().getEstado() == null)) {
            return "";
        }
        return reserva.getEstado().getId().toLowerCase();
    }
    
    public boolean getCanGenerarMovimientos() {
        if ((reserva.getLineas() != null) && (reserva.getLineas().size() > 0)) {
            for (ReservaLineaDto selected : reserva.getLineas()) {
                if (selected.getMovimiento() != null) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void printPdf() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            JsfUtil.printPdfFromHtml(getReserva().getFriendlyId() + ".pdf", "reservas/print.xhtml", "id=" + getReserva().getId().toString());
        } catch (Exception ex) {
            JsfUtil.showErrorDialog(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare() {
        if (id == null) {
            prepareAdmitase();
        } else {
            prepareModificar(id);    
        }
    }
    
    public void prepareAdmitase() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            reserva = new ReservaDto();
            reserva.setFecha(new Date());
            reservaTipoId = "E";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareEntreguese() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            reserva = new ReservaDto();
            reserva.setFecha(new Date());
            reservaTipoId = "S";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareModificar(Integer reservaId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(reservaId, "Debe especificar un id de reserva.");
            reserva = facade.findById(reservaId);
            if (reserva == null) {
                throw new ReservaNotExistException(reservaId);
            }
            //System.err.println("COJONES: " + reservaAux);
            //PrimeFaces.current().ajax().update(":panel-interior");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectCliente(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                reserva.setCliente(selectorEmpresaController.getSelectedMiniDto());
            });
            selectorEmpresaController.setOnCancelarListener((event1) -> {
            });
            selectorEmpresaController.setOnSeleccionarUpdate("@(.ReservaModificar-EmpresaSeleccionable)");
            //selectorEmpresaController.setOnCancelarUpdate("@(.Almacen-Empresa)");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    public void prepareSelectTransportista(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarUpdate("@(.ReservaModificar-Transportista)");
            System.err.println("COJONES DE HOLANN");
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                System.err.println("COJONES DE HOLANN33333");
                  reserva.setTransportistaId(selectorEmpresaController.getSelected().getId());
                  reserva.setTransportistaNombre(selectorEmpresaController.getSelected().getNombre());
                  JsfUtil.showErrorDialog("COJONES");
            });
            selectorEmpresaController.setOnCancelarListener((ActionEvent event1) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectAlmacen(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            almacenBusquedaTransversalBean.setOnSeleccionarUpdate("@(.ReservaModificar-Almacen)");
            almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                reserva.setUbicacion(almacenBusquedaTransversalBean.getSelected());
            });
            almacenBusquedaTransversalBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectStock(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorStockBean.setOnSeleccionarUpdate("ReservaEditPanel");
            selectorStockBean.setOnSeleccionarListener((ActionEvent event1) -> {
                StockMiniDto seleccionado = selectorStockBean.getSelectedMini();
                ArgumentValidator.isNotNull(seleccionado, "No se ha seleccionado ningun producto.");
                if (reserva.getLineas() == null) {
                    reserva.setLineas(new ArrayList());                    
                }
               /* if (reserva.getLineas().isEmpty()) {
                    ReservaDto dto = facade.getAdmitaseAuto(seleccionado.getId());
                    System.err.println("COJONES YUYU: " + (dto.getCliente() == null? "es null" : dto.getCliente().getNombre()));
                    reserva.setCliente(dto.getCliente());
                    reserva.setUbicacion(dto.getUbicacion());
                    if (dto.getTransportistaId() != null) {
                        reserva.setTransportistaId(dto.getTransportistaId());
                    }
                    if (dto.getTransportistaNombre() != null) {
                        reserva.setTransportistaNombre(dto.getTransportistaNombre());
                    }
                }
                reserva.getLineas().add(seleccionado);*/
            });
            selectorStockBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminarLinea(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            for (ReservaLineaDto selected : selecteds) {
                reserva.getLineas().remove(selected);
            }
            selecteds = new ArrayList();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void imprimir(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            HtmlToPdf htmlToPdf = new HtmlToPdf();
            //String xhtml = htmlToPdf.createWellFormedHtml(inputHTML);
            System.out.println("Starting conversion to PDF...");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            htmlToPdf.appUrlToPdf("reservas/print.xhtml", "id=" + reserva.getId(), outputStream);                 
            JsfUtil.showFileBufferInNewTab(reserva.getId() + ".pdf", outputStream.toByteArray());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void activar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.activar(reserva.getId());
            reserva = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void reabrir(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.reabrir(reserva.getId());
            reserva = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void anular(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.anular(reserva.getId());
            reserva = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
            
    public void prepareMovimiento(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            stockAsignacionBean.prepare(reserva);
            stockAsignacionBean.setOnGuardarJsfUpdate("ReservaForm");
            stockAsignacionBean.setOnGuardarListener((event2) -> {
                reserva = null;
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    /*public void generarMovimiento(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List <AsignacionStockForm> asignaciones = new ArrayList();
            pendientesAsignar = new ArrayList();
            for (ReservaLineaDto linea : reserva.getLineas()) {
                if (linea.getNumSerie() == null) {
                    pendientesAsignar.add(linea);
                } else {
                    asignaciones.add(new AsignacionStockForm(linea.getBooking(), linea.getStock().getId()));
                }
            }
            if (pendientesAsignar.isEmpty()) {
                facade.generarMovimiento(reserva.getId(), new Date(), asignaciones);
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    public void guardar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ReservaForm form = new ReservaForm();
            form.setFecha(reserva.getFecha());
            form.setBooking(reserva.getBooking());
            form.setAlmacenId(reserva.getUbicacion() == null ? null : reserva.getUbicacion().getId());
            List<ReservaLineaForm> lineasForm = new ArrayList();
            if (reserva.getLineas() != null) {
                for (ReservaLineaDto linea: reserva.getLineas()) {
                    lineasForm.add(
                        new ReservaLineaForm(
                            linea.getBooking(), 
                            linea.getStock() != null ? linea.getStock().getId() : null,
                            linea.getTipoProducto().getId(), 
                            linea.getLote(), 
                            linea.getNumSerie()
                        )
                    );
                }
            }
            form.setLineas(lineasForm);
            form.setClienteId(reserva.getCliente() == null ? null : reserva.getCliente().getId());
            form.setTransportistaId(reserva.getTransportistaId());
            form.setTransportistaNombre(reserva.getTransportistaNombre());
            form.setChofer(reserva.getChofer());
            form.setMatricula(reserva.getMatricula());
            form.setObservaciones(reserva.getObservaciones());
            facade.modificar(reserva.getId(), form);
            JsfUtil.addSuccessMessage("Reserva modificada satisfactoriamente.");
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.getApplication().getNavigationHandler().handleNavigation(context, null, "/reservas/modificar?faces-redirect=true&id=" + reserva.getId());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    @FacesConverter(value="ReservaLineaConverter")
    public static class ReservaLineaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReservaModificarBean bean = (ReservaModificarBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "reservaModificarBean");
            for (ReservaLineaDto linea : bean.getReserva().getLineas()) {
                if (Objects.equals(String.valueOf(linea.getId()), value)) {
                    return linea;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ReservaLineaDto) {
                ReservaLineaDto f = (ReservaLineaDto) object;
                return f.getId().toString();
            } else {
                return null;
            }
        }
    }
}