package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionEditController;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.model.DireccionEnvioEditModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("direccionEnvioEditController")
@ViewScoped
public class DireccionEnvioEditController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DireccionEnvioEditController.class.getName());
    @Inject
    private DireccionEditController direccionEditController;
    @Inject 
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    private DireccionEnvioEditModel model;
    private DireccionDto direccionTemporal;
    
    public DireccionEnvioEditModel getModel() {
        return model;
    }

    public void setModel(DireccionEnvioEditModel model) {
        this.model = model;
    }
    
    public void prepareUpdateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionTemporal = model.getUbicacion().getDireccion();
            direccionEditController.setDireccion(model.getUbicacion().getDireccion());
            direccionEditController.setListener((ActionEvent event1) -> {
                updateDireccion(event1);
            });
            direccionEditController.setUpdate("@(.DireccionesEnvio-DireccionEnvioPopup-Direccion)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
        
    public void updateDireccion(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getUbicacion().setDireccion(direccionEditController.getDireccion());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void copiarDireccionPrincipal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            DireccionDto direccion = modificarEmpresaController.getModel().getEmpresa().getDireccionFiscal();
            if (direccion == null) {
                JsfUtil.addErrorMessage("La direccion principal esta vacia.");
            } 
            model.getUbicacion().setDireccion(direccion);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorListaContactosController.setEmpresaId(model.getEmpresaId());
            selectorListaContactosController.setContactosPosibles(model.getContactosPosibles());
            selectorListaContactosController.setContactos(model.getUbicacion().getContactos());
            selectorListaContactosController.setUpdate("@(.DireccionEnvio-Contactos)");
            selectorListaContactosController.setUpdateId("DireccionEnvio-ContactosPopup");
            selectorListaContactosController.setOnGuardarListener((ActionEvent event1) -> {
                updateContactos();
            });
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateContactos() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getUbicacion().setContactos(selectorListaContactosController.getContactos()); 
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            System.out.println(ex.getMessage());
        } finally {
            log.finish();
        }
    }
    
    public void aceptar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model.getListener() != null) {
                model.getListener().processAction(event);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model.getCancelarListener() != null) {
                model.getCancelarListener().processAction(event);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
}

