package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.model.DireccionEnvioEditModel;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.model.EmpresaDireccionesEnvioModel;
import es.zarca.covellog.interfaces.web.empresas.exception.EmpresaNotExistsPresentationException;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("empresaDireccionesEnvioController")
@ViewScoped
public class EmpresaDireccionesEnvioController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EmpresaDireccionesEnvioController.class.getName());

    @Inject
    private EmpresaServiceFacade facade; 
    @Inject
    private DireccionEnvioEditController direccionEnvioEditController;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    private EmpresaDireccionesEnvioModel model = new EmpresaDireccionesEnvioModel();
    
    public EmpresaDireccionesEnvioController() {
    }

    public EmpresaDireccionesEnvioModel getModel() {
        return model;
    }

    public void setModel(EmpresaDireccionesEnvioModel model) {
        this.model = model;
    }
    
    public void prepareNew() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            DireccionEnvioEditModel direccionEnvioEditModel = new DireccionEnvioEditModel();
            direccionEnvioEditModel.setEmpresaId(model.getEmpresaId());
            direccionEnvioEditModel.setDireccionEnvio(new DireccionEnvioDto());
            direccionEnvioEditModel.setContactosPosibles(model.getContactosPosibles());
            direccionEnvioEditModel.setUpdate("@(.EmpresaDireccionesEnvio)");
            direccionEnvioEditModel.setListener((ActionEvent event) -> {
                update(event);
            });
            direccionEnvioEditController.setModel(direccionEnvioEditModel);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdate(DireccionEnvioDto direccionEnvio) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            LOGGER.log(Level.INFO, "Seleccionado direccion de envio para modificar: {0}: {1}", new Object[]{direccionEnvio.getId(), direccionEnvio.getNombre()});
            DireccionEnvioEditModel direccionEnvioEditModel = new DireccionEnvioEditModel();
            direccionEnvioEditModel.setEmpresaId(model.getEmpresaId());
            direccionEnvioEditModel.setDireccionEnvio(direccionEnvio);
            direccionEnvioEditModel.setContactosPosibles(model.getContactosPosibles());
            direccionEnvioEditModel.setUpdate("@(.EmpresaDireccionesEnvio)");
            direccionEnvioEditModel.setListener((ActionEvent event) -> {
                update(event);
            });
            direccionEnvioEditModel.setCancelarListener((ActionEvent event) -> {
                recargar();
            });
            direccionEnvioEditController.setModel(direccionEnvioEditModel);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void update(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (direccionEnvioEditController.getModel().getUbicacion().getId() == null) {
                JsfUtil.addSuccessMessage("growl", "anadirDireccionEnvio.");
                facade.anadirDireccionEnvio(model.getEmpresaId(), direccionEnvioEditController.getModel().getUbicacion());
            } else {
                JsfUtil.addSuccessMessage("growl", "modificarDireccionEnvio.");
                facade.modificarDireccionEnvio(model.getEmpresaId(), direccionEnvioEditController.getModel().getUbicacion());
            }
            cargar(model.getEmpresaId());
            JsfUtil.addSuccessMessage("growl", "Direcció de envío guardada correctamente.");
        } catch (Exception ex) {
            JsfUtil.validationFailed();
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void recargar() {
        cargar(model.getEmpresaId());
    }
    
    public void eliminar(DireccionEnvioDto direccionEnvio) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.eliminarDireccionEnvio(model.getEmpresaId(), direccionEnvio.getId());
            cargar(model.getEmpresaId());
            JsfUtil.addSuccessMessage("growl", "Direcció de envío eliminada correctamente.");
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
            if (empresa == null) {
                throw new PresentationException("La empresa no puede ser null");
            }           
            model.setEmpresaId(empresa.getId());
            if (empresa.getDireccionesEnvio() != null) {
                model.setDireccionesEnvio(empresa.getDireccionesEnvio());
            }
            if (empresa.getContactos() != null) {
                model.setContactosPosibles(empresa.getContactos());
            }            
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
}