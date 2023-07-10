package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import es.zarca.covellog.interfaces.web.app.model.Operacion;
import es.zarca.covellog.interfaces.web.empresas.model.ListadoEmpresasModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public abstract class BusquedaTransversalSelector1<T> {
    
    private static final Logger LOGGER = Logger.getLogger(ListadoEmpresasModel.class.getName());
    
    private DefaultLazyDataModel items;
    private T selected;
    private String filtro="";
    private Map<String, BusquedaTransversalLazyDataModel> itemsTransversal;
    private Map<String, Object> defautFilters = new HashMap<>();
    private ActionListener onSeleccionarListener;
    private ActionListener onCancelarListener;
    private String onSeleccionarUpdate;
    private String onCancelarUpdate;
    
    public class DefaultLazyDataModel extends LazyDataModel<T> {
        private List<T> list;
        
        public DefaultLazyDataModel(){
            this.setRowCount(getFacade().findTotalCount(defautFilters));
        }
        
        @Override
        public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            filters.putAll(defautFilters);
            list = getFacade().find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().findTotalCount(filters));
            return list;
        }
        
        public int getRowCountSinFiltrar() {
            return getFacade().findTotalCount(defautFilters);
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
    
    public class BusquedaTransversalLazyDataModel extends LazyDataModel<T> {
        private List<T> list;
        private final Map<String, Object> filtrosBase;
        
        public BusquedaTransversalLazyDataModel(Map<String, Object> filters){            
            filtrosBase = filters;
            filtrosBase.putAll(defautFilters);
            this.setRowCount(getFacade().findTotalCount(filtrosBase));
        }
        
        public int getRowCountSinSubFiltro() {
            return getFacade().findTotalCount(filtrosBase);
        }
        
        @Override
        public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            filters.putAll(filtrosBase);
            list = getFacade().find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().findTotalCount(filters));
            return list;
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
    
    public BusquedaTransversalSelector1() {
    }
    
    abstract public Object getItemRowKey(T item);

    abstract public boolean hasRowKey(T item, String rowKey);
    
    abstract public BusquedaFacade<T> getFacade();
       
    public DefaultLazyDataModel getItems(){
        if (items == null) {
            items = new DefaultLazyDataModel();
        }  
        return items;
    }
  
    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }
    
    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        if (!this.filtro.equals(filtro)) {
            this.filtro = filtro;
            itemsTransversal = null;
        }
        
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
    
    public Map<String, BusquedaTransversalLazyDataModel> getItemsTransversal() {
        if (((itemsTransversal == null) && (filtro != null) && (!filtro.isEmpty()))) {
            itemsTransversal = new LinkedHashMap<>();
            for (Filtro filtroPosible : getFacade().getFiltrosPosibles()) {
                if (filtroPosible.getOperacion().equals(Operacion.CONTAINS)) {
                    Map<String, Object> filters = new LinkedHashMap<>();
                    filters.put(filtroPosible.toString(), filtro);
                    itemsTransversal.put(
                        filtroPosible.toString(), 
                        new BusquedaTransversalLazyDataModel(filters)
                    );
                }
            }
        }
        return itemsTransversal;
    }

    public List<Filtro> getFiltrosPosibles() {
        return getFacade().getFiltrosPosibles();
    }
    
    public List<String> getItemsTransversalKeys() {
        List<String> lista = new ArrayList<>();
        if ( (getItemsTransversal() != null) && (!getItemsTransversal().isEmpty()) ) {
            getItemsTransversal().keySet().forEach(key -> {
                lista.add(key);
            });
        }
        return lista;
    }
    
    public Boolean getHayResultadosBusquedaTransversal() {
        if (itemsTransversal != null) {
            for (Map.Entry<String, BusquedaTransversalLazyDataModel> entry : itemsTransversal.entrySet()) {
                String key = entry.getKey();
                BusquedaTransversalLazyDataModel value = entry.getValue();
                if (value.getRowCount() > 0) {
                    return true;
                }            
            }
        }
        return false;
    }
    
    public void actualizarBusqueda() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            itemsTransversal = null;
            items = null;
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

    public Map<String, Object> getDefautFilters() {
        return defautFilters;
    }

    public void setDefautFilters(Map<String, Object> defautFilters) {
        this.defautFilters = defautFilters;
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
    
}
