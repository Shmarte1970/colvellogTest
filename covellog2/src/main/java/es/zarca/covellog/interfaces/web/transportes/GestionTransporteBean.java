package es.zarca.covellog.interfaces.web.transportes;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.TiposProductosServiceFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import es.zarca.covellog.interfaces.facade.transporte.TransportesFacade;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
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
@Named("gestionTransporteBean")
@ViewScoped
public class GestionTransporteBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestionTransporteBean.class.getName());
    @Inject
    private TransportesFacade facade;
    @Inject
    private ModificarTransporteBean modificarTransporteBean;
    private TransporteDto selected;
    private TransporteDtoLazyDataModel items;
    private boolean mostrarBajas = false;
    
    public class TransporteDtoLazyDataModel extends LazyDataModel<TransporteDto> {
        private List<TransporteDto> list;
        
        public TransporteDtoLazyDataModel(){
            this.setRowCount(getFacade().findTotalCount());
        }
        
        @Override
        public List<TransporteDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if (!mostrarBajas) {
                filters.put("fechaBajaIsNull", "");
            }
                
            list = getFacade().find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(TransporteDto dto) {
            return dto.getId();
	}

	@Override
	public TransporteDto getRowData(String rowKey) {
            for (TransporteDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) return dto;
            }
            return null;
        }
    }
    
    public GestionTransporteBean() {
    }
    
    private TransportesFacade getFacade() {
        return facade;
    }

    public TransporteDto getSelected() {
        return selected;
    }

    public void setSelected(TransporteDto selected) {
        this.selected = selected;
    }

    public TransporteDtoLazyDataModel getItems(){
        if (items == null) {
            items = new TransporteDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(TransporteDtoLazyDataModel items) {
        this.items = items;
    }
    
    public void baja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = getFacade().baja(selected.getId());
            JsfUtil.addSuccessMessage("Transporte dado de baja correctamente.");
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
            JsfUtil.addSuccessMessage("Transporte dado recuperada correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
       
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            modificarTransporteBean.prepareNuevo();
            modificarTransporteBean.setListener( (event) -> {                
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
            modificarTransporteBean.prepareModificar(selected.getId());
            modificarTransporteBean.setListener( (event) -> {                
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