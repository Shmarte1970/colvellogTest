package es.zarca.covellog.interfaces.facade.ubicaciones.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author francisco
 */

public class UbicacionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private EmpresaMiniDto empresa;
    private List<ContactoDto> contactos;
    private String nombre;
    private String descripcion;
    private String horario;
    private DireccionDto direccion;
    private DobleObservacionDto observaciones = new DobleObservacionDto();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmpresaMiniDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaMiniDto empresa) {
        this.empresa = empresa;
    }
    
    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public DireccionDto getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDto direccion) {
        this.direccion = direccion;
    }

    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

}