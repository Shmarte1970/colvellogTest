package es.zarca.covellog.interfaces.web.albaranes.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.zarca.covellog.interfaces.facade.albaran.AlbaranFacade;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import es.zarca.covellog.interfaces.web.empresas.controller.SelectorEmpresaController;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 *
 * @author francisco
 */
@Named("albaranLineaReservarBean")
@ViewScoped
public class AlbaranLineaReservarBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(AlbaranLineaReservarBean.class.getName());
    @Inject
    private AlbaranFacade facade;
    @Inject
    private SelectorEmpresaController selectorEmpresaController;
    private Integer transportistaId;
    @NotNull
    @Size(min = 1, max = 80)
    private String transportistaNombre;
    @Size(min = 1, max = 80)
    private String chofer;
    @Size(max = 20)
    private String matricula;
    private String observaciones;
    
    public AlbaranLineaReservarBean() {
    }

    public Integer getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(Integer transportistaId) {
        this.transportistaId = transportistaId;
    }

    public String getTransportistaNombre() {
        return transportistaNombre;
    }

    public void setTransportistaNombre(String transportistaNombre) {
        this.transportistaNombre = transportistaNombre;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void prepare() {
        transportistaId = null;
        transportistaNombre = "";
        chofer = "";
        matricula = "";
        observaciones = "";
    }
    
    public void prepareSelectTransportista(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            selectorEmpresaController.prepare();
            selectorEmpresaController.setOnSeleccionarUpdate("@(.ReservaEditPanel)");
            selectorEmpresaController.setOnSeleccionarListener((event1) -> {
                  transportistaId = selectorEmpresaController.getSelected().getId();
                  transportistaNombre = selectorEmpresaController.getSelected().getNombre();
            });
            selectorEmpresaController.setOnCancelarListener((ActionEvent event1) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
}