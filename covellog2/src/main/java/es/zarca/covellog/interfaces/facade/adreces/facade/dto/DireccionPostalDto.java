package es.zarca.covellog.interfaces.facade.adreces.facade.dto;

/**
 *
 * @author usuario
 */
public class DireccionPostalDto extends DireccionDto implements Cloneable {
    private static final long serialVersionUID = 1L;

    private String att;

    public DireccionPostalDto() {
    }
    
    public DireccionPostalDto(String att, String direccion, String direccion2, String codigoPostal, PoblacioDTO poblacion) {
        super(direccion, direccion2, codigoPostal, poblacion);
        this.att = att;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }
    
}
