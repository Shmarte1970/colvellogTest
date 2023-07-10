package es.zarca.covellog.interfaces.web.adreces.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionPostalAssembler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;



@Named("direccionPostalEditController")
@ViewScoped
public class DireccionPostalEditController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DireccionPostalEditController.class.getName());

    @Inject
    private SelectorPoblacionController selectorPoblacionController;
    private DireccionPostalDto direccion;
    private ActionListener listener;
    private String update;
    
    public DireccionPostalDto getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionPostalDto direccion) {
        if (direccion == null) {
            this.direccion = new DireccionPostalDto();
        } else {
            DireccionPostalDto direccionClone = DireccionPostalAssembler.toDto(direccion);
            this.direccion = direccionClone;
        }        
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }
    
    public void prepareUpdatePoblacion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorPoblacionController.setSelected(direccion.getPoblacion());
            selectorPoblacionController.setListener((ActionEvent event1) -> {
                updatePoblacion(event1);
            });
            selectorPoblacionController.setUpdate("@(.direccion-fiscal-poblacion-editable)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updatePoblacion(javax.faces.event.ActionEvent event) {
        updatePoblacion();
    }
    
    public void updatePoblacion() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccion.setPoblacion(selectorPoblacionController.getSelected());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void onAceptar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (listener != null) {
                listener.processAction(event);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
        
}
