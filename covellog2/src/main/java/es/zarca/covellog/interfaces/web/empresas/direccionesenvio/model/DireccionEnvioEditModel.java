package es.zarca.covellog.interfaces.web.empresas.direccionesenvio.model;

import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DireccionEnvioDto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionListener;

/**
 *
 * @author francisco
 */
public class DireccionEnvioEditModel {
    private ActionListener listener = null;
    private ActionListener cancelarListener = null;
    private String update = "";
    private DireccionEnvioDto ubicacion;    
    private List<ContactoDto> contactosPosibles = new ArrayList<>();
    private Integer empresaId;
            
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

    public DireccionEnvioDto getUbicacion() {
        return ubicacion;
    }

    public void setDireccionEnvio(DireccionEnvioDto ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<ContactoDto> getContactosPosibles() {
        return contactosPosibles;
    }

    public void setContactosPosibles(List<ContactoDto> contactosPosibles) {
        this.contactosPosibles = contactosPosibles;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public ActionListener getCancelarListener() {
        return cancelarListener;
    }

    public void setCancelarListener(ActionListener cancelarListener) {
        this.cancelarListener = cancelarListener;
    }

}
