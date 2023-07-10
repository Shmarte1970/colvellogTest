package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ProveedorDto implements Serializable {
    private String codigoProveedor = "";
    private List<ComercialDto> comerciales = new ArrayList<>();
    private List<ContactoDto> contactos;
    private DireccionPostalDto direccionPostal;
    private DobleObservacionDto observaciones;
    private FormaPagoDto formaPago;
    private String fechaBloqueo;

    public ProveedorDto() {
        comerciales = new ArrayList<>();
        observaciones = new DobleObservacionDto();
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public List<ComercialDto> getComerciales() {
        return comerciales;
    }

    public void setComerciales(List<ComercialDto> comerciales) {
        this.comerciales = comerciales;
    }

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public DireccionPostalDto getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostalDto direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public FormaPagoDto getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPagoDto formaPago) {
        this.formaPago = formaPago;
    }

    public String getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

}