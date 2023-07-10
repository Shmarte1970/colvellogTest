package es.zarca.covellog.interfaces.web.empresas.contactos.converter;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.web.empresas.contactos.controller.SelectorContactosController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author usuario
 */
@FacesConverter(value="contactoConverter", forClass = ContactoDto.class)
public class ContactoConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        SelectorContactosController controller = (SelectorContactosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "selectorContactosController");
        ContactoDto contacto = controller.getContacto(Integer.parseInt(value));
        System.err.println("Contacto: " + contacto);
        return contacto;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ContactoDto) {
            ContactoDto o = (ContactoDto) object;
            return o.getId().toString();
        } else {
            return null;
        }
    }

}