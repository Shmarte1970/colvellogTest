package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.application.exception.ValidationBusinessException;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.web.almacenes.controller.AlmacenBusquedaTransversalBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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
@Named("trasladoStockBean")
@ViewScoped
public class TrasladoStockBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(TrasladoStockBean.class.getName());
    @Inject
    private StockFacade facade;
    @Inject
    private AlmacenBusquedaTransversalBean almacenBusquedaTransversalBean;
    private List<StockDto> selecteds = new ArrayList();

    private Date fecha;
    @NotNull
    private AlmacenDto almacenDestino;
    @Size(max = 10000)
    private String observaciones;
    private boolean validationFailed = false;
    
    
    public TrasladoStockBean() {
    }
    
    public void prepare(List<StockDto> selecteds) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            this.selecteds = selecteds;
            fecha = new Date();
            almacenDestino = null;
            observaciones = "TRASLADO";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectAlmacen(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            almacenBusquedaTransversalBean.setOnSeleccionarUpdate("@(.TrasladoStock-AlmacenSeleccionable)");
            almacenBusquedaTransversalBean.setOnSeleccionarListener((ActionEvent event1) -> {
                almacenDestino = almacenBusquedaTransversalBean.getSelected();
            });
            almacenBusquedaTransversalBean.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            validationFailed = false;
            if (fecha == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("TrasladoStockForm:fecha", "La fecha es obligatoria.");
            } 
            if (almacenDestino == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("TrasladoStockForm:almacen:almacen-comp", "El almacen es obligatorio.");
            } 
            if (!validationFailed) {
                List<Integer> ids = new ArrayList();
                for (StockDto selected : selecteds) {
                    ids.add(selected.getId());
                }
                facade.traslado(ids, fecha, observaciones, almacenDestino.getId());
                JsfUtil.addSuccessMessage("Stock modificado correctamente.");
                notificarGuardar(event);
            } else {
                JsfUtil.validationFailed();
                throw new ValidationBusinessException("Revise los errores");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }


    public List<StockDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<StockDto> selecteds) {
        this.selecteds = selecteds;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public AlmacenDto getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(AlmacenDto almacenDestino) {
        this.almacenDestino = almacenDestino;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}