package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller.DireccionEnvioEditController;
import es.zarca.covellog.interfaces.web.empresas.model.ListadoEmpresasModel;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.event.SelectEvent;


/**
 *
 * @author francisco
 * @param <T>
 */
public abstract class Selector<T> {
    
    private static final Logger LOGGER = Logger.getLogger(ListadoEmpresasModel.class.getName());
    
    protected List<T> items;
    protected T selected;
    private Map<String, Object> defaultFilters = new HashMap<>();
    private ActionListener onSeleccionarListener;
    private ActionListener onCancelarListener;
    private String onSeleccionarUpdate;
    private String onCancelarUpdate;
    
    
    public Selector() {
    }
    
    abstract public Object getItemRowKey(T item);

    abstract public boolean hasRowKey(T item, String rowKey);
    
    public List<T> getItems(){
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
    
    public void returnValue(ActionListener actionListener) {
       
       JsfUtil.showErrorDialog("vamos bien");
    }
    
    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }
    
    public ActionListener getOnSeleccionarListener() {
        return onSeleccionarListener;
    }

    public void setOnSeleccionarListener(ActionListener onSeleccionarListener) {
        this.onSeleccionarListener = onSeleccionarListener;
    }

    public ActionListener getOnCancelarListener() {
        return onCancelarListener;
    }

    public void setOnCancelarListener(ActionListener onCancelarListener) {
        this.onCancelarListener = onCancelarListener;
    }

    public String getOnSeleccionarUpdate() {
        return onSeleccionarUpdate;
    }

    public void setOnSeleccionarUpdate(String onSeleccionarUpdate) {
        this.onSeleccionarUpdate = onSeleccionarUpdate;
    }

    public String getOnCancelarUpdate() {
        return onCancelarUpdate;
    }

    public void setOnCancelarUpdate(String onCancelarUpdate) {
        this.onCancelarUpdate = onCancelarUpdate;
    }
    
    public void actualizarBusqueda() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            items = null;
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }

    public Map<String, Object> getDefaultFilters() {
        return defaultFilters;
    }

    public void setDefaultFilters(Map<String, Object> defaultFilters) {
        this.defaultFilters = defaultFilters;
        actualizarBusqueda();
    }
    
    public void onSeleccionar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onSeleccionarListener != null) {
                onSeleccionarListener.processAction(null);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void onSeleccionar(SelectEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onSeleccionarListener != null) {
                onSeleccionarListener.processAction(null);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void onSeleccionar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onSeleccionarListener != null) {
                onSeleccionarListener.processAction(event);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }
    
    public void onCancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onCancelarListener != null) {
                onCancelarListener.processAction(event);
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e);
        } finally {
            log.finish();
        }
    }

    public boolean getBotonSeleccionarDisabled() {
        return selected == null;
    }
}
