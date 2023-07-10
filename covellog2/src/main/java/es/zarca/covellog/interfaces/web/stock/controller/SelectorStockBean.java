package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockMiniDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorAlmacenDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockPorLoteDto;
import es.zarca.covellog.interfaces.facade.stock.dto.StockSeleccionDto;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaFacade;
import es.zarca.covellog.interfaces.web.app.controller.BusquedaTransversalSelector;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("selectorStockBean")
@ViewScoped
public class SelectorStockBean extends BusquedaTransversalSelector<StockDto> implements Serializable {

    @Inject
    private StockFacade facade;
    private Boolean asignarNumSerie = true;
    private Boolean asignarLote = true;
    private PorLoteLazyDataModel itemsPorLote;
    private PorAlmacenLazyDataModel itemsPorAlmacen;
    private StockPorLoteDto selectedPorLote;
    private StockPorAlmacenDto selectedPorAlmacen;
    
    public class PorLoteLazyDataModel extends LazyDataModel<StockPorLoteDto> {
        private List<StockPorLoteDto> list;
        //private final Map<String, Object> filtrosBase;
        
        public PorLoteLazyDataModel() {
            this.setRowCount(facade.findPorLoteTotalCount(getDefaultFilters()));
        }
        
        @Override
        public List<StockPorLoteDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            filters.putAll(getDefaultFilters());
            list = facade.findPorLote(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findPorLoteTotalCount(filters));
            return list;
        }
        
        public int getRowCountSinFiltrar() {
            return facade.findPorLoteTotalCount(getDefaultFilters());
        }
        
        @Override
	public Object getRowKey(StockPorLoteDto item) {
            if (item == null) {
                return false;
            }
            return item.getId();
	}

	@Override
	public StockPorLoteDto getRowData(String rowKey) {
            for (StockPorLoteDto item : list) {
                if ( getRowKey(item).toString().equals(rowKey) ) {
                    return item;
                }
            }
            return null;
        }        
    }
    
    public class PorAlmacenLazyDataModel extends LazyDataModel<StockPorAlmacenDto> {
        private List<StockPorAlmacenDto> list;
        //private final Map<String, Object> filtrosBase;
        
        public PorAlmacenLazyDataModel() {
            this.setRowCount(facade.findPorAlmacenTotalCount(getDefaultFilters()));
        }
        
        @Override
        public List<StockPorAlmacenDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            filters.putAll(getDefaultFilters());
            list = facade.findPorAlmacen(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findPorAlmacenTotalCount(filters));
            return list;
        }
        
        public int getRowCountSinFiltrar() {
            return facade.findPorAlmacenTotalCount(getDefaultFilters());
        }
        
        @Override
	public Object getRowKey(StockPorAlmacenDto item) {
            if (item == null) {
                return false;
            }
            return item.getId();
	}

	@Override
	public StockPorAlmacenDto getRowData(String rowKey) {
            for (StockPorAlmacenDto item : list) {
                if ( getRowKey(item).toString().equals(rowKey)) {
                    return item;
                }
            }
            return null;
        }        
    }
    
    @Override
    public Object getItemRowKey(StockDto item) {
        if (item == null) {
            return false;
        }
        return item.getId();
    }

    @Override
    public boolean hasRowKey(StockDto item, String rowKey) {
        if ( (item == null) || (item.getId() == null) ) {
            return false;
        }
        return (item.getId().toString().equals(rowKey));
    }
    
    @Override
    public BusquedaFacade<StockDto> getFacade() {
        return facade;
    }      

    public Boolean getAsignarNumSerie() {
        return asignarNumSerie;
    }
    
    public void setAsignarNumSerie(Boolean asignarNumSerie) {
        this.asignarNumSerie = asignarNumSerie;
        if (asignarNumSerie) {
            asignarLote = true;
        }
    }

    public Boolean getAsignarLote() {
        return asignarLote;
    }
    
    public void setAsignarLote(Boolean asignarLote) {
        this.asignarLote = asignarLote;
    }
    
    public StockMiniDto getSelectedMini() {
        if (getSelected() == null) {
            return null;
        }
        StockMiniDto dto = new StockMiniDto();
        dto.setId(getSelected().getId());
        dto.setNumSerie(getSelected().getNumSerie());
        dto.setTipoProducto(getSelected().getTipoProducto());
        dto.setLote(getSelected().getLote());
        dto.setFlota(getSelected().getFlota());
        dto.setEstado(getSelected().getEstado());
        dto.setCondicion(getSelected().getCondicion());
        dto.setAlbaranEntregaFriendlyId(getSelected().getAlbaranEntregaFriendlyId());
        dto.setAlbaranRecogidaFriendlyId(getSelected().getAlbaranRecogidaFriendlyId());
        return dto;
    }

    public StockSeleccionDto getSelectedStock() {
        StockSeleccionDto dto = new StockSeleccionDto();
        if (asignarNumSerie) {
            dto.setId(getSelected().getId());
            dto.setUbicacion(getSelected().getUbicacion());
            dto.setTipoProducto(getSelected().getTipoProducto());
            dto.setNumSerie(getSelected().getNumSerie());
            dto.setLote(getSelected().getLote());
        } else if (asignarLote) {
            dto.setId(null);
            dto.setUbicacion(getSelectedPorLote().getUbicacion());
            dto.setTipoProducto(getSelectedPorLote().getTipoProducto());
            dto.setNumSerie(null);
            dto.setLote(getSelectedPorLote().getLote());
        } else {
            dto.setId(null);
            dto.setUbicacion(getSelectedPorAlmacen().getUbicacion());
            dto.setTipoProducto(getSelectedPorAlmacen().getTipoProducto());
            dto.setNumSerie(null);
            dto.setLote(null);
        }
        return dto;
    }

    public StockPorLoteDto getSelectedPorLote() {
        return selectedPorLote;
    }

    public void setSelectedPorLote(StockPorLoteDto selectedPorLote) {
        this.selectedPorLote = selectedPorLote;
    }

    public StockPorAlmacenDto getSelectedPorAlmacen() {
        return selectedPorAlmacen;
    }

    public void setSelectedPorAlmacen(StockPorAlmacenDto selectedPorAlmacen) {
        this.selectedPorAlmacen = selectedPorAlmacen;
    }

    public void onChangeAsignarNumSerieSwitch() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //asignarNumSerie = false;
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }

    public void onChangeAsignarLoteSwitch() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //asignarNumSerie = false;
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public PorLoteLazyDataModel getItemsPorLote() {
        if (itemsPorLote == null) {
            itemsPorLote = new PorLoteLazyDataModel();
        }
        return itemsPorLote;
    }
    
    public PorAlmacenLazyDataModel getItemsPorAlmacen() {
        if (itemsPorAlmacen == null) {
            itemsPorAlmacen = new PorAlmacenLazyDataModel();
        }
        return itemsPorAlmacen;
    }
    
    @Override
    public boolean getBotonSeleccionarDisabled() {
        if (asignarNumSerie) {
            return super.getBotonSeleccionarDisabled();
        } else if (asignarLote) {
            return (selectedPorLote == null);
        } else {
            return (selectedPorAlmacen == null);
        }
    }
        
}