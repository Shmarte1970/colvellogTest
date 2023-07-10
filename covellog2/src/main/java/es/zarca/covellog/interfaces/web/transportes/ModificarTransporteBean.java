package es.zarca.covellog.interfaces.web.transportes;

import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.transporte.TransportesFacade;
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.empresas.model.FiltroRolEmpresa;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
/**
 *
 * @author francisco
 */
@Named("modificarTransporteBean")
@ViewScoped
public class ModificarTransporteBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ModificarTransporteBean.class.getName());
    @Inject
    private TransportesFacade facade;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    
    private ActionListener listener;
    private boolean nuevo = true;
    private Integer id;
    private EmpresaMiniDto empresa;
    private String nombre;
    private Integer capacidad;
    private String obsInternas;
    private String obsPublicas;
    private boolean validationFailed;
    
    public ModificarTransporteBean() {
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
        
    }
    
    public void prepareSelectEmpresa(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            selectorEmpresaController.getModel().setFiltroRolEmpresa(FiltroRolEmpresa.PROVEEDORES);
            selectorEmpresaController.setOnSeleccionarUpdate("@(.TransporteEmpresaSeleccionable)");
            selectorEmpresaController.setOnSeleccionarListener((ActionEvent event1) -> {
                empresa = selectorEmpresaController.getSelectedMiniDto();
            });
            selectorEmpresaController.setOnCancelarListener((ActionEvent event1) -> {
            });
            
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareNuevo() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevo = true;
            id = null;
            empresa = null;
            nombre = "";
            capacidad = 1;
            obsInternas = null;
            obsPublicas = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    void prepareModificar(Integer id) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            nuevo=false;
            TransporteDto transporte = facade.findById(id);
            this.id = transporte.getId();
            empresa = transporte.getEmpresa();
            nombre = transporte.getNombre();
            capacidad = transporte.getCapacidad();
            obsInternas = transporte.getObservaciones().getObsInternas();
            obsPublicas = transporte.getObservaciones().getObsPublicas();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void guardar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            validationFailed = false;
            if (empresa == null) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("TransporteEditForm:empresa", "La empresa es obligatoria.");
            }
            if ((nombre == null) || (nombre.trim().isEmpty())) {
                validationFailed = true;
                JsfUtil.addValidationErrorMessage("TransporteEditForm:nombre", "El nombre es obligatorio.");
            }
            if (!validationFailed) {
                if (nuevo) {
                    facade.nuevo(empresa.getId(), nombre, capacidad, obsInternas, obsPublicas);
                } else {
                    facade.modificar(id, empresa.getId(), nombre, capacidad, obsInternas, obsPublicas);
                }
                JsfUtil.addSuccessMessage("Transporte modificado correctamente");
            } else {
                JsfUtil.validationFailed();
                PrimeFaces.current().ajax().update(":growl");
            }
        } catch (Exception ex) {            
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    
    public void cancelar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    } 

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmpresaMiniDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaMiniDto empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
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

  
}