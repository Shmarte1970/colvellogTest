package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.application.exception.ValidationBusinessException;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FlotaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
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
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 *//**
 *
 * @author francisco
 */
@Named("cambiarFlotaStockBean")
@ViewScoped
public class CambiarFlotaStockBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CambiarFlotaStockBean.class.getName());
    @Inject
    private StockFacade facade;
    
    private List<StockDto> selecteds = new ArrayList();
    private String flotaId;
    private List<FlotaDto> flotasPosibles;
    @Size(max = 10000)
    private String observaciones;
    private boolean validationFailed = false;
    
    public CambiarFlotaStockBean() {
    }
    
    public List<StockDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<StockDto> selecteds) {
        this.selecteds = selecteds;
    }

    public String getFlotaId() {
        return flotaId;
    }
    
    public List<FlotaDto> getFlotasPosibles() {
        if (flotasPosibles == null) {
            flotasPosibles = facade.getFlotasPosibles();
        }
        return flotasPosibles;
    }

    public void setFlotaId(String flotaId) {
        this.flotaId = flotaId;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void prepare(List<StockDto> selecteds) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            this.selecteds = selecteds;
            flotaId = null;
            observaciones = "";
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
            if (flotaId == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("FlotaStockForm:flota", "La flota es obligatoria.");
            }
            if (!validationFailed) {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.cambiarFlota(ids, observaciones, flotaId);
                JsfUtil.addSuccessMessage("Flota cambiada correctamente.");
                notificarGuardar(event);
            } else {
                JsfUtil.validationFailed();
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

}