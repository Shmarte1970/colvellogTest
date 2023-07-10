package es.zarca.covellog.application.empresas.form;

import es.zarca.covellog.application.adreces.form.DireccionPostalForm;

/**
 *
 * @author francisco
 */
public class ProveedorForm {
    
    private String codigoProveedor;
    private Integer contactoId;
    private Integer contactoFacturacionId;
    private DireccionPostalForm direccionPostal;
    private DobleObservacionForm observaciones;

    public ProveedorForm() {
    }

    public ProveedorForm(String codigoProveedor, Integer contactoId, Integer contactoFacturacionId, DireccionPostalForm direccionPostal, DobleObservacionForm observaciones) {
        this.codigoProveedor = codigoProveedor;
        this.contactoId = contactoId;
        this.contactoFacturacionId = contactoFacturacionId;
        this.direccionPostal = direccionPostal;
        this.observaciones = observaciones;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public Integer getContactoId() {
        return contactoId;
    }

    public void setContactoId(Integer contactoId) {
        this.contactoId = contactoId;
    }

    public Integer getContactoFacturacionId() {
        return contactoFacturacionId;
    }

    public void setContactoFacturacionId(Integer contactoFacturacionId) {
        this.contactoFacturacionId = contactoFacturacionId;
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
