package es.zarca.covellog.application.contratos.form;

import es.zarca.covellog.application.adreces.form.DireccionPostalForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.FormaPagoDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoFacturacionForm {    
    private Boolean exentoIva;
    private List<Integer> contactos;
    private FormaPagoDto formaPagoVenta;
    private FormaPagoDto formaPagoAlquiler;
    private DireccionPostalForm direccionPostal;
    private DobleObservacionForm observaciones;

    public Boolean getExentoIva() {
        return exentoIva;
    }

    public void setExentoIva(Boolean exentoIva) {
        this.exentoIva = exentoIva;
    }

    public List<Integer> getContactos() {
        return contactos;
    }

    public void setContactos(List<Integer> contactos) {
        this.contactos = contactos;
    }

    public FormaPagoDto getFormaPagoVenta() {
        return formaPagoVenta;
    }

    public void setFormaPagoVenta(FormaPagoDto formaPagoVenta) {
        this.formaPagoVenta = formaPagoVenta;
    }

    public FormaPagoDto getFormaPagoAlquiler() {
        return formaPagoAlquiler;
    }

    public void setFormaPagoAlquiler(FormaPagoDto formaPagoAlquiler) {
        this.formaPagoAlquiler = formaPagoAlquiler;
    }

    public DireccionPostalForm getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostalForm direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }

}