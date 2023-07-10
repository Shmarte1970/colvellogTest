package es.zarca.covellog.application.adreces.form;

/**
 *
 * @author francisco
 */
public class DireccionForm {
    protected String direccion;
    protected String direccion2;
    protected String codigoPostal;
    protected Integer poblacionId;

    public DireccionForm(String direccion, String direccion2, String codigoPostal, Integer poblacionId) {
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.codigoPostal = codigoPostal;
        this.poblacionId = poblacionId;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public Integer getPoblacionId() {
        return poblacionId;
    }
    
}