package es.zarca.covellog.interfaces.web.stock.controller;

import es.zarca.covellog.domain.model.app.exception.MalformedTagsException;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.util.DateUtil.DateUtil;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.stock.StockFacade;
import es.zarca.covellog.interfaces.facade.stock.dto.StockDto;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 */
@Named("cambiarPropietarioStockBean")
@ViewScoped
public class CambiarPropietarioStockBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(CambiarPropietarioStockBean.class.getName());
    @Inject
    private StockFacade facade;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    private List<StockDto> selecteds = new ArrayList();
    private Date fecha;
    private Date hora;
    @NotNull
    private EmpresaDto empresa;
    @Size(max = 255)
    private String tags;
    @Size(max = 10000)
    private String observaciones;
    
    public CambiarPropietarioStockBean() {
    }
    
    public List<StockDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<StockDto> selecteds) {
        this.selecteds = selecteds;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
    
    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void prepare(List<StockDto> selecteds) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {           
            this.selecteds = selecteds;
            fecha = new Date();
            hora = fecha;
            empresa = null;
            tags = "";
            observaciones = "";
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSelectPropietario(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selectorEmpresaController.setOnSeleccionarUpdate("@(.CambiarPropietarioStockSeleccionable)");
            selectorEmpresaController.setOnSeleccionarListener((ActionEvent event1) -> {
                empresa = selectorEmpresaController.getSelected();
            });
            selectorEmpresaController.setOnCancelarListener((ActionEvent event1) -> {
            });
            
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
            boolean validationFailed = false;
            if (fecha == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("PropietarioStockForm:fecha", "La fecha es obligatoria.");
            }
            if (hora == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("PropietarioStockForm:hora", "La hora es obligatoria.");
            }
            if (empresa == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("PropietarioStockForm:empresa", "El propietario es obligatorio.");
            } 
            if (!validationFailed) {
                List<Integer> ids = new ArrayList();
                for (StockDto selected : selecteds) {
                    ids.add(selected.getId());
                }
                facade.cambiarPropietario(ids, DateUtil.fusionarFechaHora(fecha, hora), observaciones, empresa.getId(), tags);
                JsfUtil.addSuccessMessage("Propietario cambiado correctamente.");
                notificarGuardar(event);
            } else {
                JsfUtil.validationFailed();
                //throw new ValidationBusinessException("Revise los errores");
            }
        } catch (MalformedTagsException ex) {
            JsfUtil.addValidationErrorMessage("PropietarioStockForm:tags", ex.getMessage());
            JsfUtil.validationFailed();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

}