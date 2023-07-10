package es.zarca.covellog.application.usuarios.form;

import java.io.Serializable;

/**
 *
 * @author francisco
 */
public class EditarUsuarioForm extends AltaUsuarioForm implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
