package es.zarca.covellog.application.productos.familias.form;

import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class NuevaFamiliaProductoForm {
    @Size(min = 3, max = 45)
    private String nombre;
    @Size(max = 255)
    private String observaciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

}