package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.contratos.form.ContratoPersonasForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.comerciales.controller.SelectorComercialesController;
import es.zarca.covellog.interfaces.web.comerciales.model.SelectorComercialesModel;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorContactosController;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.jpa.jpql.Assert;
/**
 *
 * @author francisco
 */
@Named("personasContratoBean")
@ViewScoped
public class PersonasContratoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(PersonasContratoBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    SelectorComercialesController selectorComercialesController;
    @Inject
    SelectorListaContactosController selectorListaContactosController;
    @Inject
    SelectorContactosController selectorContactosController;
    private Integer id=0;
    private List<ComercialDto> comerciales;
    private ContactoDto firmante;
    private List<ContactoDto> contactos;
    private List<ContactoDto> contactosPosibles;
    private boolean validationFailed = false;
    private ContratoDto contrato;
    
    public PersonasContratoBean() {
    }
    
    public ContratoDto getContrato() {
        if (contrato == null) {
            contrato = modificarContratoBean.getContrato();
        }
        return contrato;
    }
    
    public void setContrato(ContratoDto contrato) {
        this.contrato = contrato;
        modificarContratoBean.setContrato(contrato);
    }
    
    public List<ComercialDto> getComerciales() {
        if (comerciales == null) {
            comerciales = getContrato().getComerciales();
        }
        return comerciales;
    }

    public void setComerciales(List<ComercialDto> comerciales) {
        this.comerciales = comerciales;
    }

    public ContactoDto getFirmante() {
        if (firmante == null) {
            firmante = getContrato().getFirmante();
        }
        return firmante;
    }

    public void setFirmante(ContactoDto firmante) {
        this.firmante = firmante;
    }

    public List<ContactoDto> getContactos() {
        if (contactos == null) {
            contactos = getContrato().getContactos();
        }
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "El contrato es null.");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateComerciales(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            SelectorComercialesModel selectorComercialesModel = new SelectorComercialesModel();
            selectorComercialesModel.setSelecteds(comerciales);
            selectorComercialesModel.setListener((ActionEvent event1) -> {
                comerciales = selectorComercialesController.getModel().getSelecteds();
            });
            selectorComercialesModel.setUpdate("@(.Contrato-Comerciales)");
            selectorComercialesController.setModel(selectorComercialesModel);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorListaContactosController.setEmpresaId(getContrato().getId());
            EmpresaDto empresa = facade.buscarEmpresa(getContrato().getId());
            selectorListaContactosController.setContactosPosibles(empresa.getContactos());
            selectorListaContactosController.setContactos(getContrato().getContactos());
            selectorListaContactosController.setUpdate("@(.Contrato-Contactos)");
            selectorListaContactosController.setUpdateId("Contrato-ContactosSelectorPopup");
            selectorListaContactosController.setOnGuardarListener((ActionEvent event1) -> {
                contactos = selectorListaContactosController.getContactos();
            });
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateFirmante(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorContactosController.getModel().setEmpresaId(getContrato().getId());
            EmpresaDto empresa = facade.buscarEmpresa(getContrato().getId());
            selectorContactosController.getModel().setItems(empresa.getContactos());
            selectorContactosController.getModel().setSelected(null);
            selectorContactosController.getModel().setUpdate("@(.Contrato-Firmante)");
            selectorContactosController.getModel().setUpdateId("Contrato-FirmanteSelectorPopup");
            selectorContactosController.getModel().setListener((ActionEvent event1) -> {
                firmante = selectorContactosController.getModel().getSelected();
            });
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            validationFailed = false;
            if (comerciales == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("Pestanas:ContratoPersonasForm:SelectorComercial:comercialesSeleccionados", "Ponga almenos un comercial.");
            }
            if (!validationFailed) {
                ContratoPersonasForm form = new ContratoPersonasForm();
                List<Integer> comercialesId = new ArrayList<>();
                comerciales.forEach(comercial -> {
                    comercialesId.add(comercial.getId());
                });
                form.setComercialesId(comercialesId);
                List<Integer> contactosId = new ArrayList<>();
                if (contactos != null) {
                    contactos.forEach(contacto -> {
                        contactosId.add(contacto.getId());
                    });
                }
                form.setContactosId(contactosId);
                if (firmante != null) {
                    form.setFirmanteId(firmante.getId());
                }
                setContrato(facade.modificarPersonas(getContrato().getId(), form));
                prepare();
                JsfUtil.addSuccessMessage("Contrato modificado correctamente");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}