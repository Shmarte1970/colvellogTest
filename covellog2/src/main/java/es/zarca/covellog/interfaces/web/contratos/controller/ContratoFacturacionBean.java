package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.adreces.form.DireccionPostalForm;
import es.zarca.covellog.application.contratos.form.ContratoFacturacionForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoDto;
import es.zarca.covellog.interfaces.web.adreces.controller.DireccionPostalEditController;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorListaContactosController;
import es.zarca.covellog.interfaces.web.empresas.formapago.controller.FormaPagoEditController;
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
@Named("contratoFacturacionBean")
@ViewScoped
public class ContratoFacturacionBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoFacturacionBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private FormaPagoEditController formaPagoEditController;
    @Inject
    private DireccionPostalEditController direccionPostalEditController;
    @Inject
    private SelectorListaContactosController selectorListaContactosController;
    private boolean exentoIva;
    private DireccionPostalDto direccionPostal;
    private List<ContactoDto> contactos;
    private FormaPagoDto formaPagoVenta;
    private FormaPagoDto formaPagoAlquiler;
    private String obsInternas;
    private String obsPublicas;
    private boolean validationFailed;
    
    public ContratoFacturacionBean() {
    }
    
    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }

    public boolean isExentoIva() {
        return exentoIva;
    }

    public void setExentoIva(boolean exentoIva) {
        this.exentoIva = exentoIva;
    }
    
    public DireccionPostalDto getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostalDto direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public FormaPagoDto getFormaPagoVenta() {
        return formaPagoVenta;
    }

    public void setFormaPagoVenta(FormaPagoDto formaPagoVenta) {
        this.formaPagoVenta = formaPagoVenta;
    }

    public FormaPagoDto getFormaPagoAlquiler() {
        return formaPagoAlquiler;
    }

    public void setFormaPagoAlquiler(FormaPagoDto formaPagoAlquiler) {
        this.formaPagoAlquiler = formaPagoAlquiler;
    }

    public String getObsInternas() {
        return obsInternas;
    }

    public void setObsInternas(String obsInternas) {
        this.obsInternas = obsInternas;
    }

    public String getObsPublicas() {
        return obsPublicas;
    }

    public void setObsPublicas(String obsPublicas) {
        this.obsPublicas = obsPublicas;
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "El contrato es null.");
            DetalleFacturacionDto facturacion = getContrato().getFacturacion();
            if (facturacion != null) {
                exentoIva = facturacion.getExentoIva();
                direccionPostal = facturacion.getDireccionPostal();
                contactos = facturacion.getContactos() != null ? facturacion.getContactos() : new ArrayList();
                formaPagoVenta = facturacion.getFormaPagoVenta();
                formaPagoAlquiler = facturacion.getFormaPagoAlquiler();
                obsInternas = facturacion.getObservaciones().getObsInternas();
                obsPublicas = facturacion.getObservaciones().getObsPublicas();
            }
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ContratoFacturacionForm form = new ContratoFacturacionForm();
            form.setExentoIva(exentoIva);
            List<Integer> contactosIds = new ArrayList<>();
            if (contactos != null) {
                contactos.forEach(contacto -> {
                    contactosIds.add(contacto.getId());
                });
            }
            validationFailed = false;
            if (direccionPostal == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("Pestanas:ClienteFacturacionForm:direccionPostal:direccion", "La Direccion es obligatoria.");
            }
            if (!validationFailed) {
                form.setContactos(contactosIds);
                form.setFormaPagoVenta(formaPagoVenta);
                form.setFormaPagoAlquiler(formaPagoAlquiler);
                form.setDireccionPostal(
                    new DireccionPostalForm(
                        direccionPostal.getAtt(), 
                        direccionPostal.getDireccion(), 
                        direccionPostal.getDireccion2(), 
                        direccionPostal.getCodigoPostal(), 
                        direccionPostal.getPoblacion().getId()
                    )
                );
                form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));
                setContrato(facade.modificarDatosFacturacion(getContrato().getId(), form));
                prepare();
                JsfUtil.addSuccessMessage("Contrato modificado correctamente");
                notificarGuardar(event);
            } 
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateContactos(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try { 
            selectorListaContactosController.setEmpresaId(getContrato().getCliente().getId());
            selectorListaContactosController.setContactosPosibles(facade.getContactosPosibles(getContrato().getCliente().getId()));
            selectorListaContactosController.setContactos(contactos);
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
            contactos = selectorListaContactosController.getContactos(); 
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateDireccionPostal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            direccionPostalEditController.setDireccion(direccionPostal);
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
            direccionPostal = direccionPostalEditController.getDireccion();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void copiarDireccionPrincipal(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            EmpresaDto empresa = facade.buscarEmpresa(getContrato().getCliente().getId());
            DireccionDto direccion = empresa.getDireccionFiscal();
            if (direccion == null) {
                JsfUtil.addErrorMessage("La direccion principal esta vacia.");
            } else {
                direccionPostal = new DireccionPostalDto(
                    "", 
                    direccion.getDireccion(), 
                    direccion.getDireccion2(), 
                    direccion.getCodigoPostal(), 
                    direccion.getPoblacion()
                );
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
            formaPagoEditController.setModel(formaPagoVenta);
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
            formaPagoVenta = formaPagoEditController.getModel();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateFormaPagoAlquiler(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            formaPagoEditController.setModel(formaPagoAlquiler);
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
            formaPagoAlquiler = formaPagoEditController.getModel();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
}