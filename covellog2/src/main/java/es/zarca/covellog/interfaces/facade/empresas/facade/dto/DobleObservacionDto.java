package es.zarca.covellog.interfaces.facade.empresas.facade.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class DobleObservacionDto implements Serializable {
    @Size(max = 10000)
    private String obsInternas;
    @Size(max = 10000)
    private String obsPublicas;

    public DobleObservacionDto() {
        this.obsInternas = "";
        this.obsPublicas = "";
    }

    public DobleObservacionDto(String obsInternas, String obsPublicas) {
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
