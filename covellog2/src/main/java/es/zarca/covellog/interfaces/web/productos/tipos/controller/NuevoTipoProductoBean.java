package es.zarca.covellog.interfaces.web.productos.tipos.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.TiposProductosServiceFacade;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("nuevoTipoProductoBean")
@ViewScoped
public class NuevoTipoProductoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(NuevoTipoProductoBean.class.getName());
    @Inject
    private TiposProductosServiceFacade facade;
    private ActionListener listener;
    
    
    public NuevoTipoProductoBean() {
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
    
    void prepare(String id) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            JsfUtil.showErrorDialog("NuevoTipoProductoBean:prepare!!!");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
          
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareSelectTipoProducto() {
        /* selectorTipoProductoBean.setListener((ActionEvent event1) -> {
             updateTipoProducto();
         });*/
    }
    
    public void updateTipoProducto() {
        JsfUtil.showErrorDialog("NuevoTipoProductoBean:updateTipoProducto!!!");
    }

}