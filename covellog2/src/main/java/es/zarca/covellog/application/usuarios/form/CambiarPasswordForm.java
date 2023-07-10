/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.usuarios.form;

import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
public class CambiarPasswordForm {
    @Size(min = 3, max = 45)
    private String password;
    @Size(min = 3, max = 45)
    private String password2;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    
    
}
