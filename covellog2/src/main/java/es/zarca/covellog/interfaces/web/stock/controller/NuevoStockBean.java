package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.application.exception.ValidationBusinessException;
import es.zarca.covellog.application.stock.form.NuevoStockForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.CondicionDto;
import es.zarca.covellog.interfaces.facade.stock.dto.EstadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.FlotaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.web.almacenes.controller.AlmacenBusquedaTransversalBean;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.productos.tipos.controller.SelectorTipoProductoBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 */
@Named("nuevoStockBean")
@ViewScoped
public class NuevoStockBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(NuevoStockBean.class.getName());
    @Inject
    private StockFacade facade;
    @Inject
    SelectorTipoProductoBean selectorTipoProductoBean;
    @Inject
    AlmacenBusquedaTransversalBean almacenBusquedaTransversalBean;
    @Inject
    SelectorEmpresaController selectorEmpresaController;
    private ActionListener guardarListener = null;
    private ActionListener cancelarListener = null;
    
    private List<FlotaDto> flotasPosibles;
    private List<EstadoDto> estadosPosibles;
    private List<CondicionDto> condicionesPosibles;
    
    private Integer id;
    @NotNull
    private TipoProductoDto tipoProducto;
    @NotNull
    private AlmacenDto almacen;
    private String lote;   
    @NotNull
    private List<String> numerosSerie;
    private EmpresaDto propietario;
    private FlotaDto flota;
    private EstadoDto estado;
    private CondicionDto condicion;
    @Size(max = 10000)
    private String obsInternas;
    @Size(max = 10000)
    private String obsPublicas;
    private boolean validationFailed;

    public NuevoStockBean() {
    }

    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            id = null;
            tipoProducto = null;
            almacen = null;
            lote = "";
            numerosSerie = new ArrayList();
            numerosSerie.add("");
            flota = null;
            estado = null;
            condicion = null;
            obsInternas = "";
            obsPublicas = "";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectTipoProducto(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorTipoProductoBean.setOnSeleccionarUpdate("@(.NuevoStock-TipoProductoSeleccionable)");
            selectorTipoProductoBean.setOnSeleccionarListener((ActionEvent event1) -> {
                tipoProducto = selectorTipoProductoBean.getSelected();
            });
            selectorTipoProductoBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectAlmacen(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            almacenBusquedaTransversalBean.setOnSeleccionarUpdate("@(.TrasladoStock-AlmacenSeleccionable)");
            almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                almacen = almacenBusquedaTransversalBean.getSelected();
            });
            almacenBusquedaTransversalBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectEmpresa(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            almacenBusquedaTransversalBean.setOnSeleccionarUpdate("@(.NuevoStock-AlmacenSeleccionable)");
            almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                almacen = almacenBusquedaTransversalBean.getSelected();
            });
            almacenBusquedaTransversalBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
            
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                propietario = selectorEmpresaController.getSelected();
            });
            selectorEmpresaController.setOnCancelarListener((event1) -> {
            });
            selectorEmpresaController.setOnSeleccionarUpdate("@(.NuevoStock-EmpresaSeleccionable)");
            selectorEmpresaController.setOnCancelarUpdate("@(.NuevoStock-EmpresaSeleccionable)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public List<String> loteCompleteText(String filtro) {
        List<String> lotesList = new ArrayList<>();
        List<IdCantidadDto> dtos = facade.getLotesPosibles(filtro);
        for (IdCantidadDto dto : dtos) {
            lotesList.add(dto.getId());
        }
        return lotesList;
    }
       
    public boolean getValidationFailed() {
        return validationFailed;
    }
    
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            validationFailed = false;
            if (almacen == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:almacen:almacen-comp", "El almacen es obligatorio.");
            } 
            if (tipoProducto == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:tipoProducto:tipo-producto", "El tipo de producto es obligatorio.");
            }
            if (propietario == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:propietario:empresa", "El propietario es obligatorio.");
            }
            if (flota == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:flota", "La flota es obligatoria.");
            }
            if (estado == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:estado", "El estado es obligatorio.");
            }
            if (condicion == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:condicion", "La condicion es obligatoria.");
            }
            for(int i=0;i<numerosSerie.size();i++) {
                if ((numerosSerie.get(i) == null) || (numerosSerie.get(i).isEmpty())) {
                    validationFailed = true;
                    JsfUtil.addValidationErrorMessage("StockEditForm:numerosSerieSet:" + i + ":numeroSerie", "El numero de serie es obligatorio.");
                }
            }
            if (!validationFailed) {
                NuevoStockForm form = new NuevoStockForm();
                form.setTipoProductoId(tipoProducto.getId());
                form.setAlmacenId(almacen.getId());
                form.setLote(lote);
                form.setNumerosSerie(numerosSerie);
                form.setPropietarioId(propietario.getId());
                form.setFlotaId(flota.getId());
                form.setEstadoId(estado.getId());
                form.setCondicionId(condicion.getId());
                form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));
                facade.alta(form);
                JsfUtil.addSuccessMessage("Stock creado correctamente.");
                guardarListener.processAction(event);
            } else {
                JsfUtil.validationFailed();
                throw new ValidationBusinessException("Revise los errores");
               // 
               // PrimeFaces.current().ajax().update("StockEditForm");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private NuevoStockForm createForm() {
        NuevoStockForm form = new NuevoStockForm();        
        form.setTipoProductoId(null);
        form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));
        return form;
    }
    
    public void cancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            cancelarListener.processAction(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 

    public List<FlotaDto> getFlotasPosibles() {
        if (flotasPosibles == null) {
            flotasPosibles = facade.getFlotasPosibles();
        }
        return flotasPosibles;
    }
    
    public FlotaDto getFlota(String id) {
        for (FlotaDto dto : flotasPosibles) {
            if (dto.getId().equals(id)) {
                return dto;
            }
        }
        return null;
    }

    public List<EstadoDto> getEstadosPosibles() {
        if (estadosPosibles == null) {
            estadosPosibles = facade.getEstadosPosibles();
        }
        return estadosPosibles;
    }
    
    public EstadoDto getEstado(String id) {
        for (EstadoDto dto : estadosPosibles) {
            if (dto.getId().equals(id)) {
                return dto;
            }
        }
        return null;
    }

    public List<CondicionDto> getCondicionesPosibles() {
        if (condicionesPosibles == null) {
            condicionesPosibles = facade.getCondicionesPosibles();
        }
        return condicionesPosibles;
    }
    
    public CondicionDto getCondicion(String id) {
        for (CondicionDto dto : condicionesPosibles) {
            if (dto.getId().equals(id)) {
                return dto;
            }
        }
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public AlmacenDto getAlmacen() {
        return almacen;
    }

    public void setAlmacen(AlmacenDto almacen) {
        this.almacen = almacen;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public List<String> getNumerosSerie() {
        return numerosSerie;
    }

    public void setNumerosSerie(List<String> numerosSerie) {
        this.numerosSerie = numerosSerie;
    }
    
    public void addNumeroSerie() {
        this.numerosSerie.add("");
    }
    
    public void removeNumeroSerie(int index) {
        this.numerosSerie.remove(index);
    }

    public String getObsInternas() {
        return obsInternas;
    }

    public FlotaDto getFlota() {
        return flota;
    }

    public void setFlota(FlotaDto flota) {
        this.flota = flota;
    }

    public EstadoDto getEstado() {
        return estado;
    }

    public void setEstado(EstadoDto estado) {
        this.estado = estado;
    }

    public EmpresaDto getPropietario() {
        return propietario;
    }

    public void setPropietario(EmpresaDto propietario) {
        this.propietario = propietario;
    }

    public CondicionDto getCondicion() {
        return condicion;
    }

    public void setCondicion(CondicionDto condicion) {
        this.condicion = condicion;
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
    
    @FacesConverter(value="FlotaConverter")
    public static class FlotaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NuevoStockBean bean = (NuevoStockBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "nuevoStockBean");
            return bean.getFlota(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FlotaDto) {
                FlotaDto f = (FlotaDto) object;
                return f.getId();
            } else {
                LOGGER.log(Level.SEVERE, "FlotaDto: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), FlotaDto.class.getName()});
                return null;
            }
        }
    }
    
    @FacesConverter(value="EstadoConverter")
    public static class EstadoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NuevoStockBean bean = (NuevoStockBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "nuevoStockBean");
            return bean.getEstado(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof EstadoDto) {
                EstadoDto f = (EstadoDto) object;
                return f.getId();
            } else {
                LOGGER.log(Level.SEVERE, "EstadoDto: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), EstadoDto.class.getName()});
                return null;
            }
        }
    }
    
    @FacesConverter(value="CondicionConverter")
    public static class CondicionConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NuevoStockBean bean = (NuevoStockBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "nuevoStockBean");
            return bean.getCondicion(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CondicionDto) {
                CondicionDto f = (CondicionDto) object;
                return f.getId();
            } else {
                LOGGER.log(Level.SEVERE, "CondicionDto: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), CondicionDto.class.getName()});
                return null;
            }
        }
    }
    
}