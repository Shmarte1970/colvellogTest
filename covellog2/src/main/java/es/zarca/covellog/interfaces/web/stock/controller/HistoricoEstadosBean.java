package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.EstadoDto;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoEstadosDto;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("historicoEstadosBean")
@ViewScoped
public class HistoricoEstadosBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(HistoricoEstadosBean.class.getName());
    @Inject
    private StockFacade facade;
    private Integer stockId;
    private StockListadoHistoricoEstadosDto model;
    
    public StockListadoHistoricoEstadosDto getModel() {
        if (model == null) {
             model = facade.findHistoricoEstados(stockId);
        } 
        return model;
    }

    public void setModel(StockListadoHistoricoEstadosDto model) {
        this.model = model;
    }

    public EstadoDto getEstado() {
        if ((getModel() == null) || (getModel().getHistorico() == null) || (getModel().getHistorico().isEmpty())) {
            return null;
        }
        return model.getHistorico().get(getModel().getHistorico().size() - 1).getEstado();
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
    
}