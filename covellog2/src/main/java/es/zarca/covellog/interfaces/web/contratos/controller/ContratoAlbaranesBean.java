package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.albaran.dto.AlbaranDto;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.web.albaranes.controller.ModificarAlbaranBean;
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
import org.eclipse.persistence.jpa.jpql.Assert;
/**
 *
 * @author francisco
 */
@Named("contratoAlbaranesBean")
@ViewScoped
public class ContratoAlbaranesBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoAlbaranesBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private ModificarAlbaranBean modificarAlbaranBean;
    private List<AlbaranDto> albaranesEntregaSeleccionados;
    private List<AlbaranDto> albaranesEntrega;
    private List<AlbaranDto> albaranesRecogidaSeleccionados;
    private List<AlbaranDto> albaranesRecogida;
    
    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }

    public List<AlbaranDto> getAlbaranesEntregaSeleccionados() {
        return albaranesEntregaSeleccionados;
    }
    
    public AlbaranDto getAlbaranEntregaSeleccionado() {
        if ((albaranesEntregaSeleccionados != null) && (!albaranesEntregaSeleccionados.isEmpty())) {
            return albaranesEntregaSeleccionados.get(0);
        }
        return null;
    }

    public void setAlbaranesEntregaSeleccionados(List<AlbaranDto> albaranesEntregaSeleccionados) {
        this.albaranesEntregaSeleccionados = albaranesEntregaSeleccionados;
    }
    
    public List<AlbaranDto> getAlbaranesEntrega() {
        return albaranesEntrega;
    }

    public void setAlbaranesEntrega(List<AlbaranDto> albaranesEntrega) {
        this.albaranesEntrega = albaranesEntrega;
    }

    public List<AlbaranDto> getAlbaranesRecogidaSeleccionados() {
        return albaranesRecogidaSeleccionados;
    }
    
    public AlbaranDto getAlbaranRecogidaSeleccionado() {
        if ((albaranesRecogidaSeleccionados != null) && (!albaranesRecogidaSeleccionados.isEmpty())) {
            return albaranesRecogidaSeleccionados.get(0);
        }
        return null;
    }

    public void setAlbaranesRecogidaSeleccionados(List<AlbaranDto> albaranesRecogidaSeleccionados) {
        this.albaranesRecogidaSeleccionados = albaranesRecogidaSeleccionados;
    }

    public List<AlbaranDto> getAlbaranesRecogida() {
        return albaranesRecogida;
    }

    public void setAlbaranesRecogida(List<AlbaranDto> albaranesRecogida) {
        this.albaranesRecogida = albaranesRecogida;
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            Assert.isNotNull(getContrato(), "El contrato es null.");
            System.err.println("COJONES AHORA: LA VISTA LLAMA FACADE");
            albaranesEntrega = facade.getAlbaranesEntrega(getContrato().getId());
            albaranesEntregaSeleccionados = new ArrayList(); 
            System.err.println("COJONES AHORA: LA VISTA VUELVE FACADE " + albaranesEntrega.size());
            albaranesRecogida = facade.getAlbaranesRecogida(getContrato().getId());
            albaranesRecogidaSeleccionados = new ArrayList(); 
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void assertAlgunAlbaranEntregaSeleccionada() {
        if ((albaranesEntregaSeleccionados == null) || (albaranesEntregaSeleccionados.isEmpty())) {
            throw new PresentationException("Seleccione alguna albaran de entrega.");
        }
    }
    
    public void prepareAlbaranEntregaModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertAlgunAlbaranEntregaSeleccionada();
            modificarAlbaranBean.prepare(albaranesEntregaSeleccionados.get(0).getId());
            modificarContratoBean.setAccionActual(ModificarContratoBean.AccionPanel.MODIFICAR_ALBARAN_ENT);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void assertAlgunAlbaranRecogidaSeleccionada() {
        if ((albaranesRecogidaSeleccionados == null) || (albaranesRecogidaSeleccionados.isEmpty())) {
            throw new PresentationException("Seleccione alguna albaran de recogida.");
        }
    }
    
    public void prepareAlbaranRecogidaModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertAlgunAlbaranRecogidaSeleccionada();
            modificarAlbaranBean.prepare(albaranesRecogidaSeleccionados.get(0).getId());
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminarAlbaranRecogida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
           // assertAlgunAlbaranEntregaSeleccionada();
            albaranesEntregaSeleccionados.forEach(albaranEntregaSeleccionado -> {
          //      facade.eliminarAlbaranEntrega(albaranEntregaSeleccionado.getId());
            });
            setContrato(facade.findById(getContrato().getId()));
            prepare();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminarAlbaranEntrega() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            assertAlgunAlbaranEntregaSeleccionada();
            albaranesEntregaSeleccionados.forEach(albaranEntregaSeleccionado -> {
                facade.eliminarAlbaranEntrega(albaranEntregaSeleccionado.getId());
            });
            setContrato(facade.findById(getContrato().getId()));
            prepare();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void openAlbaranEntrega() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getApplication().getNavigationHandler().handleNavigation(context, null, "/albaranes/modificar?faces-redirect=true&id=" + albaranesEntregaSeleccionados.get(0).getId());
        
    }
    
    public void openAlbaranRecogida() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getApplication().getNavigationHandler().handleNavigation(context, null, "/albaranes/modificar?faces-redirect=true&id=" + albaranesRecogidaSeleccionados.get(0).getId());
        
    }
    
    @FacesConverter(value="contratoAlbaranDtoConverter")
    public static class ContratoAlbaranDtoConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContratoAlbaranesBean ContratoAlbaranesBean = (ContratoAlbaranesBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "contratoAlbaranesBean");
            for (AlbaranDto albaran : ContratoAlbaranesBean.getAlbaranesEntrega()) {
                if (albaran.getId().equals(Integer.parseInt(value))) {
                    return albaran;
                }
            }
            for (AlbaranDto albaran : ContratoAlbaranesBean.getAlbaranesRecogida()) {
                if (albaran.getId().equals(Integer.parseInt(value))) {
                    return albaran;
                }
            }
            return null;
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
    
 /*   @FacesConverter(value="contratoAlbaranDtoConverter")
    public static class ContratoAlbaranDtoConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContratoAlbaranesBean ContratoAlbaranesBean = (ContratoAlbaranesBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "contratoAlbaranesBean");
            for (AlbaranDto albaran : ContratoAlbaranesBean.getAlbaranesRecogida()) {
                if (albaran.getId().equals(Integer.parseInt(value))) {
                    return albaran;
                }
            }
            return null;
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
    }*/
}