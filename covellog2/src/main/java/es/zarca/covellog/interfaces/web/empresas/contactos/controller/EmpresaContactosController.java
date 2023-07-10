package es.zarca.covellog.interfaces.web.empresas.contactos.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.empresas.contactos.model.EmpresaContactosModel;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.empresas.exception.EmpresaNotExistsPresentationException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("empresaContactosController")
@ViewScoped
public class EmpresaContactosController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EmpresaContactosController.class.getName());

    @Inject
    private EmpresaServiceFacade facade;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    @Inject
    private ContactoEditController contactoEditController; 
    private EmpresaContactosModel model = new EmpresaContactosModel();
   
    
    public EmpresaContactosController() {
    }

    public EmpresaContactosModel getModel() {
        return model;
    }

    public void setModel(EmpresaContactosModel model) {
        this.model = model;
    }
    
    public void prepararNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //model.setContacto(new ContactoDto());
            contactoEditController.prepareNew(model.getEmpresaId());
            contactoEditController.setUpdate("@(.EmpresaContactos)");
            contactoEditController.setListener((ActionEvent event) -> {
                modificar();
            });
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
   /* public void cancelarPrepararNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.setContacto(null);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }*/
    
    public void prepararModificar(ContactoDto contacto) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            //model.setContacto(contacto);
            contactoEditController.prepareEdit(model.getEmpresaId(), contacto);
            contactoEditController.setUpdate("@(.EmpresaContactos)");
            contactoEditController.setListener((ActionEvent event) -> {
                modificar();
            });
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void modificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (contactoEditController.getContacto().getId() == null) {
                System.err.println("AACOJONES ************************************** : " + contactoEditController.getContacto().getApellidos());
                facade.anadirContacto(model.getEmpresaId(), contactoEditController.getContacto());
            } else {
                System.err.println("BBCOJONES ************************************** : " + contactoEditController.getContacto().getApellidos());
                facade.modificarContacto(model.getEmpresaId(), contactoEditController.getContacto());
            }
            cargar(model.getEmpresaId());
            JsfUtil.addSuccessMessage("growl", "El contacto se ha modificado correctamente.");
        } catch (Exception ex) {
            //JsfUtil.addException(ex);
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminar(Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.eliminarContacto(model.getEmpresaId(), contactoId);
            cargar(model.getEmpresaId());
            JsfUtil.addSuccessMessage("growl", "Eliminar Contacto", "Contacto eliminado correctamente.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void subir(Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.subirContacto(model.getEmpresaId(), contactoId);
            cargar(model.getEmpresaId());
            JsfUtil.addSuccessMessage("growl", "Prioridad contacto", "Prioridad contacto subida correctamente.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void bajar(Integer contactoId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.bajarContacto(model.getEmpresaId(), contactoId);
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CONTACTOS);
            JsfUtil.addSuccessMessage("growl", "Prioridad contacto", "Prioridad contacto bajada correctamente.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cargar(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            EmpresaDto empresa = facade.buscarPorIdDto(empresaId);
            if (empresa == null) {
                throw new EmpresaNotExistsPresentationException(empresaId);
            }
            actualizarEmpresa(empresa);
            modificarEmpresaController.actualizarEmpresa(empresa);
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            model.setEmpresaId(empresa.getId());
            if (empresa.getContactos() != null) {
                model.setContactos(empresa.getContactos());
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
      
}