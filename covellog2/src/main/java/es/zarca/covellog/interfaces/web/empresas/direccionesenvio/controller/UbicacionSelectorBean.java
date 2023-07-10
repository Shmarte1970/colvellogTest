package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.controller;

import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import es.zarca.covellog.interfaces.web.app.controller.Selector;
import es.zarca.covellog.interfaces.web.empresas.direccionesenvio.model.DireccionEnvioEditModel;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.helpers.JsfUtil;
import java.io.Serializable;
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
@Named("ubicacionSelectorBean")
@ViewScoped
public class UbicacionSelectorBean extends Selector<UbicacionDto> implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(UbicacionSelectorBean.class.getName());
    @Inject
    private DireccionEnvioEditController direccionEnvioEditController;
    
    public UbicacionSelectorBean() {
    }

    private UbicacionDto getUbicacion(int id) {
        for (UbicacionDto ubicacionDto : getItems()) {
            if ((ubicacionDto != null) && (ubicacionDto.getId().equals(id))) {
                return ubicacionDto;
            }
        }
        return null;
    }

    @Override
    public Object getItemRowKey(UbicacionDto item) {
        if (item == null) {
            return false;
        }
        return item.getId();
    }

    @Override
    public boolean hasRowKey(UbicacionDto item, String rowKey) {
        if ( (item == null) || (item.getId() == null) ) {
            return false;
        }
        
        return (item.getId().toString().equals(rowKey));
    }
    
    @FacesConverter(value="ubicacionSelectorBeanConverter")
    public static class DireccionEnvioSelectorConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                System.err.println("COJONES CHUNGOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!: ");
                return null;
            }
            UbicacionSelectorBean controller = (UbicacionSelectorBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "ubicacionSelectorBean");
            UbicacionDto contacto = controller.getUbicacion(Integer.parseInt(value));
            System.err.println("COJONES Ubicacion: " + contacto);
            return contacto;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UbicacionDto) {
                UbicacionDto o = (UbicacionDto) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }

}
