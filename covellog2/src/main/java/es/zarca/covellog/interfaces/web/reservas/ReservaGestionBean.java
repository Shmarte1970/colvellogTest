package es.zarca.covellog.interfaces.web.reservas;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.ReservaFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.reservas.ReservaDto;
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
@Named("reservaGestionBean")
@ViewScoped
public class ReservaGestionBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ReservaGestionBean.class.getName());
    @Inject
    private ReservaFacade facade;
    @Inject 
    private ReservaModificarBean reservaModificarBean;
    private List<ReservaDto> selecteds = new ArrayList();
    private ReservaDtoLazyDataModel items;
    private boolean mostrarAnulados = false;
    
    public class ReservaDtoLazyDataModel extends LazyDataModel<ReservaDto> {
        private List<ReservaDto> list;
        
        public ReservaDtoLazyDataModel(){
            this.setRowCount(facade.findTotalCount());
        }
        
        @Override
        public List<ReservaDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if (!mostrarAnulados) {
                filters.put("ocultarAnulados", "");
            }
            list = facade.find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(ReservaDto dto) {
            return dto.getId();
	}

	@Override
	public ReservaDto getRowData(String rowKey) {
            for (ReservaDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) return dto;
            }
            return null;
        }
    }

    public ReservaDto getSelected() {
        if ((selecteds != null) && (selecteds.size() > 0)) {
            return selecteds.get(0);
        }
        return null;
    }

    public List<ReservaDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ReservaDto> selecteds) {
        this.selecteds = selecteds;
    }
    
    public ReservaDtoLazyDataModel getItems(){
        if (items == null) {
            items = new ReservaDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(ReservaDtoLazyDataModel items) {
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
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/reservas/modificar?faces-redirect=true&id=" + selecteds.get(0).getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER); 
        try {
            ArgumentValidator.isNotNull(getSelected(), "Seleccione algun reserva.");
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/reservas/modificar?faces-redirect=true&id=" + getSelected().getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}