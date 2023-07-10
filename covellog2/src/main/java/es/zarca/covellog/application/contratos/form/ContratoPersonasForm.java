package es.zarca.covellog.application.contratos.form;

import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoPersonasForm {
    private List<Integer> comercialesId;
    private Integer firmanteId;
    private List<Integer> contactosId;

    public List<Integer> getComercialesId() {
        return comercialesId;
    }

    public void setComercialesId(List<Integer> comercialesId) {
        this.comercialesId = comercialesId;
    }

    public Integer getFirmanteId() {
        return firmanteId;
    }

    public void setFirmanteId(Integer firmanteId) {
        this.firmanteId = firmanteId;
    }

    public List<Integer> getContactosId() {
        return contactosId;
    }

    public void setContactosId(List<Integer> contactosId) {
        this.contactosId = contactosId;
    }
   
}