package es.zarca.covellog.interfaces.web.empresas.controller;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaAssembler;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.EmpresaMiniDtoAssembler;
import es.zarca.covellog.interfaces.web.empresas.model.ListadoEmpresasModel;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author francisco
 */
@Named("selectorEmpresaController")
@ViewScoped
public class SelectorEmpresaController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorEmpresaController.class.getName());
    @Inject
    private EmpresaServiceFacade facade;
    private ActionListener onSeleccionarListener;
    private ActionListener onCancelarListener;
    private String onSeleccionarUpdate;
    private String onCancelarUpdate;
    
    private ListadoEmpresasModel model;

    public SelectorEmpresaController() {
    }

    public ListadoEmpresasModel getModel() {
        if (model == null) {
            model = new ListadoEmpresasModel(facade);
        }
        return model;
    }

    public void setModel(ListadoEmpresasModel model) {
        this.model = model;
    }

    public ActionListener getOnSeleccionarListener() {
        return onSeleccionarListener;
    }

    public ActionListener getOnCancelarListener() {
        return onCancelarListener;
    }

    public void setOnCancelarListener(ActionListener onCancelarListener) {
        this.onCancelarListener = onCancelarListener;
    }
    
    public void setOnSeleccionarListener(ActionListener onSeleccionarListener) {
        this.onSeleccionarListener = onSeleccionarListener;
    }

    public String getOnSeleccionarUpdate() {
        return onSeleccionarUpdate;
    }

    public void setOnSeleccionarUpdate(String onSeleccionarUpdate) {
        this.onSeleccionarUpdate = onSeleccionarUpdate;
    }

    public String getOnCancelarUpdate() {
        return onCancelarUpdate;
    }

    public void setOnCancelarUpdate(String onCancelarUpdate) {
        this.onCancelarUpdate = onCancelarUpdate;
    }
    
    public void onSeleccionar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model.getSelected() != null) {
                if (onSeleccionarListener != null) {
                    onSeleccionarListener.processAction(event);
                }
            } else {
                JsfUtil.showErrorDialog("Seleccione alguna empresa");
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void onSeleccionar(SelectEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onSeleccionarListener != null) {
                onSeleccionarListener.processAction(null);
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public void onCancelar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (onCancelarListener != null) {
                onCancelarListener.processAction(event);
            }
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }
    }
    
    public EmpresaDto getSelected() {
        return EmpresaAssembler.toDto(model.getSelected());
    }
    
    public EmpresaMiniDto getSelectedMiniDto() {
        return EmpresaMiniDtoAssembler.toDto(model.getSelected());
    }

    public void prepare() {
        this.model = null;
    }
    
}