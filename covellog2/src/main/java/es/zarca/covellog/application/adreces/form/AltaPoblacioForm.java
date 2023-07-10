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
public class AltaPoblacioForm extends DefaultAltaForm {
    protected String nom;
    protected Integer idProvincia;
    protected Integer idPais;

    public AltaPoblacioForm() {
        super();
        this.nom = "";
        this.idProvincia = 0;
    }
    
    public AltaPoblacioForm(String nom, Integer idProvincia) {
        super();
        this.nom = nom;
        this.idProvincia = idProvincia;
    }
    
    public AltaPoblacioForm(String nom, Integer idProvincia, String motiu) {
        super(motiu);
        this.nom = nom;
        this.idProvincia = idProvincia;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        System.out.println("Cambio nombre: " + nom);
        this.nom = nom;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        System.out.println("Cambio provincia: " + idProvincia);
        this.idProvincia = idProvincia;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        System.out.println("Cambio pais: " + idPais);
        this.idPais = idPais;
    }
    
    

}
