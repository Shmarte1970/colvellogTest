/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.form;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class DefaultAltaForm {
    protected String motiu;
    protected Date data;

    public DefaultAltaForm() {
        this.motiu = "";
        this.data = new Date();
    }

    public DefaultAltaForm(String motiu) {
        this.motiu = motiu;
        this.data = new Date();
    }
    
    public String getMotiu() {
        return motiu;
    }

    public void setMotiu(String motiu) {
        this.motiu = motiu;
    }

    public Date getData() {
        return data;
    }
    
    
     
}
