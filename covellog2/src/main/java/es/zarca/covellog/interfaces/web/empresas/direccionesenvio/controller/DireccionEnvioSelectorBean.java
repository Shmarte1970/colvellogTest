package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.EmpresaServiceFacade;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.web.app.controller.Selector;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("direccionEnvioSelectorBean")
@ViewScoped
public class DireccionEnvioSelectorBean extends Selector<DireccionEnvioDto> implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(DireccionEnvioSelectorBean.class.getName());
    @Inject
    private DireccionEnvioEditBean direccionEnvioEditBean;
    @Inject
    private EmpresaServiceFacade facade; 
    private Integer empresaId;
    
    public DireccionEnvioSelectorBean() {
    }    

    @Override
    public List<DireccionEnvioDto> getItems() {
        if (items == null) {
            items = facade.findDireccionesEnvio(empresaId);
        }
        return items;
    }
    
    private DireccionEnvioDto getDireccionEnvio(int id) {
        for (DireccionEnvioDto direccionEnvioDto : getItems()) {
            if ((direccionEnvioDto != null) && (direccionEnvioDto.getId().equals(id))) {
                return direccionEnvioDto;
            }
        }
        return null;
    }

    @Override
    public Object getItemRowKey(DireccionEnvioDto item) {
        if (item == null) {
            return false;
        }
        return item.getId();
    }

    @Override
    public boolean hasRowKey(DireccionEnvioDto item, String rowKey) {
        if ( (item == null) || (item.getId() == null) ) {
            return false;
        }
        
        return (item.getId().toString().equals(rowKey));
    }
    
    public void prepare(Integer empresaId) {
        this.empresaId = empresaId;
        items = null;
        selected = null;
    }
    
    public void prepareModificar() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            ArgumentValidator.isNotNull(getSelected(), "Seleccione alguna dirección de envío.");
            direccionEnvioEditBean.prepare(empresaId, getSelected().getId());
            direccionEnvioEditBean.setOnGuardarJsfUpdate("@(.DireccionEnvioSelectorForm)");
            direccionEnvioEditBean.setOnGuardarListener((ActionEvent event) -> {
                items = null;
            });
        } catch (Exception ex) {
            JsfUtil.validationFailed();
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    public void prepareAnadir() {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            direccionEnvioEditBean.prepare(empresaId);
            direccionEnvioEditBean.setOnGuardarJsfUpdate("@(.DireccionEnvioSelectorForm)");
            direccionEnvioEditBean.setOnGuardarListener((ActionEvent event) -> {
                items = null;
            });
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="direccionEnvioSelectorBeanConverter")
    public static class DireccionEnvioSelectorConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DireccionEnvioSelectorBean controller = (DireccionEnvioSelectorBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "direccionEnvioSelectorBean");
            DireccionEnvioDto contacto = controller.getDireccionEnvio(Integer.parseInt(value));
            return contacto;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DireccionEnvioDto) {
                DireccionEnvioDto o = (DireccionEnvioDto) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }

}
