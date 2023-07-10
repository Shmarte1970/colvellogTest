package es.zarca.covellog.interfaces.facade.adreces.facade.dto;

/**
 *
 * @author usuario
 */
public class DireccionDto implements Cloneable {
    private static final long serialVersionUID = 1L;

    private String direccion;
    private String direccion2;
    private String codigoPostal;
    private PoblacioDTO poblacion;

    public DireccionDto() {
    }

    public DireccionDto(String direccion, String direccion2, String codigoPostal, PoblacioDTO poblacion) {
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.codigoPostal = codigoPostal;
        this.poblacion = poblacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public PoblacioDTO getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(PoblacioDTO poblacion) {
        this.poblacion = poblacion;
    }
    
}
