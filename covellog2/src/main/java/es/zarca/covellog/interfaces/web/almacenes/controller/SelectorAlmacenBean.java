package es.zarca.covellog.interfaces.web.almacenes.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.almacen.AlmacenesFacade;
import es.zarca.covellog.interfaces.facade.almacen.dto.AlmacenDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.helpers.OrdreAssembler;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
/**
 *
 * @author francisco
 */
@Named("selectorAlmacenBean")
@ViewScoped
public class SelectorAlmacenBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorAlmacenBean.class.getName());
    @Inject
    private AlmacenesFacade facade;
    @Inject
    private EditarAlmacenBean editarAlmacenBean;
    private AlmacenDto selected;
    private AlmacenDtoLazyDataModel items;
    private boolean mostrarBajas = false;
    private boolean mostrarEditor = false;

    public class AlmacenDtoLazyDataModel extends LazyDataModel<AlmacenDto> {
        private List<AlmacenDto> list;
        
        public AlmacenDtoLazyDataModel(){
            this.setRowCount(facade.findTotalCount());
        }
        
        @Override
        public List<AlmacenDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if (!mostrarBajas) {
                filters.put("fechaBajaIsNull", "");
            }
                
            list = facade.find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(facade.findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(AlmacenDto dto) {
            return dto.getId();
	}

	@Override
	public AlmacenDto getRowData(String rowKey) {
            for (AlmacenDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) return dto;
            }
            return null;
        }
    }
    
    public SelectorAlmacenBean() {    
    }

    public AlmacenDto getSelected() {
        return selected;
    }

    public void setSelected(AlmacenDto selected) {
        this.selected = selected;
    }

    public AlmacenDtoLazyDataModel getItems(){
        if (items == null) {
            items = new AlmacenDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(AlmacenDtoLazyDataModel items) {
        this.items = items;
    }
    
    public void baja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = facade.baja(selected.getId());
            JsfUtil.addSuccessMessage("Almacen dado de baja correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void anularBaja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = facade.anularBaja(selected.getId());
            JsfUtil.addSuccessMessage("Almacen recuperado correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareNuevo() {
        /*FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            editarAlmacenBean.prepareNuevo();
            mostrarEditor =  true;
            editarAlmacenBean.setGuardarListener( (event) -> {
                items = null;
                PrimeFaces.current().ajax().update("gestion-almacenes");
            });
            editarAlmacenBean.setCancelarListener( (event) -> {
                mostrarEditor = false;
                PrimeFaces.current().ajax().update("gestion-almacenes");
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }*/
        FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/almacenes/nuevo?faces-redirect=true&id=" + selected.getId());
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            /*
            editarAlmacenBean.prepareModificar(selected.getId());
            mostrarEditor =  true;
            editarAlmacenBean.setGuardarListener( (event) -> {
                items = null;
                PrimeFaces.current().ajax().update("gestion-almacenes");
            });
            editarAlmacenBean.setCancelarListener( (event) -> {
                mostrarEditor = false;
                PrimeFaces.current().ajax().update("gestion-almacenes");
            });
            */
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getNavigationHandler().handleNavigation(context, null, "/almacenes/modificar?faces-redirect=true&id=" + selected.getId());
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

    public boolean isMostrarEditor() {
        return mostrarEditor;
    }
    
}