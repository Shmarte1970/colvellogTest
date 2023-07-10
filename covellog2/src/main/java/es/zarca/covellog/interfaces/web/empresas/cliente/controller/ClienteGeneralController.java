package es.zarca.covellog.interfaces.web.empresas.cliente.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.comerciales.internal.assembler.ComercialAssembler;
import es.zarca.covellog.interfaces.facade.contactos.internal.assembler.ContactoAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.ClienteDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.TipoClienteDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionPostalEditController;
import es.zarca.covellog.interfaces.web.comerciales.controller.SelectorComercialesController;
import es.zarca.covellog.interfaces.web.comerciales.model.SelectorComercialesModel;
import es.zarca.covellog.interfaces.web.empresas.cliente.exception.EmpresaNoTieneRolClientePresentationException;
import es.zarca.covellog.interfaces.web.empresas.cliente.model.ClienteGeneralModel;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.controller.ModificarEmpresaEstado;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("clienteGeneralController")
@ViewScoped
public class ClienteGeneralController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ClienteGeneralController.class.getName());
    
    @Inject
    EmpresaServiceFacade facade;
    @Inject
    SelectorComercialesController selectorComercialesController;
    @Inject
    private SelectorListaContactosController selectorListaContactosController;
    @Inject
    DireccionPostalEditController direccionPostalEditController;
    @Inject
    private ModificarEmpresaController modificarEmpresaController;
    
    private ClienteGeneralModel model = new ClienteGeneralModel();
    
    
    public ClienteGeneralController() {
    }
    
    public ClienteGeneralModel getModel() {
        return model;
    }

    public void setModel(ClienteGeneralModel model) {
        this.model = model;
    }
    
    public void comprobarCif() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getEmpresa().setEsCorrectoCif(facade.esCorrectoCif(model.getEmpresa().getCif()));
        } catch (Exception e) {
            JsfUtil.addException(e);
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
            model.setEmpresa(empresa);
            model.setTiposClientePosibles(facade.getTiposClientePosibles());
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    private ClienteDto getCliente() {
        ClienteDto cliente = model.getEmpresa().getCliente();
        if (cliente == null) {
            throw new EmpresaNoTieneRolClientePresentationException(model.getEmpresa());
        }
        return cliente;
    }
    
    
    public void prepareUpdateComerciales(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            SelectorComercialesModel selectorComercialesModel = new SelectorComercialesModel();
            selectorComercialesModel.setSelecteds(model.getEmpresa().getCliente().getComerciales());
            selectorComercialesModel.setListener((ActionEvent event1) -> {
                updateComerciales(event1);
            });
            selectorComercialesModel.setUpdate("@(.Cliente-Comerciales)");
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
                LOGGER.log(Level.INFO, " CONTROLLER: ClienteGeneralController.updateComerciales(): A\u00f1ade => {0}", comercial.getNombreCompleto());
            });
            model.getEmpresa().getCliente().setComerciales(comerciales);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
  
    
   /* public void prepareUpdateContacto(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            SelectorContactosModel selectorContactosModel = new SelectorContactosModel();
            selectorContactosModel.setItems(model.getEmpresa().getContactos());
            for (ContactoDto contacto : model.getEmpresa().getContactos()) {
                System.out.println(contacto.getNombre());
            }
            selectorContactosModel.setUpdate("@(.Cliente-Contacto)");
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
            model.getEmpresa().getCliente().setContactos(selectorContactosController.getModel().getSelected()); 
            LOGGER.log(Level.INFO, "Contacto seleccionado: {0}", model.getEmpresa().getCliente().getContacto());
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
            selectorListaContactosController.setEmpresaId(model.getEmpresa().getId());
            selectorListaContactosController.setContactosPosibles(model.getEmpresa().getContactos());
            selectorListaContactosController.setContactos(model.getEmpresa().getCliente().getContactos());
            selectorListaContactosController.setUpdate("@(.Cliente-Contactos)");
            selectorListaContactosController.setUpdateId("Cliente-ContactosSelectorPopup");
            selectorListaContactosController.setOnGuardarListener((ActionEvent event1) -> {
                updateContactos();
            });
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    //
    public void updateContactos() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            model.getEmpresa().getCliente().setContactos(selectorListaContactosController.getContactos()); 
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
    public void prepareCreate() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {                       
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CREAR_CLIENTE);            
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
            ClienteDto cliente = getCliente();
            //Assert.required(cliente.getContacto(), "contacto");
            facade.crearCliente(model.getEmpresa().getId(),
                cliente.getCodigoCliente(),
                cliente.getTipoCliente().getId(),
                ComercialAssembler.dtoToArrayId(cliente.getComerciales()),
                ContactoAssembler.dtoToArrayId(cliente.getContactos()),
                cliente.getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
            JsfUtil.addSuccessMessage("growl", "Cliente creado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void modificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ClienteDto cliente = getCliente();
            //Assert.required(cliente.getContacto(), "contacto");
            facade.modificarCliente(model.getEmpresa().getId(),
                cliente.getCodigoCliente(),
                cliente.getTipoCliente().getId(),
                ComercialAssembler.dtoToArrayId(cliente.getComerciales()),
                ContactoAssembler.dtoToArrayId(cliente.getContactos()),
                cliente.getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
            JsfUtil.addSuccessMessage("growl", "Cliente modificado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ClienteDto cliente = getCliente();
            facade.eliminarCliente(model.getEmpresa().getId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.EMPRESA);
            JsfUtil.addSuccessMessage("growl", "Cliente eliminado.");
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
            ClienteDto cliente = getCliente();
            facade.clienteBloquear(model.getEmpresa().getId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
            JsfUtil.addSuccessMessage("growl", "Cliente bloqueado.");
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
            ClienteDto cliente = getCliente();
            facade.clienteDesbloquear(model.getEmpresa().getId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
            JsfUtil.addSuccessMessage("growl", "Cliente desbloqueado.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void convertirEnPotencial() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ClienteDto cliente = getCliente();
            facade.convertirClienteEnPotencial(model.getEmpresa().getId());
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.POTENCIAL);
            JsfUtil.addSuccessMessage("growl", "Cliente convertido a potencial.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    /**
     *
     */
    public void convertirEnCliente() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ClienteDto cliente = getCliente();
            facade.crearCliente(model.getEmpresa().getId(),
                cliente.getCodigoCliente(),
                cliente.getTipoCliente().getId(),
                ComercialAssembler.dtoToArrayId(cliente.getComerciales()),
                ContactoAssembler.dtoToArrayId(cliente.getContactos()),
                cliente.getObservaciones()
            );
            modificarEmpresaController.setEstado(ModificarEmpresaEstado.CLIENTE_GENERAL);
            JsfUtil.addSuccessMessage("growl", "Potencial convertido a cliente.");
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void sugerirCodigoCliente() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            getCliente().setCodigoCliente(facade.sugerirCodigoCliente());
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    @FacesConverter(value="TipoClienteConverter")
    public static class TipoClienteConverter implements Converter {
        
        private ClienteGeneralController getController(FacesContext facesContext) {
            return (ClienteGeneralController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "clienteGeneralController");
        }

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            for (TipoClienteDto tipoClientePosible : getController(facesContext).getModel().getTiposClientePosibles()) {
                if (tipoClientePosible.getId().equals(value)) {
                    return tipoClientePosible;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoClienteDto) {
                TipoClienteDto o = (TipoClienteDto) object;
                return o.getId();
            } else {
                return null;
            }
        }
    }
    
}