package es.zarca.covellog.interfaces.web.app.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import es.zarca.covellog.interfaces.web.app.model.Operacion;
import es.zarca.covellog.interfaces.web.empresas.model.ListadoEmpresasModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author francisco
 * @param <T>
 */
public abstract class BusquedaTransversalSelector<T> extends BusquedaSelector<T> {
    
    private static final Logger LOGGER = Logger.getLogger(ListadoEmpresasModel.class.getName());
    
    private String filtro="";
    private Map<String, BusquedaTransversalLazyDataModel> itemsTransversal;
    
    public class BusquedaTransversalLazyDataModel extends LazyDataModel<T> {
        private List<T> list;
        private final Map<String, Object> filtrosBase;
        
        public BusquedaTransversalLazyDataModel(Map<String, Object> filters){            
            filtrosBase = filters;
            filtrosBase.putAll(getDefaultFilters());
            this.setRowCount(getFacade().findTotalCount(filtrosBase));
        }
        
        public int getRowCountSinSubFiltro() {
            return getFacade().findTotalCount(filtrosBase);
        }
        
        @Override
        public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            System.err.println("COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES COJONES ");
            System.err.println(filtrosBase.size());
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
    
    public BusquedaTransversalSelector() {
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
    
    @Override
    public void actualizarBusqueda() {
        super.actualizarBusqueda();
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            itemsTransversal = null;
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

}
