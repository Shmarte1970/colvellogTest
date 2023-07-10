package es.zarca.covellog.interfaces.web.components;

/**
 *
 * @author francisco
 */
import es.zarca.covellog.interfaces.facade.transporte.dto.TransporteDto;
import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent("transporteSelectorComponent")
public class TransporteSelectorComponent extends UINamingContainer {
    private TransporteDto seleccionado;

    public TransporteDto getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(TransporteDto seleccionado) {      
        this.seleccionado = seleccionado;
    }
    
    public void asignarSeleccionado() {
        TransporteDto selected = (TransporteDto) getAttributes().get("selected");
        //getAttributes().put("selected", seleccionado);
        MethodExpression actionListener = (MethodExpression) getAttributes().get("actionListener");
        Object params[] = { null };
        actionListener.invoke(FacesContext.getCurrentInstance().getELContext(), params) ;  
    }
}