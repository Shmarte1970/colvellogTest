package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("contratoAsignarAlbaranEntregaBean")
@ViewScoped
public class ContratoAsignarAlbaranEntregaBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoAsignarAlbaranEntregaBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    private List<AlbaranDto> albaranes = new ArrayList();
    private AlbaranDto selected = null;
    private List<ContratoLineaDto> lineasSeleccionadas;
    
    public ContratoDto getContrato() {
        ContratoDto contrato = modificarContratoBean.getContrato();
        ArgumentValidator.isNotNull(contrato, "No hay contrato asociado.");
        return contrato;
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }

    public List<AlbaranDto> getAlbaranes() {
        return albaranes;
    }

    public AlbaranDto getAlbaran(Integer id) {
        for (AlbaranDto albaran : albaranes) {
            if (albaran.getId().equals(id)) {
                return albaran;
            }
        }
        return null;
    }
                
                
    public void setAlbaranes(List<AlbaranDto> albaranes) {
        this.albaranes = albaranes;
    }

    public AlbaranDto getSelected() {
        return selected;
    }

    public void setSelected(AlbaranDto selected) {
        this.selected = selected;
    }
    
    
    public void prepare(List<ContratoLineaDto> lineasSeleccionadas) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            ArgumentValidator.isNotEmpty(lineasSeleccionadas, "Seleccione alguna linea de contrato.");
            this.lineasSeleccionadas = lineasSeleccionadas;
            selected = null;
            albaranes = facade.getAlbaranesEntrega(getContrato().getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void assertAlgunAlbaranSeleccionada() {
        if (selected == null) {
            throw new PresentationException("Seleccione algun albaran de entrega.");
        }
    }
    
    private void assertAlgunLineaSeleccionada() {
        ArgumentValidator.isNotNull(lineasSeleccionadas, "No ha seleccionado ninguna linea de contrato.");
    }
    
    public void onSelect() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertAlgunAlbaranSeleccionada();
            assertAlgunLineaSeleccionada();
            List<Integer> ids = new ArrayList();
            lineasSeleccionadas.forEach(lineaSeleccionada -> {
                ids.add(lineaSeleccionada.getId());
            });
            facade.anadirLineasContratoAAlbaran(getContrato().getId(), ids, getSelected().getId());
            notificarGuardar(null);
        } catch (Exception ex) {
            selected = null;
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void create() {
       FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertAlgunLineaSeleccionada();
            List<Integer> ids = new ArrayList();
            lineasSeleccionadas.forEach(lineaSeleccionada -> {
                ids.add(lineaSeleccionada.getId());
            });
            facade.crearAlbaranEntrega(getContrato().getId(), ids);
            notificarGuardar(null);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        } 
    }
    
    @FacesConverter(value="contratoAsignarAlbaranEntregaDtoConverter")
    public static class ContratoAsignarAlbaranEntregaDtoConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContratoAsignarAlbaranEntregaBean bean = (ContratoAsignarAlbaranEntregaBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "contratoAsignarAlbaranEntregaBean");
            return bean.getAlbaran(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            
            if (object == null) {
                return null;
            }
            if (object instanceof AlbaranDto) {
                AlbaranDto o = (AlbaranDto) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }
    
}