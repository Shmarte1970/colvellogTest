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
public class DefaultEditarForm extends DefaultUpdateForm {
      
    public DefaultEditarForm() {
        super();
    }
    
    public DefaultEditarForm(Integer id) {
        super(id);
    }

    public DefaultEditarForm(Integer id, String motiu) {
        super(id, motiu);
    }
         
}
