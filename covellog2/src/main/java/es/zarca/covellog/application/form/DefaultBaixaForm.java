/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.form;

/**
 *
 * @author francisco
 */
public class DefaultBaixaForm extends DefaultUpdateForm {

    public DefaultBaixaForm() {
        super();
    }
    
    public DefaultBaixaForm(Integer id) {
        super(id);
    }

    public DefaultBaixaForm(Integer id, String motiu) {
        super(id, motiu);
    }
    
    
}
