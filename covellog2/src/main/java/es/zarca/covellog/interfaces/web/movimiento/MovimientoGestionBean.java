package es.zarca.covellog.interfaces.web.movimiento;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.MovimientoFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.MovimientoDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("movimientoGestionBean")
@ViewScoped
public class MovimientoGestionBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MovimientoGestionBean.class.getName());
    @Inject
    private MovimientoFacade facade;
    @Inject 
    private MovimientoModificarBean movimientoModificarBean;
    private List<MovimientoDto> selecteds = new ArrayList();
    private MovimientoDtoLazyDataModel items;
    private boolean mostrarAnulados = false;
    
    public class MovimientoDtoLazyDataModel extends LazyDataModel<MovimientoDto> {
        private List<MovimientoDto> list;
        
        public MovimientoDtoLazyDataModel(){
            this.setRowCount(facade.findTotalCount());
        }
        
        @Override
        public List<MovimientoDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if (!mostrarAnulados) {
                filters.put("ocultarAnulados", "");
            }
            list = facade.find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(MovimientoDto dto) {
            return dto.getId();
	}

	@Override
	public MovimientoDto getRowData(String rowKey) {
            for (MovimientoDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) return dto;
            }
            return null;
        }
    }

    public MovimientoDto getSelected() {
        if ((selecteds != null) && (selecteds.size() > 0)) {
            return selecteds.get(0);
        }
        return null;
    }

    public List<MovimientoDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<MovimientoDto> selecteds) {
        this.selecteds = selecteds;
    }
    
    public MovimientoDtoLazyDataModel getItems(){
        if (items == null) {
            items = new MovimientoDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(MovimientoDtoLazyDataModel items) {
        this.items = items;
    }

    public boolean isMostrarAnulados() {
        return mostrarAnulados;
    }
    
    public void invertirMostrarAnulados() {
        mostrarAnulados = !mostrarAnulados;
    }

    public void setMostrarAnulados(boolean mostrarAnulados) {
        this.mostrarAnulados = mostrarAnulados;
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/movimientos/modificar?faces-redirect=true&id=" + selecteds.get(0).getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER); 
        try {
            ArgumentValidator.isNotNull(getSelected(), "Seleccione algun movimiento.");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/movimientos/modificar?faces-redirect=true&id=" + getSelected().getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}