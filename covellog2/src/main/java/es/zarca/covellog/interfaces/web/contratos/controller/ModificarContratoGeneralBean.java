package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.contratos.form.ModificarContratoGeneralForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller.DireccionEnvioSelectorBean;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 */
@Named("modificarContratoGeneralBean")
@ViewScoped
public class ModificarContratoGeneralBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarContratoGeneralBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private DireccionEnvioSelectorBean direccionEnvioSelectorBean;
    private Date fecha;
    private String codigoPedidoCliente;
    private String codigoPedidoProveedor;
    private Integer previsionMesesAlquiler;
    @Size(max = 10000)
    private String obsInternas;
    @Size(max = 10000)
    private String obsPublicas;
    private UbicacionDto direccionEnvio;
    private boolean validationFailed;
    
    public ModificarContratoGeneralBean() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getCodigoPedidoCliente() {
        return codigoPedidoCliente;
    }

    public void setCodigoPedidoCliente(String codigoPedidoCliente) {
        this.codigoPedidoCliente = codigoPedidoCliente;
    }

    public String getCodigoPedidoProveedor() {
        return codigoPedidoProveedor;
    }

    public void setCodigoPedidoProveedor(String codigoPedidoProveedor) {
        this.codigoPedidoProveedor = codigoPedidoProveedor;
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

    public UbicacionDto getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(UbicacionDto direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public Integer getPrevisionMesesAlquiler() {
        return previsionMesesAlquiler;
    }

    public void setPrevisionMesesAlquiler(Integer previsionMesesAlquiler) {
        this.previsionMesesAlquiler = previsionMesesAlquiler;
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ContratoDto contrato = modificarContratoBean.getContrato();
            fecha = contrato.getFechaContrato();
            codigoPedidoCliente = contrato.getCodigoPedidoCliente();
            codigoPedidoProveedor = contrato.getCodigoProveedor();
            obsInternas = contrato.getObservaciones().getObsInternas();
            obsPublicas = contrato.getObservaciones().getObsPublicas();
            direccionEnvio = contrato.getDireccionEnvio();
            previsionMesesAlquiler = contrato.getPrevisionMesesAlquiler();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareUpdateDireccionEnvio(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionEnvioSelectorBean.prepare(modificarContratoBean.getContrato().getCliente().getId());
            direccionEnvioSelectorBean.setOnSeleccionarUpdate(":growl,@(.contrato-direccion-envio)");
            direccionEnvioSelectorBean.setOnSeleccionarListener((ActionEvent event1) -> {
                this.direccionEnvio = direccionEnvioSelectorBean.getSelected();
            });
            direccionEnvioSelectorBean.setOnCancelarListener((ActionEvent event1) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            validationFailed = false;
            
            if (fecha == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("Pestanas:ContratoGeneralForm:fecha", "La fecha de contrato es obligatoria.");
                      } 
            if (direccionEnvio == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("Pestanas:ContratoGeneralForm:direccionFiscal:direccion", "La direccion es obligatoria.");
            }
            if (!validationFailed) {
                ModificarContratoGeneralForm form = new ModificarContratoGeneralForm();
                form.setFecha(fecha);
                form.setCodigoPedidoCliente(codigoPedidoCliente);
                form.setCodigoPedidoProveedor(codigoPedidoProveedor);
                form.setPrevisionMesesAlquiler(previsionMesesAlquiler);
                form.setDireccionEnvioId(direccionEnvio.getId());
                form.setObservaciones(new DobleObservacionForm(obsInternas, obsPublicas));
                facade.modificarGeneral(modificarContratoBean.getContrato().getId(), form);
                modificarContratoBean.setContrato(null);
                JsfUtil.addSuccessMessage("Contrato modificado correctamente.");
            } else {
                JsfUtil.validationFailed();
                //throw new ValidationBusinessException("Revise los errores");
               // 
               // PrimeFaces.current().ajax().update("StockEditForm");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
}