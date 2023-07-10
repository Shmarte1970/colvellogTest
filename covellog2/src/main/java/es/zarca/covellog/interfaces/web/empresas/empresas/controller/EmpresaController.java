package es.zarca.covellog.interfaces.web.empresas.empresas.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionEditController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.empresas.empresas.model.EmpresaModel;
import es.zarca.covellog.interfaces.web.empresas.exception.EmpresaNotExistsPresentationException;
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
@Named("empresaController")
@ViewScoped
public class EmpresaController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(EmpresaController.class.getName());

    @Inject 
    private EmpresaServiceFacade facade;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    @Inject
    private DireccionEditController direccionEditController;
    
    private EmpresaModel model;
    
    public EmpresaController() {
        model = new EmpresaModel();
    }

    public EmpresaModel getModel() {
        return model;
    }

    public void setModel(EmpresaModel model) {
        this.model = model;
    }
    
    public void comprobarCif() {
        model.getEmpresa().setEsCorrectoCif(facade.esCorrectoCif(model.getEmpresa().getCif()));
    }
    
    public void prepareUpdateDireccionFiscal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            direccionEditController.setDireccion(model.getEmpresa().getDireccionFiscal());
            direccionEditController.setListener((ActionEvent event1) -> {
                updateDireccionFiscal(event1);
            });
            direccionEditController.setUpdate("@(.Empresa-DireccionFiscal)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
        
    public void updateDireccionFiscal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getEmpresa().setDireccionFiscal(direccionEditController.getDireccion());
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
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            model.setEmpresa(empresa);
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void crearPotencial() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            modificarEmpresaController.actualizarEmpresa(model.getEmpresa());
            JsfUtil.addSuccessMessage("growl", "Empresa modificada.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }      
    
    public void crear() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            EmpresaDto empresa = model.getEmpresa();
            model.setEmpresa(
                facade.altaEmpresa(
                    empresa.getCif(),
                    empresa.getNombre(),
                    empresa.getAlias(),
                    empresa.getHorario(),
                    empresa.getIdioma().getId(),
                    empresa.getDireccionFiscal(),
                    empresa.getWeb(),
                    empresa.getPalabrasClave()
                )
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.EMPRESA, model.getEmpresa());
            JsfUtil.addSuccessMessage("growl", "Empresa creada.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
            
    public void cancelCreate() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.LISTADO);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void modificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            EmpresaDto empresa = model.getEmpresa();
            model.setEmpresa(
                facade.modificarInfoEmpresa(
                    empresa.getId(),
                    empresa.getCif(),
                    empresa.getNombre(),
                    empresa.getAlias(),
                    empresa.getHorario(),
                    empresa.getIdioma().getId(),
                    empresa.getDireccionFiscal(),
                    empresa.getWeb(),
                    empresa.getPalabrasClave()
                )
            );
            JsfUtil.addSuccessMessage("growl", "Empresa modificada.");
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.EMPRESA, model.getEmpresa());
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
   
}