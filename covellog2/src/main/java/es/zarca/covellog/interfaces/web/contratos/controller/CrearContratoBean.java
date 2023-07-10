package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.application.exception.ValidationBusinessException;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("crearContratoBean")
@ViewScoped
public class CrearContratoBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CrearContratoBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    private Date fecha;
    private EmpresaDto empresa;
    private boolean validationFailed;
    
    public CrearContratoBean() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }
    
    public void prepareSelectEmpresa(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                empresa = selectorEmpresaController.getSelected();
            });
            selectorEmpresaController.setOnCancelarListener((event1) -> {
            });
            selectorEmpresaController.setOnSeleccionarUpdate("@(.Contrato-Empresa)");
            selectorEmpresaController.setOnCancelarUpdate("");
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            fecha = new Date();
            empresa = null;
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
            validationFailed = false;
            if (fecha == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("ContratoCrearForm:fecha_input", "La fecha es obligatoria.");
            }
            if (empresa == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("ContratoCrearForm:empresa", "El cliente es obligatorio.");
            }
            if (!validationFailed) {
                facade.crear(fecha, empresa.getId());
                JsfUtil.addSuccessMessage("Contrato creado correctamente.");
                notificarGuardar(event);
            } else {
                JsfUtil.validationFailed();
                throw new ValidationBusinessException("Revise los errores");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
 
}