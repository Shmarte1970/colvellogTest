package es.zarca.covellog.interfaces.web.comerciales.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.comerciales.ComercialSelectorFacade;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.web.comerciales.model.SelectorComercialesModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("selectorComercialesController")
@ViewScoped
public class SelectorComercialesController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorComercialesController.class.getName());
    
    @Inject
    private ComercialSelectorFacade facade;
    private SelectorComercialesModel model = new SelectorComercialesModel();

    public SelectorComercialesController() {
        
    }

    public SelectorComercialesModel getModel() {
        if ( (model != null) && (model.getItems() == null) ) {
            model.setItems(facade.getComercialesPosibles());
        }
        return model;
    }
    
    public void setModel(SelectorComercialesModel model) {
        this.model = model;
    }
    
    public ComercialDto getComercial(Integer comercialId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            return facade.getComercial(comercialId);
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }  
        return null;
    }
    
    public void onSelect() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model.getListener() != null) {
                model.getListener().processAction(null);
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }        
    }
    
}