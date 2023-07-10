package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.empresas.model.ListadoEmpresasModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author francisco
 * @param <T>
 */
public abstract class BusquedaSelector<T> {
    
    private static final Logger LOGGER = Logger.getLogger(ListadoEmpresasModel.class.getName());
    
    private DefaultLazyDataModel items;
    private T selected;
    private Map<String, Object> defaultFilters = new HashMap<>();
    private ActionListener onSeleccionarListener;
    private ActionListener onCancelarListener;
    private String onSeleccionarUpdate;
    private String onCancelarUpdate;
    
    public class DefaultLazyDataModel extends LazyDataModel<T> {
        private List<T> list;
        
        public DefaultLazyDataModel(){
            this.setRowCount(getFacade().findTotalCount(defaultFilters));
        }
        
        @Override
        public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            System.err.println("COJONES COJONES busquedaselector " + String.valueOf(defaultFilters.size()));
            filters.putAll(defaultFilters);
            list = getFacade().find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().findTotalCount(filters));
            return list;
        }
        
        public int getRowCountSinFiltrar() {
            return getFacade().findTotalCount(defaultFilters);
        }
        
        @Override
	public Object getRowKey(T item) {
            return getItemRowKey(item);
	}

	@Override
	public T getRowData(String rowKey) {
            for (T item : list) {
                if (hasRowKey(item, rowKey)) {
                    return item;
                }
            }
            return null;
        }        
    }
    
    public BusquedaSelector() {
    }
    
    abstract public Object getItemRowKey(T item);

    abstract public boolean hasRowKey(T item, String rowKey);
    
    abstract public BusquedaFacade<T> getFacade();
       
    public DefaultLazyDataModel getItems(){
        if (items == null) {
            items = new DefaultLazyDataModel();
        }  
        System.err.println("COJONES DefaultLazyDataModel getItems(): " + String.valueOf(items.getRowCount()));
        return items;
    }
    
    public void returnValue(ActionListener actionListener) {
        System.err.println("COJONES QUE RARO RARO RARO RARO ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
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
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

    public Map<String, Object> getDefaultFilters() {
        return defaultFilters;
    }

    public void setDefaultFilters(Map<String, Object> defaultFilters) {
        System.err.println("COJONES COJONES busquedaselector.setDefaultFilters " + String.valueOf(defaultFilters.size()));
        this.defaultFilters = defaultFilters;
        actualizarBusqueda();
    }
    
    public void onSeleccionar(SelectEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onSeleccionarListener != null) {
                onSeleccionarListener.processAction(null);
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
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
            JsfUtil.addException(e);
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
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

    public boolean getBotonSeleccionarDisabled() {
        return selected == null;
    }
}
