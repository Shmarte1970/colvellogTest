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
public class DefaultUpdateForm {
    protected Integer id;
    protected String motiu;
    protected Date data;

    public DefaultUpdateForm() {
        this.motiu = "";
        this.data = new Date();
    }
    
    public DefaultUpdateForm(Integer id) {
        this.id = id;
        this.motiu = "";
        this.data = new Date();
    }

    public DefaultUpdateForm(Integer id, String motiu) {
        this.id = id;
        this.motiu = motiu;
        this.data = new Date();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
