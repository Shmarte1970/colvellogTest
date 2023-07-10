package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.ContratoFacade;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoDto;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.web.exception.PresentationException;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.primefaces.PrimeFaces;
/**
 *
 * @author francisco
 */
@Named("contratoLineasBean")
@ViewScoped
public class ContratoLineasBean extends GuardarCancelarBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContratoLineasBean.class.getName());
    @Inject
    private ContratoFacade facade;
    @Inject
    private ModificarContratoBean modificarContratoBean;
    @Inject
    private ContratoLineaModificarBean contratoLineaModificarBean;
    @Inject
    private ContratoAsignarFechaBean contratoAsignarFechaBean;
    @Inject
    private ContratoSolicitarRecogidaBean contratoSolicitarRecogidaBean;
    @Inject
    private ContratoRecogerBean contratoRecogerBean;
    @Inject
    private ContratoAsignarAlbaranEntregaBean contratoAsignarAlbaranEntregaBean;
    @Inject
    private ContratoAsignarAlbaranRecogidaBean contratoAsignarAlbaranRecogidaBean;
    
    private List<ContratoLineaDto> lineas;
    private List<ContratoLineaDto> selecteds;
    private String onSelect;
    private String onCompleteAceptar;
    private Date fecha;
    
    public ContratoDto getContrato() {
        return modificarContratoBean.getContrato();
    }
    
    public void setContrato(ContratoDto contrato) {
        modificarContratoBean.setContrato(contrato);
    }
    
    public List<ContratoLineaDto> getLineas() {
        System.err.println("********************************************** GET LINEAS zz");
        if (lineas == null) {
            System.err.println("********************************************** INTENTO LA CARGA");
            Assert.isNotNull(getContrato(), "El contrato es null.");
            
            lineas = getContrato().getLineas();
            System.err.println("********************************************** LINEAS CARGADAS " + String.valueOf(lineas.size()));
        }
        if (lineas != null) System.err.println("********************************************** LINEAS " + String.valueOf(lineas.size()));
        else System.err.println("********************************************** LINEAS FALLO LA CARGA");
        return lineas;
    }
    
    public ContratoLineaDto getLinea(Integer id) {
        for (ContratoLineaDto linea : getLineas()) {
            if (Objects.equals(id, linea.getId())) {
                return linea;
            }
        }
        return null;
    }

    public void setLineas(List<ContratoLineaDto> lineas) {
        this.lineas = lineas;
    }

    public List<ContratoLineaDto> getSelecteds() {
        return selecteds;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public void onSelect() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
        } catch (Exception e) {
            JsfUtil.addException(e);
        } finally {
            log.finish();
        }        
    }

    public String getOnCompleteAceptar() {
        return onCompleteAceptar;
    }

    public void setOnCompleteAceptar(String onCompleteAceptar) {
        this.onCompleteAceptar = onCompleteAceptar;
    }

    public void setSelecteds(List<ContratoLineaDto> selecteds) {
        this.selecteds = selecteds;
    }

    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((selecteds != null) && (!selecteds.isEmpty())) {
                modificarContratoBean.setAccionActual(ModificarContratoBean.AccionPanel.MODIFICAR_LINEA);
                contratoLineaModificarBean.prepare(selecteds.get(0));
                System.err.println("COJONES QUE ME DICES");
               
                contratoLineaModificarBean.setOnGuardarListener((event) -> {
                    lineas = null;
                });
                contratoLineaModificarBean.setOnCancelarListener((event) -> {                
                    modificarContratoBean.setAccionActual(ModificarContratoBean.AccionPanel.PESTANAS);
                });
            } else {
                JsfUtil.showErrorDialog("Seleccione primero una linea.");
            }
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareNueva() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            contratoLineaModificarBean.prepare();
            modificarContratoBean.setAccionActual(ModificarContratoBean.AccionPanel.MODIFICAR_LINEA);
            contratoLineaModificarBean.setOnGuardarListener((event) -> {
                lineas = null;
            });
            contratoLineaModificarBean.setOnCancelarListener((event) -> {                
                modificarContratoBean.setAccionActual(ModificarContratoBean.AccionPanel.PESTANAS);
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            lineas = null;
            selecteds = new ArrayList();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepare(List<ContratoLineaDto> selecteds) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            lineas = null;
            this.selecteds = selecteds;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    private void assertAlgunaLineaSeleccionada() {
        if ((selecteds == null) || (selecteds.isEmpty())) {
            throw new PresentationException("Seleccione alguna linea");
        }
    }
    
    public void prepareAsignarAlbaranEntregaBean() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarAlbaranEntregaBean.prepare(selecteds);
            contratoAsignarAlbaranEntregaBean.setOnGuardarListener( (event) -> {                
                JsfUtil.addSuccessMessage("Albaran asignado correctamente.");
                setContrato(null);
                lineas = null;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            contratoAsignarAlbaranEntregaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareAsignarAlbaranRecogidaBean() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarAlbaranRecogidaBean.prepare(selecteds);
            contratoAsignarAlbaranRecogidaBean.setOnGuardarListener( (event) -> {                
                JsfUtil.addSuccessMessage("Albaran asignado correctamente.");
                setContrato(null);
                lineas = null;
                PrimeFaces.current().ajax().update(":mi-layout-contenido");
            });
            contratoAsignarAlbaranRecogidaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareModificarFechaEntregaPrevista() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarFechaBean.prepare(selecteds);
            contratoAsignarFechaBean.setTitulo("Fecha Entrega Prevista");
            contratoAsignarFechaBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.lineaModificarFechaEntregaPrevista(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Fecha de entrega cambiada correctamente.");
                setContrato(null);
                lineas = null;
            });
            contratoAsignarFechaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareEntregar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarFechaBean.prepare(selecteds);
            contratoAsignarFechaBean.setTitulo("Entregar");
            contratoAsignarFechaBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.entregarProductos(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Productos entregados correctamente.");
                setContrato(null);
                lineas = null;
            });
            contratoAsignarFechaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareModificarFechaEntrega() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarFechaBean.prepare(selecteds);
            contratoAsignarFechaBean.setTitulo("Modificar Fecha Entrega");
            contratoAsignarFechaBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.modificarFechaEntregaProductos(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Fecha entrega modificada correctamente.");
                setContrato(null);
                lineas = null;
            });
            contratoAsignarFechaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelarEntrega() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            List<Integer> ids = new ArrayList();
            selecteds.forEach(selected -> {
                ids.add(selected.getId());
            });
            facade.cancelarEntregaProductos(getContrato().getId(), ids);
            JsfUtil.addSuccessMessage("Entrega cancelada correctamente.");
            setContrato(null);
            lineas = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareSolicitarRecogida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarFechaBean.prepare(selecteds);
            contratoAsignarFechaBean.setTitulo("Solicitud Recogida");
            contratoAsignarFechaBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.solicitarRecogidaProductos(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Recogida solicitada correctamente.");
                setContrato(null);
                lineas = null;
            });
            contratoAsignarFechaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelarSolicitudRecogida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            List<Integer> ids = new ArrayList();
            selecteds.forEach(selected -> {
                ids.add(selected.getId());
            });
            facade.cancelarSolicitudRecogidaProductos(getContrato().getId(), ids);
            JsfUtil.addSuccessMessage("Solicitud recogida cancelada correctamente.");
            setContrato(null);
            lineas = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareRecoger() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarFechaBean.prepare(selecteds);
            contratoAsignarFechaBean.setTitulo("Recogida");
            contratoAsignarFechaBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.recogerProductos(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Recogida productos efectuada correctamente.");
                setContrato(null);
                lineas = null;
            });
            contratoAsignarFechaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareModificarFechaRecogida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {            
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            contratoAsignarFechaBean.prepare(selecteds);
            contratoAsignarFechaBean.setTitulo("Modificar Fecha Fin Alquiler");
            contratoAsignarFechaBean.setOnGuardarListener( (event) -> {
                List<Integer> ids = new ArrayList();
                selecteds.forEach(selected -> {
                    ids.add(selected.getId());
                });
                facade.modificarFechaRecogidaProductos(getContrato().getId(), ids, contratoAsignarFechaBean.getFecha());
                JsfUtil.addSuccessMessage("Fecha fin alquiler modificada correctamente.");
                setContrato(null);
                lineas = null;
            });
            contratoAsignarFechaBean.setOnCancelarListener( (event) -> {
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelarRecogida() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            List<Integer> ids = new ArrayList();
            selecteds.forEach(selected -> {
                ids.add(selected.getId());
            });
            facade.cancelarRecogidaProductos(getContrato().getId(), ids);
            JsfUtil.addSuccessMessage("Recogida productos cancelada correctamente.");
            setContrato(null);
            lineas = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void eliminarLinea() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getContrato(), "El contrato es null.");
            assertAlgunaLineaSeleccionada();
            List<Integer> ids = new ArrayList();
            selecteds.forEach(selected -> {
                ids.add(selected.getId());
            });
            facade.eliminarLinea(getContrato().getId(), ids);
            setContrato(null);
            lineas = null;
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }

    @FacesConverter(value="ContratoLineaConverter")
    public static class ContratoLineaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContratoLineasBean bean = (ContratoLineasBean) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "contratoLineasBean");
            return bean.getLinea(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ContratoLineaDto) {
                ContratoLineaDto f = (ContratoLineaDto) object;
                return f.getId().toString();
            } else {
                LOGGER.log(Level.SEVERE, "ContratoLineaDto: object {0} is of type {1}; expected type: {2}", 
                    new Object[]{object, object.getClass().getName(), ContratoLineaDto.class.getName()});
                return null;
            }
        }
    }

}