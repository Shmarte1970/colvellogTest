package es.zarca.covellog.interfaces.web.empresas.proveedor.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.DireccionPostalAssembler;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ProveedorDto;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionPostalEditController;
import es.zarca.covellog.interfaces.web.comerciales.controller.SelectorComercialesController;
import es.zarca.covellog.interfaces.web.comerciales.model.SelectorComercialesModel;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.formapago.controller.FormaPagoEditController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.empresas.proveedor.exception.EmpresaNoTieneRolProveedorPresentationException;
import es.zarca.covellog.interfaces.web.empresas.proveedor.model.ProveedorModel;
import es.zarca.covellog.interfaces.web.exception.ModelIsNullPresentationException;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.List;
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
@Named("proveedorController")
@ViewScoped
public class ProveedorController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ProveedorController.class.getName());
    
    @Inject
    private EmpresaServiceFacade facade;
    @Inject
    private SelectorComercialesController selectorComercialesController;
    @Inject
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    private DireccionPostalEditController direccionPostalEditController;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    @Inject
    private FormaPagoEditController formaPagoEditController;
    
    private ProveedorModel model = new ProveedorModel();
    
    public ProveedorController() {
    }
    
    public ProveedorModel getModel() {
        return model;
    }

    public void setModel(ProveedorModel model) {
        this.model = model;
    }
    
    
    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((empresa == null) || (empresa.getProveedor() == null)) {
                throw new EmpresaNoTieneRolProveedorPresentationException(empresa);
            }             
            model.setEmpresaId(empresa.getId());
            model.setProveedor(empresa.getProveedor());
            model.setContactosPosibles(empresa.getContactos());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    
    public void prepareUpdateComerciales(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model == null) {
                throw new ModelIsNullPresentationException("ProveedorModel");
            }
            SelectorComercialesModel selectorComercialesModel = new SelectorComercialesModel();
            selectorComercialesModel.setSelecteds(model.getProveedor().getComerciales());
            selectorComercialesModel.setListener((ActionEvent event1) -> {
                updateComerciales(event1);
            });
            selectorComercialesModel.setUpdate("@(.Proveedor-Comerciales)");
            selectorComercialesController.setModel(selectorComercialesModel);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateComerciales(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            List<ComercialDto> comerciales = selectorComercialesController.getModel().getSelecteds();
            comerciales.forEach((comercial) -> {
                LOGGER.log(Level.INFO, "Seleccionado => {0}", comercial.getNombreCompleto());
            });
            model.getProveedor().setComerciales(comerciales);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
  
    /*
    public void prepareUpdateContacto(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model == null) {
                throw new ModelIsNullPresentationException("ProveedorModel");
            }
            SelectorContactosModel selectorContactosModel = new SelectorContactosModel();
            selectorContactosModel.setItems(model.getContactosPosibles());
            selectorContactosModel.setUpdate("@(.Proveedor-Contacto)");
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
    
    public void updateContacto() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            LOGGER.log(Level.INFO, "Contacto seleccionado: {0}", selectorContactosController.getModel().getSelected());
            model.getProveedor().setContacto(selectorContactosController.getModel().getSelected()); 
            LOGGER.log(Level.INFO, "Contacto seleccionado: {0}", model.getProveedor().getContacto());
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
            selectorListaContactosController.setContactos(model.getProveedor().getContactos());
            selectorListaContactosController.setUpdate("@(.Proveedor-Contactos)");
            selectorListaContactosController.setUpdateId("Proveedor-ContactosPopup");
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
            model.getProveedor().setContactos(selectorListaContactosController.getContactos()); 
        } catch (Exception ex) {
            JsfUtil.addException(ex);
            System.out.println(ex.getMessage());
        } finally {
            log.finish();
        }
    }  
    
    public void prepareUpdateDireccionPostal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model == null) {
                throw new ModelIsNullPresentationException("ProveedorModel");
            }
            direccionPostalEditController.setDireccion(model.getProveedor().getDireccionPostal());
            direccionPostalEditController.setListener((ActionEvent event1) -> {
                updateDireccionPostal(event1);
            });
            direccionPostalEditController.setUpdate("@(.Proveedor-DireccionPostal)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    public void updateDireccionPostal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getProveedor().setDireccionPostal(direccionPostalEditController.getDireccion());
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
            } else {
                DireccionPostalDto direccionPostal = new DireccionPostalDto(
                    "", 
                    direccion.getDireccion(), 
                    direccion.getDireccion2(), 
                    direccion.getCodigoPostal(), 
                    direccion.getPoblacion()
                );
                model.getProveedor().setDireccionPostal(direccionPostal);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateFormaPago(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            formaPagoEditController.setModel(model.getProveedor().getFormaPago());
            formaPagoEditController.setListener((ActionEvent event1) -> {
                updateFormaPago();
            });
            formaPagoEditController.setUpdate("@(.Proveedor-FormaPago)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateFormaPago() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getProveedor().setFormaPago(formaPagoEditController.getModel());
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
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CREAR_PROVEEDOR);            
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
            ProveedorDto dto = model.getProveedor();
            ArgumentValidator.isNotNull(dto, "proveedor");
            //Assert.required(dto.getContacto(), "contacto");
            facade.proveedorCrear(model.getEmpresaId(),
                dto.getCodigoProveedor(),
                ComercialAssembler.dtoToArrayId(dto.getComerciales()),
                ContactoAssembler.dtoToArrayId(dto.getContactos()),
                dto.getFormaPago(),
                DireccionPostalAssembler.toForm(dto.getDireccionPostal()),
                dto.getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.PROVEEDOR);
            JsfUtil.addSuccessMessage("growl", "Proveedor creado correctamente.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void modificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ProveedorDto dto = model.getProveedor();
            ArgumentValidator.isNotNull(dto, "proveedor");
            //Assert.required(dto.getContacto(), "contacto");
            facade.modificarProveedor(model.getEmpresaId(),
                dto.getCodigoProveedor(),
                ComercialAssembler.dtoToArrayId(dto.getComerciales()),
                ContactoAssembler.dtoToArrayId(dto.getContactos()),
                dto.getFormaPago(),
                DireccionPostalAssembler.toForm(dto.getDireccionPostal()),
                dto.getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.PROVEEDOR);
            JsfUtil.addSuccessMessage("growl", "Proveedor modificado correctamente.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void eliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(model.getProveedor(), "proveedor");
            facade.eliminarProveedor(model.getEmpresaId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.EMPRESA);
            JsfUtil.addSuccessMessage("growl", "Proveedor eliminado correctamente.");
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
            ArgumentValidator.isNotNull(model.getProveedor(), "proveedor");
            facade.proveedorBloquear(model.getEmpresaId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.PROVEEDOR);
            JsfUtil.addSuccessMessage("growl", "Proveedor bloqueado.");
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
            ArgumentValidator.isNotNull(model.getProveedor(), "proveedor");
            facade.proveedorDesbloquear(model.getEmpresaId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.PROVEEDOR);
            JsfUtil.addSuccessMessage("growl", "Proveedor desbloqueado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void sugerirCodigoProveedor() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getProveedor().setCodigoProveedor(facade.sugerirCodigoProveedor());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
}
