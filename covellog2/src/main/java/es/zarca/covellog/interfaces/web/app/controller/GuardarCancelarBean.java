package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
/**
 *
 * @author francisco
 */
public class GuardarCancelarBean extends ComponentBean {
    private static final Logger LOGGER = Logger.getLogger(GuardarCancelarBean.class.getName());

    private ActionListener onGuardarListener;
    private String onGuardarJsfUpdate = "";
    private String onGuardarJsComplete = "";
    private ActionListener onCancelarListener;
    private String onCancelarJsfUpdate = "";
    private String onCancelarJsComplete = "";

    public ActionListener getOnGuardarListener() {
        return onGuardarListener;
    }

    public void setOnGuardarListener(ActionListener onGuardarListener) {
        this.onGuardarListener = onGuardarListener;
    }

    public String getOnGuardarJsfUpdate() {
        return onGuardarJsfUpdate;
    }

    public void setOnGuardarJsfUpdate(String onGuardarJsfUpdate) {
        this.onGuardarJsfUpdate = onGuardarJsfUpdate;
    }

    public String getOnGuardarJsComplete() {
        return onGuardarJsComplete;
    }

    public void setOnGuardarJsComplete(String onGuardarJsComplete) {
        this.onGuardarJsComplete = onGuardarJsComplete;
    }

    public ActionListener getOnCancelarListener() {
        return onCancelarListener;
    }

    public void setOnCancelarListener(ActionListener onCancelarListener) {
        this.onCancelarListener = onCancelarListener;
    }

    public String getOnCancelarJsfUpdate() {
        return onCancelarJsfUpdate;
    }

    public void setOnCancelarJsfUpdate(String onCancelarJsfUpdate) {
        this.onCancelarJsfUpdate = onCancelarJsfUpdate;
    }

    public String getOnCancelarJsComplete() {
        return onCancelarJsComplete;
    }

    public void setOnCancelarJsComplete(String onCancelarJsComplete) {
        this.onCancelarJsComplete = onCancelarJsComplete;
    }
    
    protected void notificarGuardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        if (onGuardarListener != null) {
            onGuardarListener.processAction(event);
        }
        log.finish();
    }
    
    protected void notificarCancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        if (onCancelarListener != null) {
            onCancelarListener.processAction(event);
        }
        log.finish();
    } 
    
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            guardarImpl();
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    protected void guardarImpl() {      
    }
    
    public void cancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            cancelarImpl();
            notificarCancelar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    protected void cancelarImpl() {  
    }

}