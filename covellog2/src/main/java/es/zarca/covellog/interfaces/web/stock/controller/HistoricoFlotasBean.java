package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FlotaDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoFlotasDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("historicoFlotasBean")
@ViewScoped
public class HistoricoFlotasBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(HistoricoFlotasBean.class.getName());
    @Inject
    private CambiarFlotaStockBean cambiarFlotaStockBean;
    @Inject
    private StockFacade facade;
    private Integer stockId;
    private StockListadoHistoricoFlotasDto model;
    
    public StockListadoHistoricoFlotasDto getModel() {
        if (model == null) {
             model = facade.findHistoricoFlotas(stockId);
        } 
        return model;
    }

    public void setModel(StockListadoHistoricoFlotasDto model) {
        this.model = model;
    }

    public FlotaDto getFlota() {
        if ((getModel() == null) || (getModel().getHistorico() == null) || (getModel().getHistorico().isEmpty())) {
             return null;
        }
        return model.getHistorico().get(getModel().getHistorico().size() - 1).getFlota();
    }
    

    void prepare(Integer stockId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(stockId, "El id de stock es obligatorio.");        
            this.stockId = stockId;
            model = null;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    public void prepareCambiarFlota() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<StockDto> lista = new ArrayList();
            lista.add(model.getStock());
            cambiarFlotaStockBean.prepare(lista);
            cambiarFlotaStockBean.setOnGuardarJsfUpdate(":DetalleFlotaStockForm");
            cambiarFlotaStockBean.setOnGuardarJsComplete("handleSubmit(args,'DetalleFlotaCambiarDialog');");
            cambiarFlotaStockBean.setOnGuardarListener( (event) -> {
                model = null;
            });
            cambiarFlotaStockBean.setOnCancelarJsfUpdate("");
            cambiarFlotaStockBean.setOnCancelarJsComplete("closeDialog('DetalleFlotaCambiarDialog');");
            cambiarFlotaStockBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void deshacerUltimoCambioFlota() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.deshacerUltimoCambioFlota(model.getStock().getId());
            //prepare(model.getStock().getId());
            model = null;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}