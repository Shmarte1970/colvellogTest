package es.zarca.covellog.interfaces.web.contratos.controller;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.facade.contrato.dto.ContratoLineaDto;
import es.zarca.covellog.interfaces.web.helpers.ExceptionHandler;
import es.zarca.covellog.interfaces.web.app.controller.GuardarCancelarBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
/**
 *
 * @author francisco
 */
@Named("contratoLineaSelectorBean")
@ViewScoped
public class ContratoLineaSelectorBean extends GuardarCancelarBean implements Serializable {
    private List<ContratoLineaDto> items;
    private List<ContratoLineaDto> selecteds;
    
    public ContratoLineaSelectorBean() {
    }

    public List<ContratoLineaDto> getItems() {
        return items;
    }

    public void setItems(List<ContratoLineaDto> items) {
        this.items = items;
    }

    public List<ContratoLineaDto> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<ContratoLineaDto> selecteds) {
        this.selecteds = selecteds;
    }
    
    public void prepare(List<ContratoLineaDto> items) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            this.items = items;
            selecteds = new ArrayList();
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @Override
    public void guardar(ActionEvent event) {
        FunctionLog log = new FunctionLog(Capa.CONTROLLER);
        try {
            notificarGuardar(event);
        } catch (Exception ex) {
            ExceptionHandler.handleException(ex);
        } finally {
            log.finish();
        }
    }
    
    @FacesConverter(value="contratoLineaSelectorConverter")
    public static class ContratoLineaSelectorConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContratoLineaSelectorBean contratoLineaDto = (ContratoLineaSelectorBean) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "contratoLineaSelectorBean");
            for (ContratoLineaDto linea : contratoLineaDto.getItems()) {
                if (linea.getId().equals(Integer.parseInt(value))) {
                    return linea;
                }
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            
            if (object == null) {
                return null;
            }
            if (object instanceof ContratoLineaDto) {
                ContratoLineaDto o = (ContratoLineaDto) object;
                return o.getId().toString();
            } else {
                return null;
            }
        }
    }
    

}