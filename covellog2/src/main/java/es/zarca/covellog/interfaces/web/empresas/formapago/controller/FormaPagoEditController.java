package es.zarca.covellog.interfaces.web.empresas.formapago.controller;

import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.util.crono.Crono;
import es.zarca.covellog.interfaces.facade.empresas.facade.FormaPagoEditFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoLineaDto;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELResolver;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionListener;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("formaPagoEditController")
@ViewScoped
public class FormaPagoEditController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(FormaPagoEditController.class.getName());
    @Inject
    private FormaPagoEditFacade facade;
    
    private ActionListener listener = null;
    private String update = "";
    private FormaPagoDto model = null;
    private List<TipoPago> tiposPagoPosibles = null;
    private List<TipoVencimiento> tiposVencimientoPosibles = null;
    private List<FormaPagoLineaDto> seleccionados = new ArrayList<>();
    private FormaPagoLineaDto formaPagoLinea;    
    private boolean esCreacion;
    
    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public FormaPagoDto getModel() {
        return model;
    }

    public void setModel(FormaPagoDto model) {
        if (model == null) {
            model = new FormaPagoDto();
        }
        this.model = model;
    }

    public List<TipoPago> getTiposPagoPosibles() {
        return tiposPagoPosibles;
    }

    public void setTiposPagoPosibles(List<TipoPago> tiposPagoPosibles) {
        this.tiposPagoPosibles = tiposPagoPosibles;
    }

    public List<TipoVencimiento> getTiposVencimientoPosibles() {
        return tiposVencimientoPosibles;
    }

    public void setTiposVencimientoPosibles(List<TipoVencimiento> tiposVencimientoPosibles) {
        this.tiposVencimientoPosibles = tiposVencimientoPosibles;
    }

    public List<FormaPagoLineaDto> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<FormaPagoLineaDto> seleccionados) {
        this.seleccionados = seleccionados;
    }

    public FormaPagoLineaDto getFormaPagoLinea() {
        return formaPagoLinea;
    }

    public void setFormaPagoLinea(FormaPagoLineaDto formaPagoLinea) {
        this.formaPagoLinea = formaPagoLinea;
    }
    
    public void quitar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (seleccionados.isEmpty()) {
                JsfUtil.addValidationErrorMessage("formaPago", "Seleccione una línea");
                JsfUtil.validationFailed();
            } else {
                seleccionados.forEach(seleccionado -> {
                    model.getLineas().remove(seleccionado);                    
                });
                int i = 0;
                for (FormaPagoLineaDto item : model.getLineas()) {
                    item.setNumLinea(i);
                    LOGGER.log(Level.SEVERE, " CONTROLLER items: {0}", item);
                    i++;
                }
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareEditar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((seleccionados == null) ||(seleccionados.isEmpty())) {
                JsfUtil.addValidationErrorMessage("formaPago", "Seleccione una línea");
                JsfUtil.validationFailed();
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "TITULO", "Seleccione una linea"));
            } else {
                formaPagoLinea = seleccionados.get(0);
                loadDefaultSelectOneValues();
            }
            esCreacion = false;
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareEditar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if ((seleccionados == null) ||(seleccionados.isEmpty())) {
                JsfUtil.addValidationErrorMessage("formaPago", "Seleccione una línea");
                JsfUtil.validationFailed();
                //throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "TITULO", "Seleccione una linea"));
            } else {
                formaPagoLinea = seleccionados.get(0);
                loadDefaultSelectOneValues();
            }
            esCreacion = false;
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareAnadir(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            loadDefaultSelectOneValues();
            formaPagoLinea = new FormaPagoLineaDto();
            esCreacion = true;
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void anularDiaPago() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
           formaPagoLinea.setDiaPago(null);
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }

    public void aceptar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (model.getLineas().isEmpty()) {
                model = null;
            }
            if (listener != null) {
                listener.processAction(event);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelar(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void aceptarEditarLinea(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            if (esCreacion) {
                formaPagoLinea.setNumLinea(model.getLineas().size());
                model.getLineas().add(formaPagoLinea);
            }
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void cancelarEditarLinea(javax.faces.event.ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void loadDefaultSelectOneValues() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            tiposPagoPosibles = facade.getTiposPagosPosibles();
            tiposVencimientoPosibles = facade.getTiposVencimientoPosibles();
        } catch (Exception ex) {
            JsfUtil.addException(ex);
        } finally {
            log.finish();
        }        
    }
 
    @FacesConverter(value="formaPagoEditLineaConverter", forClass = FormaPagoLineaDto.class)
    public static class FormaPagoEditLineaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ELResolver resolver = facesContext.getApplication().getELResolver();
            FormaPagoEditController controller = (FormaPagoEditController)resolver.getValue(
                facesContext.getELContext(), 
                null, 
                "formaPagoEditController"
            );
            return controller.getModel().getLineas().get(Integer.parseInt(value));
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FormaPagoLineaDto) {
                FormaPagoLineaDto o = (FormaPagoLineaDto) object;
                return o.getNumLinea().toString();
            } else {
                return null;
            }
        }
    }
    
    @FacesConverter(value="formaPagoEditTipoPagoConverter")
    public static class FormaPagoEditTipoPagoConverter implements Converter {
        
        private FormaPagoEditController getController(FacesContext facesContext) {
            return (FormaPagoEditController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "formaPagoEditController");
        }

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            for (TipoPago tipoPagoPosible : getController(facesContext).getTiposPagoPosibles()) {
                if (tipoPagoPosible.getId().equals(value)) {
                    return tipoPagoPosible;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoPago) {
                TipoPago o = (TipoPago) object;
                return o.getId();
            } else {
                return null;
            }
        }
    }
    
    @FacesConverter(value="formaPagoEditTipoVencimientoConverter")
    public static class FormaPagoEditTipoVencimientoConverter implements Converter {
        
        private FormaPagoEditController getController(FacesContext facesContext) {
            return (FormaPagoEditController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "formaPagoEditController");
        }

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            System.err.println("getAsObject: " + value);
            if (value == null || value.length() == 0) {
                return null;
            }
            for (TipoVencimiento tipoVencimientoPosible : getController(facesContext).getTiposVencimientoPosibles()) {
                if (tipoVencimientoPosible.getId().equals(value)) {
                    System.err.println("getAsObject: Result " + tipoVencimientoPosible);
                    return tipoVencimientoPosible;
                }
            }
            System.err.println("getAsObject: NULLLL");
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoVencimiento) {
                TipoVencimiento o = (TipoVencimiento) object;
                System.err.println("**************************************************************************getAsString: " + o.getId());
                return o.getId();
            } else {
                return null;
            }
        }
    }
    
}

