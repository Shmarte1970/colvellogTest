package es.zarca.covellog.interfaces.web.productos.familias.controller;

import es.zarca.covellog.application.productos.familias.form.ModificarFamiliaProductoForm;
import es.zarca.covellog.application.productos.familias.form.NuevaFamiliaProductoForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.stock.FamiliasProductosServiceFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.FamiliaProductoDto;
import es.zarca.covellog.interfaces.facade.stock.internal.DefaultFamiliasProductosServiceFacade;
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
@Named("gestionFamiliaProductoBean")
@ViewScoped
public class GestionFamiliaProductoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(GestionFamiliaProductoBean.class.getName());
    @Inject
    private DefaultFamiliasProductosServiceFacade facade;
    private FamiliaProductoDto selected;
    private FamiliaProductoDtoLazyDataModel items;
    private boolean mostrarBajas = false;
    private NuevaFamiliaProductoForm createForm = null;
    private ModificarFamiliaProductoForm updateForm = null;
    
    public class FamiliaProductoDtoLazyDataModel extends LazyDataModel<FamiliaProductoDto> {
        private List<FamiliaProductoDto> list;
        
        public FamiliaProductoDtoLazyDataModel(){
            this.setRowCount(getFacade().findAllTotalCount());
        }
        
        @Override
        public List<FamiliaProductoDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            if (!mostrarBajas) {
                filters.put("fechaBajaIsNull", "");
            }
                
            list = getFacade().find(first, pageSize, OrdreAssembler.fromSortOrder(sortField, sortOrder), filters);
            this.setRowCount(getFacade().findTotalCount(filters));
            return list;
        }
        
        @Override
	public Object getRowKey(FamiliaProductoDto dto) {
            return dto.getId();
	}

	@Override
	public FamiliaProductoDto getRowData(String rowKey) {
            for (FamiliaProductoDto dto : list) {
                if (dto.getId().equals(Integer.parseInt(rowKey))) return dto;
            }
            return null;
        }
    }
    
    public GestionFamiliaProductoBean() {
        
    }
    
    private FamiliasProductosServiceFacade getFacade() {
        return facade;
    }

    public FamiliaProductoDto getSelected() {
        return selected;
    }

    public void setSelected(FamiliaProductoDto selected) {;
        this.selected = selected;
    }

    public FamiliaProductoDtoLazyDataModel getItems(){
        if (items == null) {
            items = new FamiliaProductoDtoLazyDataModel();
        }  
        return items;
    }

    public void setItems(FamiliaProductoDtoLazyDataModel items) {
        this.items = items;
    }
    
    public void baja() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selected = getFacade().baja(selected.getId());
            JsfUtil.addSuccessMessage("Familia producto dada de baja correctamente.");
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
            JsfUtil.addSuccessMessage("Familia producto recuperada correctamente.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            createForm = new NuevaFamiliaProductoForm();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void nuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (createForm != null) {
                selected = getFacade().nuevo(createForm);
                JsfUtil.addSuccessMessage("Familia producto creada correctamente.");
                if (!JsfUtil.isValidationFailed()) {
                    items = null;    // Invalidate list of items to trigger re-query.
                }
            } else {
                JsfUtil.addErrorMessage("Se ha perdido su formulario.");
            }
        } catch (Exception ex) {
            JsfUtil.validationFailed();
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            updateForm = new ModificarFamiliaProductoForm();
            updateForm.setNombre(selected.getNombre());
            updateForm.setObservaciones(selected.getObservaciones());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void cancelNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 
    
    public void modificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            selected = facade.modificar(selected.getId(), updateForm);
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
            JsfUtil.addSuccessMessage("Familia producto modificada correctamente.");
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
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

    public NuevaFamiliaProductoForm getCreateForm() {
        return createForm;
    }

    public void setCreateForm(NuevaFamiliaProductoForm createForm) {
        this.createForm = createForm;
    }

    public ModificarFamiliaProductoForm getUpdateForm() {
        return updateForm;
    }

    public void setUpdateForm(ModificarFamiliaProductoForm updateForm) {
        this.updateForm = updateForm;
    }
    
}