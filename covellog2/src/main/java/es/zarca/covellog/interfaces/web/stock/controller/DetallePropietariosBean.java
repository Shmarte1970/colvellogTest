package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.listadohistorico.StockListadoHistoricoPropietariosDto;
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
@Named("detallePropietariosBean")
@ViewScoped
public class DetallePropietariosBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DetallePropietariosBean.class.getName());
    @Inject
    private CambiarPropietarioStockBean cambiarPropietarioStockBean;
    @Inject
    private StockFacade facade;
    private Integer stockId;
    private StockListadoHistoricoPropietariosDto model;
    
    public StockListadoHistoricoPropietariosDto getModel() {
        if (model == null) {
             model = facade.findHistoricoPropietarios(stockId);
        } 
        return model;
    }

    public void setModel(StockListadoHistoricoPropietariosDto model) {
        this.model = model;
    }

    public EmpresaMiniDto getPropietario() {
        if ((getModel() == null) || (getModel().getHistorico() == null) || (getModel().getHistorico().isEmpty())) {
            System.err.println("COJONES PROPI 0");
            return null;
        }
        System.err.println("COJONES PROPI 1");
        return model.getHistorico().get(getModel().getHistorico().size() - 1).getPropietario();
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
    
    
    public void prepareCambiarPropietario() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<StockDto> lista = new ArrayList();
            lista.add(model.getStock());
            cambiarPropietarioStockBean.prepare(lista);
            cambiarPropietarioStockBean.setOnGuardarJsfUpdate(":DetallePropietarioStockForm");
            cambiarPropietarioStockBean.setOnGuardarListener( (event) -> {
                model = null;
            });
            cambiarPropietarioStockBean.setOnCancelarJsfUpdate("");
            cambiarPropietarioStockBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void deshacerUltimoCambioPropietario() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.deshacerUltimoCambioPropietario(model.getStock().getId());
            //prepare(model.getStock().getId());
            model = null;
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}