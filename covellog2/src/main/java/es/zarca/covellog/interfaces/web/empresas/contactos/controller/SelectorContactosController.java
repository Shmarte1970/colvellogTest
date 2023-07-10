package es.zarca.covellog.interfaces.web.empresas.contactos.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.empresas.contactos.model.SelectorContactosModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author francisco
 */
@Named("selectorContactosController")
@ViewScoped
public class SelectorContactosController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorContactosController.class.getName());
    @Inject
    private ContactoEditController contactoEditController;
    @Inject
    private SelectorContactosController selectorContactosController;
    @Inject
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    private EmpresaServiceFacade facade;
    private SelectorContactosModel model = new SelectorContactosModel();

    public SelectorContactosModel getModel() {
        return model;
    }

    public void setModel(SelectorContactosModel model) {
        this.model = model;
    }
    
    public ContactoDto getContacto(Integer id) {
        for (ContactoDto item : model.getItems()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public void prepareNuevoContacto() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contactoEditController.prepareNew(model.getEmpresaId());
            contactoEditController.setUpdate("@(.ContactosSelector-ContactosPopup)");
            contactoEditController.setListener((ActionEvent event) -> {
                nuevoContacto();
            });
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void nuevoContacto() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.anadirContacto(model.getEmpresaId(), contactoEditController.getContacto());
            EmpresaDto empresa = facade.buscarPorIdDto(model.getEmpresaId());
            selectorListaContactosController.setContactosPosibles(empresa.getContactos());
            selectorListaContactosController.prepareAnadir();
            if (empresa.getContactos().size() == 1) {
                JsfUtil.addSuccessMessage("growl", empresa.getContactos().get(0).getNombre());
                selectorContactosController.getModel().setSelected(empresa.getContactos().get(0));
                JsfUtil.addSuccessMessage("growl", selectorContactosController.getModel().getSelected().getNombre());
                PrimeFaces.current().executeScript("selectFirst()");
            }
            
            JsfUtil.addSuccessMessage("growl", "El contacto se ha creado correctamente.");
        } catch (Exception e) {
            
            JsfUtil.validationFailed();JsfUtil.showErrorDialog(e.getMessage());
        } finally {
            log.finish();
        }       
    }
    
    public void onSelect() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model.getListener() != null) {
                LOGGER.log(Level.SEVERE, "Proceso LISTENER");
                model.getListener().processAction(null);
            } else {
                LOGGER.log(Level.SEVERE, "No hay LISTENER");
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }        
    }

}