package es.zarca.covellog.interfaces.web.empresas.cliente.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionPostalEditController;
import es.zarca.covellog.interfaces.web.empresas.cliente.exception.EmpresaNoTieneRolClientePresentationException;
import es.zarca.covellog.interfaces.web.empresas.cliente.model.ClienteFacturacionModel;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.formapago.controller.FormaPagoEditController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
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
@Named("clienteFacturacionController")
@ViewScoped
public class ClienteFacturacionController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ClienteFacturacionController.class.getName());
    
    @Inject
    private EmpresaServiceFacade facade;
    @Inject
    private FormaPagoEditController formaPagoEditController;
    @Inject
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    private DireccionPostalEditController direccionPostalEditController;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    
    
    private ClienteFacturacionModel model;
    
    public ClienteFacturacionController() {
    }
    
    public ClienteFacturacionModel getModel() {
        return model;
    }

    public void setModel(ClienteFacturacionModel model) {
        this.model = model;
    }
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            System.err.println("COJONES model.getContactosPosibles(): " + (model.getContactosPosibles() == null ? "NULLLLL" : model.getContactosPosibles()));
            selectorListaContactosController.setEmpresaId(model.getEmpresaId());
            selectorListaContactosController.setContactosPosibles(model.getContactosPosibles());
            selectorListaContactosController.setContactos(model.getDetalleFacturacion().getContactos());
            selectorListaContactosController.setUpdate("@(.ClienteFacturacion-ContactosFacturacion)");
            selectorListaContactosController.setUpdateId("ClienteFacturacion-ContactosSelectorPopup");
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
            model.getDetalleFacturacion().setContactos(selectorListaContactosController.getContactos()); 
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
            direccionPostalEditController.setDireccion(model.getDetalleFacturacion().getDireccionPostal());
            direccionPostalEditController.setListener((ActionEvent event1) -> {
                updateDireccionPostal(event1);
            });
            direccionPostalEditController.setUpdate("@(.ClienteFacturacion-DireccionPostal)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    public void updateDireccionPostal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getDetalleFacturacion().setDireccionPostal(direccionPostalEditController.getDireccion());
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
                model.getDetalleFacturacion().setDireccionPostal(direccionPostal);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateFormaPagoVenta(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            formaPagoEditController.setModel(model.getDetalleFacturacion().getFormaPagoVenta());
            formaPagoEditController.setListener((ActionEvent event1) -> {
                updateFormaPagoVenta();
            });
            formaPagoEditController.setUpdate("@(.formaPagoVentaCliente)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateFormaPagoVenta() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getDetalleFacturacion().setFormaPagoVenta(formaPagoEditController.getModel());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateFormaPagoAlquiler(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            formaPagoEditController.setModel(model.getDetalleFacturacion().getFormaPagoAlquiler());
            formaPagoEditController.setListener((ActionEvent event1) -> {
                updateFormaPagoAlquiler();
            });
            formaPagoEditController.setUpdate("@(.formaPagoAlquilerCliente)");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void updateFormaPagoAlquiler() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getDetalleFacturacion().setFormaPagoAlquiler(formaPagoEditController.getModel());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            facade.modificarInfoClienteFacturacion(model.getEmpresaId(), model.getDetalleFacturacion());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_FACTURACION);
            JsfUtil.addSuccessMessage("growl", "Datos facturación cliente modificados.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    public void actualizarEmpresa(EmpresaDto empresa) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((empresa == null) || (empresa.getCliente() == null)) {
                throw new EmpresaNoTieneRolClientePresentationException(empresa);
            }
            model = new ClienteFacturacionModel();
            model.setEmpresaId(empresa.getId());
            if (empresa.getCliente().getDetalleFacturacion() == null) {
                model.setDetalleFacturacion(new DetalleFacturacionDto());
            } else {
                model.setDetalleFacturacion(empresa.getCliente().getDetalleFacturacion());
            }
            model.setContactosPosibles(empresa.getContactos());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
   
}