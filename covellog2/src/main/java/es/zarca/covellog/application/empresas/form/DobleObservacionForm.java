package es.zarca.covellog.application.empresas.form;

import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class DobleObservacionForm {
    @Size(max = 65535)
    private String obsInternas;
    @Size(max = 65535)
    private String obsPublicas;

    public DobleObservacionForm() {
    }

    public DobleObservacionForm(String obsInternas, String obsPublicas) {
        this.obsInternas = obsInternas;
        this.obsPublicas = obsPublicas;
    }

    public String getObsInternas() {
        return obsInternas;
    }

    public void setObsInternas(String obsInternas) {
        this.obsInternas = obsInternas;
    }

    public String getObsPublicas() {
        return obsPublicas;
    }

    public void setObsPublicas(String obsPublicas) {
        this.obsPublicas = obsPublicas;
    }
    
}
