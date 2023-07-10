package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.CondicionDto;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 */
@Named("cambiarCondicionStockBean")
@ViewScoped
public class CambiarCondicionStockBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CambiarCondicionStockBean.class.getName());
    @Inject
    private StockFacade facade;
    private List<StockDto> selecteds = new ArrayList();
    private String condicionId;
    private List<CondicionDto> condicionesPosibles;
    @NotNull
    private Date fecha;
    
    @Size(max = 10000)
    private String observaciones;

    public CambiarCondicionStockBean() {
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

    public String getCondicionId() {
        return condicionId;
    }

    public void setCondicionId(String condicionId) {
        this.condicionId = condicionId;
    }

    public List<CondicionDto> getCondicionesPosibles() {
        if (condicionesPosibles == null) {
            condicionesPosibles = facade.getCondicionesPosibles();
        }
        return condicionesPosibles;
    }

    public void setCondicionesPosibles(List<CondicionDto> condicionesPosibles) {
        this.condicionesPosibles = condicionesPosibles;
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
            fecha = new Date();
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
            List<Integer> ids = new ArrayList();
            selecteds.forEach(selected -> {
                ids.add(selected.getId());
            });
            facade.cambiarCondicion(ids, fecha, condicionId,  observaciones);
            JsfUtil.addSuccessMessage(String.valueOf(ids.size()) + " stocks modificados correctamente");
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

}