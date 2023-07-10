package es.zarca.covellog.interfaces.web.productos.tipos.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.TiposProductosServiceFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.TipoProductoDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("gestionTipoProductoBean")
@ViewScoped
public class GestionTipoProductoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestionTipoProductoBean.class.getName());
    @Inject
    private TiposProductosServiceFacade facade;
    @Inject
    private EditarTipoProductoBean editarTipoProductoBean;
    private TipoProductoDto selected;
    private TipoProductoDtoLazyDataModel items;
    private boolean mostrarBajas = false;
    
    public class TipoProductoDtoLazyDataModel extends LazyDataModel<TipoProductoDto> {
        private List<TipoProductoDto> list;
        
        public TipoProductoDtoLazyDataModel(){
            this.setRowCount(getFacade().findTotalCount());
        }
        
        @Override
        public List<TipoProductoDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if (!mostrarBajas) {
                filters.put("fechaBajaIsNull", "");
            }
                
            list = getFacade().find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(TipoProductoDto dto) {
            return dto.getId();
	}

	@Override
	public TipoProductoDto getRowData(String rowKey) {
            for (TipoProductoDto dto : list) {
                if (dto.getId().equals(rowKey)) return dto;
            }
            return null;
        }
    }
    
    public GestionTipoProductoBean() {
        
    }
    
    private TiposProductosServiceFacade getFacade() {
        return facade;
    }

    public TipoProductoDto getSelected() {
        return selected;
    }

    public void setSelected(TipoProductoDto selected) {
        this.selected = selected;
    }

    public TipoProductoDtoLazyDataModel getItems(){
        if (items == null) {
            items = new TipoProductoDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(TipoProductoDtoLazyDataModel items) {
        this.items = items;
    }
    
    public void baja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = getFacade().baja(selected.getId());
            JsfUtil.addSuccessMessage("Tipo producto dada de baja correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void anularBaja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = getFacade().anularBaja(selected.getId());
            JsfUtil.addSuccessMessage("Tipo producto recuperada correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
       
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            editarTipoProductoBean.prepareNuevo();
            editarTipoProductoBean.setListener( (event) -> {                
                items = null;
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            editarTipoProductoBean.prepareModificar(selected.getId());
            editarTipoProductoBean.setListener( (event) -> {                
                items = null;
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public boolean isMostrarBajas() {
        return mostrarBajas;
    }

    public void invertirMostrarBajas() {
        this.mostrarBajas = !this.mostrarBajas;
        items = null;  
    }

}