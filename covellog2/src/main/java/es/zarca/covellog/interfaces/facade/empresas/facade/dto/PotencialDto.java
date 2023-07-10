package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author francisco
 */
public class PotencialDto implements Serializable {
    private DobleObservacionDto observaciones;
    private List<ComercialDto> comerciales;
    private String fechaBloqueo;
    private List<ContactoDto> contactos;

    public PotencialDto() {
        comerciales = new ArrayList<>();
        observaciones = new DobleObservacionDto();
    }
    
    public PotencialDto(List<ComercialDto> comerciales, List<ContactoDto> contactos, DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
        this.contactos = contactos;
        this.comerciales = comerciales;
    }
    
    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public List<ComercialDto> getComerciales() {
        return comerciales;
    }

    public void setComerciales(List<ComercialDto> comerciales) {
        this.comerciales = comerciales;
    }

    public String getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }
    
}