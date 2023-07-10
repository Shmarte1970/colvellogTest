package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
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
/**
 *
 * @author francisco
 */
@Named("contratoRecogerBean")
@ViewScoped
public class ContratoRecogerBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoRecogerBean.class.getName());
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private ContratoFacade facade;
    @NotNull
    private List<ContratoLineaDto> selecteds;
    private Date fecha;
    private Date fechaPicker;    

    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaPicker() {
        return fechaPicker;
    }

    public void setFechaPicker(Date fechaPicker) {
        this.fechaPicker = fechaPicker;        
    }
    
    public void udpateFecha() {
        fecha = fechaPicker;
    }
    
    public void udpateCalendar() {
        fechaPicker = fecha;
    }
    
    public void prepare(List<ContratoLineaDto> lineas) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            selecteds = lineas;
            fecha = null;
            fechaPicker = null;
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
            List<Integer> ids = new ArrayList();
            for (ContratoLineaDto selected : selecteds) {
                ids.add(selected.getId());
            }
            facade.recogerProductos(getContrato().getId(), ids, fecha);
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

}