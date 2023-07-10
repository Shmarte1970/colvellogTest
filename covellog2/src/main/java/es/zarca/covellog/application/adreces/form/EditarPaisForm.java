/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.adreces.form;

/**
 *
 * @author francisco
 */
public class EditarPaisForm extends AltaPaisForm {
    private Integer id;

    public EditarPaisForm(String nom) {
        super(nom);
    }

    public EditarPaisForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
