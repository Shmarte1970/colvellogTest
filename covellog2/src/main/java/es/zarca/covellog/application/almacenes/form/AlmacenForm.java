package es.zarca.covellog.application.almacenes.form;

import es.zarca.covellog.application.adreces.form.DireccionForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class AlmacenForm {
    private Integer empresaId;
    @NotNull
    @Size(min = 1, max = 200)
    private String nombre;
    private String descripcion;
    private DireccionForm direccion;
    private List<Integer> contactosIds = new ArrayList<>();
    private String horario;
    private DobleObservacionForm observaciones;

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
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
    
    public DireccionForm getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionForm direccion) {
        this.direccion = direccion;
    }

    public List<Integer> getContactosIds() {
        return contactosIds;
    }

    public void setContactosIds(List<Integer> contactosIds) {
        this.contactosIds = contactosIds;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }
    
}
