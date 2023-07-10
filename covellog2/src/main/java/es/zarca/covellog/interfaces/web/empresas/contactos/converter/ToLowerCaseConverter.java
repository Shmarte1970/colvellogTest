package es.zarca.covellog.interfaces.web.empresas.contactos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author usuario
 */
@FacesConverter("toLowerCaseConverter")
public class ToLowerCaseConverter implements Converter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (!(modelValue instanceof String)) { 
            return null; // Or throw ConverterException.
        }

        return ((String) modelValue).toLowerCase();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null) { 
            return null;
        }

        return submittedValue.toLowerCase();
    }

}