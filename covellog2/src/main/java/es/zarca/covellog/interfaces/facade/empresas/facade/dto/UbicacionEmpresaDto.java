package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */

public class UbicacionEmpresaDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    @NotNull
    private List<ContactoDto> contactos;
    @NotNull
    @Size(min = 1, max = 200)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 255)
    private String descripcion;
    @NotNull
    @Size(min = 1, max = 200)
    private String horario;
    @NotNull
    private DireccionDto direccion;
    @NotNull
    private DobleObservacionDto observaciones = new DobleObservacionDto();
    private Date fechaBaja;
    private EmpresaDto empresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public EmpresaDto getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDto empresa) {
        this.empresa = empresa;
    }

}