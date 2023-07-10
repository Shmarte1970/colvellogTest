package es.zarca.covellog.interfaces.web.idiomas.converter;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.IdiomaDto;
import es.zarca.covellog.interfaces.web.idiomas.controller.SelectorIdiomaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author francisco
 */
@FacesConverter(value="idiomaConverter", forClass = IdiomaDto.class)
public class IdiomaConverter implements Converter {

    private static final Logger LOGGER = Logger.getLogger(IdiomaConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        try {
            if (value == null || value.length() == 0) {
                return null;
            }
            SelectorIdiomaController controller = (SelectorIdiomaController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "selectorIdiomaController");
            return controller.getIdioma(value);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        try {
            if (object == null) {
                return null;
            }
            if (object instanceof IdiomaDto) {
                IdiomaDto o = (IdiomaDto) object;
                return o.getId();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }
}
