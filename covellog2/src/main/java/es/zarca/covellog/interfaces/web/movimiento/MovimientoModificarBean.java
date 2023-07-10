package es.zarca.covellog.interfaces.web.movimiento;
import es.zarca.covellog.application.stock.exception.MovimientoNotExistException;
import es.zarca.covellog.application.stock.form.MovimientoForm;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoLinea;
import es.zarca.covellog.domain.model.stock.movimientos.MovimientoTipo;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.MovimientoFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoLineaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoLineaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.web.almacenes.controller.AlmacenBusquedaTransversalBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.HtmlToPdf;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.stock.controller.SelectorStockBean;
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
@Named("movimientoModificarBean")
@ViewScoped
public class MovimientoModificarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MovimientoModificarBean.class.getName());
    @Inject
    private MovimientoFacade facade;
    @Inject
    private TransporteSelectorBean transporteSelectorBean;
    @Inject
    private AlmacenBusquedaTransversalBean almacenBusquedaTransversalBean;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    @Inject
    private SelectorStockBean selectorStockBean;
    private List<MovimientoLineaDto> selecteds;
    private Integer id;
    private MovimientoDto movimiento;
    private String movimientoTipoId ;
    
    public MovimientoModificarBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MovimientoDto getMovimiento() {
        if (movimiento == null) {
            movimiento = new MovimientoDto();
            movimiento.setLineas(new ArrayList());
        }
        return movimiento;
    }

    public void setMovimiento(MovimientoDto movimiento) {
        this.movimiento = movimiento;
    }

    public List<MovimientoLineaDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<MovimientoLineaDto> selecteds) {
        this.selecteds = selecteds;
    }

    public String getMovimientoTipoId() {
        return movimientoTipoId;
    }

    public void setMovimientoTipoId(String movimientoTipoId) {
        this.movimientoTipoId = movimientoTipoId;
    }

    public void prepare() {
        if (id == null) {
            prepareEntrada();
        } else {
            prepareModificar(id);    
        }
    }
    
    public void prepareEntrada() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            movimiento = new MovimientoDto();
            movimiento.setFecha(new Date());
            movimientoTipoId = "E";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSalida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            movimiento = new MovimientoDto();
            movimiento.setFecha(new Date());
            movimientoTipoId = "S";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareModificar(Integer movimientoId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(movimientoId, "Debe especificar un id de movimiento.");
            movimiento = facade.findById(movimientoId);
            if (movimiento == null) {
                throw new MovimientoNotExistException(movimientoId);
            }
            //System.err.println("COJONES: " + movimientoAux);
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
                movimiento.setCliente(selectorEmpresaController.getSelectedMiniDto());
            });
            selectorEmpresaController.setOnCancelarListener((event1) -> {
            });
            selectorEmpresaController.setOnSeleccionarUpdate("@(.MovimientoModificar-EmpresaSeleccionable)");
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
            selectorEmpresaController.setOnSeleccionarUpdate("@(.MovimientoModificar-Transportista)");
            System.err.println("COJONES DE HOLANN");
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                System.err.println("COJONES DE HOLANN33333");
                  movimiento.setTransportistaId(selectorEmpresaController.getSelected().getId());
                  movimiento.setTransportistaNombre(selectorEmpresaController.getSelected().getNombre());
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
            almacenBusquedaTransversalBean.setOnSeleccionarUpdate("@(.MovimientoModificar-Almacen)");
            almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                movimiento.setUbicacion(almacenBusquedaTransversalBean.getSelected());
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
            selectorStockBean.setOnSeleccionarUpdate("MovimientoEditPanel");
            selectorStockBean.setOnSeleccionarListener((ActionEvent event1) -> {
                StockMiniDto seleccionado = selectorStockBean.getSelectedMini();
                ArgumentValidator.isNotNull(seleccionado, "No se ha seleccionado ningun producto.");
                if (movimiento.getLineas() == null) {
                    movimiento.setLineas(new ArrayList());                    
                }
                if (movimiento.getLineas().isEmpty()) {
                    MovimientoDto dto = facade.getEntradaAutocompleteFromStockId(seleccionado.getId());
                    System.err.println("COJONES YUYU: " + (dto.getCliente() == null? "es null" : dto.getCliente().getNombre()));
                    movimiento.setCliente(dto.getCliente());
                    movimiento.setUbicacion(dto.getUbicacion());
                    if (dto.getTransportistaId() != null) {
                        movimiento.setTransportistaId(dto.getTransportistaId());
                    }
                    if (dto.getTransportistaNombre() != null) {
                        movimiento.setTransportistaNombre(dto.getTransportistaNombre());
                    }
                }
                MovimientoLineaDto linea = new MovimientoLineaDto();
                linea.setBooking("SIN BOOKING");
                linea.setStock(seleccionado);
                movimiento.getLineas().add(linea);
            });
            selectorStockBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminarStock(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            for (MovimientoLineaDto selected : selecteds) {
                movimiento.getLineas().remove(selected);
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
            htmlToPdf.appUrlToPdf("movimientos/print.xhtml", "id=" + movimiento.getId(), outputStream);                 
            JsfUtil.showFileBufferInNewTab(movimiento.getId() + ".pdf", outputStream.toByteArray());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            MovimientoForm form = new MovimientoForm();
            form.setFecha(movimiento.getFecha());
            form.setAlmacenId(movimiento.getUbicacion() == null ? null : movimiento.getUbicacion().getId());
            List<Integer> ids = new ArrayList();
            if (movimiento.getLineas() != null) {
                for (MovimientoLineaDto stock : movimiento.getLineas()) {
                    ids.add(stock.getId());
                }
            }
            form.setStockIds(ids);
            form.setClienteId(movimiento.getCliente() == null ? null : movimiento.getCliente().getId());
            form.setTransportistaId(movimiento.getTransportistaId());
            form.setTransportistaNombre(movimiento.getTransportistaNombre());
            form.setChofer(movimiento.getChofer());
            form.setMatricula(movimiento.getMatricula());
            form.setObservaciones(movimiento.getObservaciones());
            if ("E".equals(movimientoTipoId)) {
                movimiento = facade.entrada(form);
                JsfUtil.addSuccessMessage("Entrada guardada correctamente.");
            } else {
                movimiento = facade.salida(form);
                JsfUtil.addSuccessMessage("Salida guardado correctamente.");
            }
            
            
            //FacesContext context = FacesContext.getCurrentInstance();
            //context.getApplication().getNavigationHandler().handleNavigation(context, null, "/movimientos/modificar?faces-redirect=true&id=" + movimiento.getId());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.eliminar(movimiento.getId());
            JsfUtil.addSuccessMessage("Movimiento eliminado correctamente.");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/reservas/modificar?faces-redirect=true&id=" + movimiento.getReserva().getId());
        } catch (Exception ex) {
            JsfUtil.showErrorDialog(ex.getMessage());
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="MovimientoLineaConverter")
    public static class MovimientoLineaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientoModificarBean bean = (MovimientoModificarBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "movimientoModificarBean");
            for (MovimientoLineaDto linea : bean.getMovimiento().getLineas()) {
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
            if (object instanceof MovimientoLineaDto) {
                MovimientoLineaDto f = (MovimientoLineaDto) object;
                return f.getId().toString();
            } else {
                return null;
            }
        }
    }
}