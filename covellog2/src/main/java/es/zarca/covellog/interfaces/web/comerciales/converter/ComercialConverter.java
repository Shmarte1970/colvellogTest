package es.zarca.covellog.interfaces.web.comerciales.converter;

import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.web.comerciales.controller.SelectorComercialesController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author francisco
 */
@FacesConverter(value="comercialConverter", forClass = ComercialDto.class)
public class ComercialConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        SelectorComercialesController controller = (SelectorComercialesController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "selectorComercialesController");
        return controller.getComercial(Integer.valueOf(value));
    }


    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ComercialDto) {
            ComercialDto o = (ComercialDto) object;
            return o.getId().toString();
        } else {
            return null;
        }
    }

}
