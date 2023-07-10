package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.application.exception.ValidationBusinessException;
import es.zarca.covellog.application.stock.form.ModificarStockForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.web.almacenes.controller.AlmacenBusquedaTransversalBean;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.productos.tipos.controller.SelectorTipoProductoBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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
@Named("modificarStockBean")
@ViewScoped
public class ModificarStockBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarStockBean.class.getName());
    @Inject
    private StockFacade facade;
    @Inject
    SelectorTipoProductoBean selectorTipoProductoBean;
    @Inject
    AlmacenBusquedaTransversalBean almacenBusquedaTransversalBean;
    @Inject
    SelectorEmpresaController selectorEmpresaController;
    
    private Integer id;
    @NotNull
    private Date fecha;
    @NotNull
    private TipoProductoDto tipoProducto;
    private String lote;   
    @NotNull
    private String numeroSerie;
    @Size(max = 10000)
    private String obsInternas;
    @Size(max = 10000)
    private String obsPublicas;
    
    private boolean validationFailed;

    public ModificarStockBean() {
    }

    public void prepare(int id) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            StockDto stock = facade.findById(id);
            this.id = id;
            fecha = new Date();
            tipoProducto = stock.getTipoProducto();
            lote = stock.getLote();
            numeroSerie = stock.getNumSerie();
            obsInternas = stock.getObservaciones().getObsInternas();
            obsPublicas = stock.getObservaciones().getObsPublicas();
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
            
            if (fecha == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:fecha", "El almacen es obligatorio.");
            } 
            if (tipoProducto == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:tipoProducto:tipo-producto", "El tipo de producto es obligatorio.");
            }
            if ((numeroSerie == null) || (numeroSerie.isEmpty())) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("StockEditForm:numeroSerie", "El numero de serie es obligatorio.");
            }
            if (!validationFailed) {
                ModificarStockForm form = new ModificarStockForm();
                form.setFecha(fecha);
                form.setTipoProductoId(tipoProducto.getId());
                form.setLote(lote);
                form.setNumeroSerie(numeroSerie);
                form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));
                facade.modificar(id, form);
                JsfUtil.addSuccessMessage("Stock modificado correctamente.");
                notificarGuardar(event);
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
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public TipoProductoDto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProductoDto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
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
    
}