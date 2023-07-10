package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionPostalDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.util.List;

/**
 *
 * @author francisco
 */
public class DetalleFacturacionDto {
    
    private Boolean exentoIva;
    private ContactoDto contacto;
    private List<ContactoDto> contactos;
    private FormaPagoDto formaPagoVenta;
    private FormaPagoDto formaPagoAlquiler;
    private DireccionPostalDto direccionPostal;
    private Boolean malPagador;
    private DobleObservacionDto observaciones;
    
    public DetalleFacturacionDto() {
    }

    public DetalleFacturacionDto(Boolean exentoIva, ContactoDto contacto, List<ContactoDto> contactos, FormaPagoDto formaPagoVenta, FormaPagoDto formaPagoAlquiler, DireccionPostalDto direccionPostal, Boolean malPagador, DobleObservacionDto observaciones) {
        this.exentoIva = exentoIva;
        this.contacto = contacto;
        this.contactos = contactos;
        this.formaPagoVenta = formaPagoVenta;
        this.formaPagoAlquiler = formaPagoAlquiler;
        this.direccionPostal = direccionPostal;
        this.malPagador = malPagador;
        this.observaciones = observaciones;
    }

    public Boolean getExentoIva() {
        return exentoIva;
    }

    public void setExentoIva(Boolean exentoIva) {
        this.exentoIva = exentoIva;
    }

    public ContactoDto getContacto() {
        return contacto;
    }

    public void setContacto(ContactoDto contacto) {
        this.contacto = contacto;
    }

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
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

    public DireccionPostalDto getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostalDto direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public Boolean getMalPagador() {
        return malPagador;
    }

    public void setMalPagador(Boolean malPagador) {
        this.malPagador = malPagador;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }
    
}