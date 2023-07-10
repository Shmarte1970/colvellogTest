package es.zarca.covellog.interfaces.web.empresas.potencial.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.PotencialDto;
import es.zarca.covellog.interfaces.web.comerciales.controller.SelectorComercialesController;
import es.zarca.covellog.interfaces.web.comerciales.model.SelectorComercialesModel;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.potencial.model.PotencialModel;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.empresas.potencial.exception.EmpresaNoTieneRolPotencialPresentationException;
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
@Named("potencialController")
@ViewScoped
public class PotencialController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(PotencialController.class.getName());

    @Inject 
    private EmpresaServiceFacade facade;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    @Inject
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    private SelectorComercialesController selectorComercialesController;
    private PotencialModel model = new PotencialModel();
   
    
    public PotencialController() {
    }

    public PotencialModel getModel() {
        return model;
    }

    public void setModel(PotencialModel model) {
        this.model = model;
    }
    
    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((empresa == null) || (empresa.getPotencial() == null)) {
                throw new EmpresaNoTieneRolPotencialPresentationException(empresa);
            }             
            model.setEmpresaId(empresa.getId());
            model.setPotencial(empresa.getPotencial());
            model.setContactosPosibles(empresa.getContactos());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    private PotencialDto getPotencial() {
        PotencialDto potencial = model.getPotencial();
        if (potencial == null) {
            throw new EmpresaNoTieneRolPotencialPresentationException(model.getEmpresaId());
        }
        return potencial;
    }
    
    
    public void prepareUpdateComerciales(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            SelectorComercialesModel selectorComercialesModel = new SelectorComercialesModel();
            selectorComercialesModel.setSelecteds(model.getPotencial().getComerciales());
            selectorComercialesModel.setUpdate("@(.Potencial-Comerciales)");
            selectorComercialesModel.setListener((ActionEvent event1) -> {
                updateComerciales();
            });
            selectorComercialesController.setModel(selectorComercialesModel);
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
      
    public void updateComerciales() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            for (ComercialDto selected : selectorComercialesController.getModel().getSelecteds()) {
                LOGGER.log(Level.INFO, "Comercial: {0}", selected);
            }
            model.getPotencial().setComerciales(selectorComercialesController.getModel().getSelecteds());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    
    /*public void prepareUpdateContacto(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            SelectorContactosModel selectorContactosModel = new SelectorContactosModel();
            selectorContactosModel.setItems(model.getContactosPosibles());
            selectorContactosModel.setUpdate("@(.Potencial-Contacto)");
            selectorContactosModel.setListener((ActionEvent event1) -> {
                updateContacto();
            });
            selectorContactosController.setModel(selectorContactosModel);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelUpdateContacto() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateContacto() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            LOGGER.log(Level.INFO, " Contacto seleccionado: {0}", selectorContactosController.getModel().getSelected());
            model.getPotencial().setContacto(selectorContactosController.getModel().getSelected()); 
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            System.out.println(ex.getMessage());
        } finally {
            log.finish();
        }
    }*/
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorListaContactosController.setEmpresaId(model.getEmpresaId());
            selectorListaContactosController.setContactosPosibles(model.getContactosPosibles());
            selectorListaContactosController.setContactos(model.getPotencial().getContactos());
            selectorListaContactosController.setUpdate("@(.Potencial-Contactos)");
            selectorListaContactosController.setUpdateId("Potencial-ContactosPopup");
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
            model.getPotencial().setContactos(selectorListaContactosController.getContactos()); 
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            System.out.println(ex.getMessage());
        } finally {
            log.finish();
        }
    }  
    
    /**
     *
     */
    public void prepareConvertirEnCliente() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CONVERTIR_EN_CLIENTE);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelConvertirEnCliente() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.POTENCIAL);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void prepareCreate() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {                       
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CREAR_POTENCIAL);            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void cancelCreate() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {                       
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.EMPRESA);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
     
    public void crear() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.required(model.getPotencial(), "potencial");
            //Assert.required(model.getPotencial().getContacto(), "contacto");
            facade.crearPotencial(model.getEmpresaId(), 
                ComercialAssembler.dtoToArrayId(model.getPotencial().getComerciales()),
                ContactoAssembler.dtoToArrayId(model.getPotencial().getContactos()),
                model.getPotencial().getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.POTENCIAL);
            JsfUtil.addSuccessMessage("growl", "Potencial creado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            JsfUtil.addErrorMessage(model.getContactoUI(), ex.getMessage());
        } finally {
            log.finish();
        }
    }
    
    public void modificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            LOGGER.log(Level.SEVERE, "mierda: 1 " +  model.getPotencial().getObservaciones().getObsInternas());
            ArgumentValidator.required(model.getPotencial(), "potencial");
            //Assert.required(model.getPotencial().getContacto(), "contacto");
            facade.modificarPotencial(model.getEmpresaId(), 
                ComercialAssembler.dtoToArrayId(model.getPotencial().getComerciales()),
                ContactoAssembler.dtoToArrayId(model.getPotencial().getContactos()),
                model.getPotencial().getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.POTENCIAL);
            JsfUtil.addSuccessMessage("growl", "Potencial modificado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            JsfUtil.addErrorMessage(model.getContactoUI(), ex.getMessage());
        } finally {
            log.finish();
        }
    }
    
    public void eliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(model, "model");
            ArgumentValidator.isNotNull(model.getEmpresaId(), "empresaId");
            facade.eliminarPotencial(model.getEmpresaId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.EMPRESA);
            JsfUtil.addSuccessMessage("growl", "Potencial eliminado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void bloquear() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(model, "model");
            ArgumentValidator.isNotNull(model.getEmpresaId(), "empresaId");
            facade.potencialBloquear(model.getEmpresaId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.POTENCIAL);
            JsfUtil.addSuccessMessage("growl", "Potencial bloqueado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void desbloquear() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(model, "model");
            ArgumentValidator.isNotNull(model.getEmpresaId(), "empresaId");
            facade.potencialDesbloquear(model.getEmpresaId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.POTENCIAL);
            JsfUtil.addSuccessMessage("growl", "Potencial desbloqueado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
   
}