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
public class AltaProvinciaForm extends DefaultAltaForm {
    private String nom;
    private String codi;
    private String codiPostal;
    private Integer idPais;
    
    public AltaProvinciaForm(String nom) {
        super();
        this.nom = nom;
    }

    public AltaProvinciaForm() {
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }
    
    
}
