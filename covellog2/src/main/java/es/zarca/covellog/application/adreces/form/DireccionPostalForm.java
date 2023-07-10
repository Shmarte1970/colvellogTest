package es.zarca.covellog.application.adreces.form;

/**
 *
 * @author francisco
 */
public class DireccionPostalForm extends DireccionForm {
    private String att;

    public DireccionPostalForm(String att, String direccion, String direccion2, String codigoPostal, Integer poblacionId) {
        super(direccion, direccion2, codigoPostal, poblacionId);
        this.att = att;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }
    
}