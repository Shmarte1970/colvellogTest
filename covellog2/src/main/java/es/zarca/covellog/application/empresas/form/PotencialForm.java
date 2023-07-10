package es.zarca.covellog.application.empresas.form;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class PotencialForm {
    
    private DobleObservacionForm observaciones;
    private Integer contactoId;
    private List<Integer> comerciales = new ArrayList<>();
    public PotencialForm() {
    }

    public PotencialForm(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }
    
    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getContactoId() {
        return contactoId;
    }

    public void setPrioridad(Integer contactoId) {
        this.contactoId = contactoId;
    }

    
    public List<Integer> getComerciales() {
        return comerciales;
    }

    public void setComerciales(List<Integer> comerciales) {
        this.comerciales = comerciales;
    }
    
}
