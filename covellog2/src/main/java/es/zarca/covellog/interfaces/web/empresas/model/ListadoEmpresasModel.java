package es.zarca.covellog.interfaces.web.empresas.model;

import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import es.zarca.covellog.interfaces.web.app.model.Operacion;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author francisco
 */
public class ListadoEmpresasModel implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ListadoEmpresasModel.class.getName());
    
    private EmpresaServiceFacade facade;
    private EmpresaLazyDataModel items;
    private Empresa selected;
    private String[] selectedTipoEmpresa = {};
    private String filtro="";
    private Map<String, BusquedaDiagonalEmpresaLazyDataModel> itemsDiagonal;
    private Map<String, Object> defautFilters = new HashMap<>();
    private FiltroRolEmpresa filtroRolEmpresa = FiltroRolEmpresa.TODAS;
    
    public class EmpresaLazyDataModel extends LazyDataModel<Empresa> {
        private List<Empresa> list;
        
        public EmpresaLazyDataModel(){
            this.setRowCount(facade.listarEmpresasTotalCount(defautFilters));
        }
        
        @Override
        public List<Empresa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if ( (selectedTipoEmpresa != null) && (selectedTipoEmpresa.length > 0) ) {
                filters.put("tipos", selectedTipoEmpresa);
            }
            filters.putAll(defautFilters);
            list = facade.listarEmpresas(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.listarEmpresasTotalCount(filters));
            return list;
        }
        
        public int getRowCountSinFiltrar() {
            return facade.listarEmpresasTotalCount(defautFilters);
        }
        
        @Override
	public Object getRowKey(Empresa empresa) {
            return empresa.getId();
	}

	@Override
	public Empresa getRowData(String rowKey) {
            
            for (Empresa empresa : list) {
                if (empresa.getId().equals(Integer.parseInt(rowKey))) {
                    System.out.println("Tipo => " + empresa.getRoles().size());
                    return empresa;
                }
            }
            return null;
        }        
    }
    
    public class BusquedaDiagonalEmpresaLazyDataModel extends LazyDataModel<Empresa> {
        private List<Empresa> list;
        private final Map<String, Object> filtrosBase;
        
        public BusquedaDiagonalEmpresaLazyDataModel(Map<String, Object> filters){            
            filtrosBase = filters;
            filtrosBase.putAll(defautFilters);
            if ( (selectedTipoEmpresa != null) && (selectedTipoEmpresa.length > 0) ) {
                filters.put("tipos", selectedTipoEmpresa);
            }
            this.setRowCount(facade.listarEmpresasTotalCount(filtrosBase));
            System.err.println("[z] constructor " + String.valueOf(getRowCount()));
        }
        
        public int getRowCountSinSubFiltro() {
            return facade.listarEmpresasTotalCount(filtrosBase);
        }
        
        @Override
        public List<Empresa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            filters.putAll(filtrosBase);
            list = facade.listarEmpresas(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.listarEmpresasTotalCount(filters));
            System.err.println("[z] load " + String.valueOf(getRowCount()));
            return list;
        }
        
        @Override
	public Object getRowKey(Empresa empresa) {
            return empresa.getId();
	}

	@Override
	public Empresa getRowData(String rowKey) {
            
            for (Empresa empresa : list) {
                if (empresa.getId().equals(Integer.parseInt(rowKey))) {
                    System.out.println("Tipo => " + empresa.getRoles().size());
                    return empresa;
                }
            }
            return null;
        }        
    }
    
    public ListadoEmpresasModel() {
    }
    
    public ListadoEmpresasModel(EmpresaServiceFacade facade) {
        this.facade = facade;
    }
    
    public EmpresaLazyDataModel getItems(){
        if (items == null) {
            items = new EmpresaLazyDataModel();
        }  
        return items;
    }
  
    public Empresa getSelected() {
        return selected;
    }

    public void setSelected(Empresa selected) {
        this.selected = selected;
        if (selected != null) System.out.println(selected);
        else System.out.println("null");
    }
    
    public String[] getSelectedTipoEmpresa() {
        return selectedTipoEmpresa;
    }
 
    public void setSelectedTipoEmpresa(String[] selectedTipoEmpresa) {
        this.selectedTipoEmpresa = selectedTipoEmpresa;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        
        if (!this.filtro.equals(filtro)) {
            this.filtro = filtro;
            itemsDiagonal = null;
            LOGGER.log(Level.INFO, "[z] setFiltro {0}", filtro);
        } else {
            LOGGER.log(Level.INFO, "[z] setFiltro MISMO {0}", filtro);
        }
        
    }

    public FiltroRolEmpresa getFiltroRolEmpresa() {
        return filtroRolEmpresa;
    }
    
    public String getFiltroRolEmpresaStr() {
        switch(filtroRolEmpresa) {
            case PROVEEDORES:
                return "PROVEEDOR";
            case POTENCIALES:
                return "POTENCIAL";
            case CLIENTES:
                return "CLIENTE";
        }
        return "EMPRESA";
    }

    public void setFiltroRolEmpresa(FiltroRolEmpresa filtroRolEmpresa) {
        this.filtroRolEmpresa = filtroRolEmpresa;     
        switch(filtroRolEmpresa) {
            case TODAS:
                break;
            case PROVEEDORES:
                defautFilters.put("tipo", "PROVEEDOR");
                break;
            case POTENCIALES:
                defautFilters.put("tipo", "POTENCIAL");
                break;
            case CLIENTES:
                defautFilters.put("tipo", "CLIENTE");
                break;
                
        }
    }
    
    public Map<String, BusquedaDiagonalEmpresaLazyDataModel> getItemsDiagonal() {
        if (((itemsDiagonal == null) && (filtro != null) && (!filtro.isEmpty()))) {
            LOGGER.log(Level.INFO, "[z] Creo itemsDiagonal");
            itemsDiagonal = new LinkedHashMap<>();
            for (Filtro filtroPosible : facade.getFiltrosPosibles()) {
                if (filtroPosible.getOperacion().equals(Operacion.CONTAINS)) {
                    LOGGER.log(Level.INFO, "getItemsDiagonal: Filtro: {0} con valor {1}", new Object[]{filtroPosible.toString(), filtro});
                    Map<String, Object> filters = new LinkedHashMap<>();
                    filters.put(filtroPosible.toString(), filtro);
                    itemsDiagonal.put(
                        filtroPosible.toString(), 
                        new BusquedaDiagonalEmpresaLazyDataModel(filters)
                    );
                }
            }
        }
        return itemsDiagonal;
    }

    public List<Filtro> getFiltrosPosibles() {
        return facade.getFiltrosPosibles();
    }
    
    public List<String> getItemsDiagonalKeys() {
        List<String> lista = new ArrayList<>();
        if ( (getItemsDiagonal() != null) && (!getItemsDiagonal().isEmpty()) ) {
            getItemsDiagonal().keySet().forEach(key -> {
                lista.add(key);
            });
        }
        return lista;
    }
    
    public Boolean getHayResultadosDiagonal() {
        if (itemsDiagonal != null) {
            for (Map.Entry<String, BusquedaDiagonalEmpresaLazyDataModel> entry : itemsDiagonal.entrySet()) {
                String key = entry.getKey();
                BusquedaDiagonalEmpresaLazyDataModel value = entry.getValue();
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
            itemsDiagonal = null;
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
    
    
}