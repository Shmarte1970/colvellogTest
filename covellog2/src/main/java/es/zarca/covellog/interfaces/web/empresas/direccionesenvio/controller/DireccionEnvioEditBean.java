package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionEditController;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("direccionEnvioEditBean")
@ViewScoped
public class DireccionEnvioEditBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DireccionEnvioEditBean.class.getName());
    
    @Inject
    private DireccionEditController direccionEditController;
    @Inject 
    private SelectorListaContactosController selectorListaContactosController;
    @Inject 
    
    private EmpresaServiceFacade facade;
    private Integer empresaId;
    private Integer direccionEnvioId;
    
    private DireccionEnvioDto direccionEnvio;    

    public DireccionEnvioDto getDireccionEnvio() {
        if (direccionEnvio == null) {
            direccionEnvio = facade.findDireccionEnvio(empresaId, direccionEnvioId);
        }
        return direccionEnvio;
    }
    
    @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (direccionEnvio.getId() != null) {
                facade.modificarDireccionEnvio(empresaId, direccionEnvio);
                JsfUtil.addSuccessMessage("growl", "Dirección de envío modificada correctamente.");
            } else {
                facade.anadirDireccionEnvio(empresaId, direccionEnvio);
                JsfUtil.addSuccessMessage("growl", "Dirección de envío creada correctamente.");
            }
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(Integer empresaId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            this.empresaId = empresaId;
            this.direccionEnvioId = null;
            direccionEnvio = new DireccionEnvioDto();
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(Integer empresaId, Integer direccionEnvioId) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            this.empresaId = empresaId;
            this.direccionEnvioId = direccionEnvioId;
            direccionEnvio = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionEditController.setDireccion(direccionEnvio.getDireccion());
            direccionEditController.setListener((ActionEvent event1) -> {
                updateDireccion(event1);
            });
            direccionEditController.setUpdate("@(.DireccionesEnvio-DireccionEnvioPopup-Direccion)");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
        
    public void updateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionEnvio.setDireccion(direccionEditController.getDireccion());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void copiarDireccionPrincipal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            DireccionDto direccion = facade.findDireccionFiscalEmpresa(empresaId);
            if (direccion == null) {
                JsfUtil.addErrorMessage("La direccion principal esta vacia.");
            } 
            direccionEnvio.setDireccion(direccion);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorListaContactosController.setEmpresaId(empresaId);
            selectorListaContactosController.setSelecteds(null);
            //selectorListaContactosController.setContactosPosibles(contactosPosibles);
            selectorListaContactosController.setContactos(direccionEnvio.getContactos());
            selectorListaContactosController.setUpdate("@(.DireccionEnvio-Contactos)");
            selectorListaContactosController.setUpdateId("DireccionEnvio-ContactosPopup");
            selectorListaContactosController.setOnGuardarListener((ActionEvent event1) -> {
                updateContactos();
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateContactos() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionEnvio.setContactos(selectorListaContactosController.getContactos()); 
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}