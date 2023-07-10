/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.adreces.form;

import es.zarca.covellog.application.form.DefaultAltaForm;

/**
 *
 * @author francisco
 */
public class AltaPaisForm extends DefaultAltaForm {
    private String nom;
    private String codiIso2;

    public AltaPaisForm() {
        super();
    }
   
    public AltaPaisForm(String nom) {
        super();
        this.nom = nom;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodiIso2() {
        return codiIso2;
    }

    public void setCodiIso2(String codiIso2) {
        this.codiIso2 = codiIso2;
    }

}
