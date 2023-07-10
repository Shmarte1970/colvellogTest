package es.zarca.covellog.application.albaranes.form;

import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class ModificarAlbaranInfoForm {
    @NotNull
    private Date fecha;
    /*private Integer transporteId;
    private Integer almacenId;
    private DireccionForm direccion;*/
    @Size(max = 100)
    private String textoAviso;
    private DobleObservacionForm observaciones;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /*public Integer getTransporteId() {
        return transporteId;
    }

    public void setTransporteId(Integer transporteId) {
        this.transporteId = transporteId;
    }

    public Integer getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Integer almacenId) {
        this.almacenId = almacenId;
    }

    public DireccionForm getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionForm direccion) {
        this.direccion = direccion;
    }*/

    public String getTextoAviso() {
        return textoAviso;
    }

    public void setTextoAviso(String textoAviso) {
        this.textoAviso = textoAviso;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
