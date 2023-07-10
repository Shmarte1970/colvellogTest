/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.form;

import es.zarca.covellog.interfaces.facade.adreces.facade.form.AdreçaForm;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ContactePrincipalForm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nom;
    private String carrec;
    private PersonaContacteForm contacte;
    
}
