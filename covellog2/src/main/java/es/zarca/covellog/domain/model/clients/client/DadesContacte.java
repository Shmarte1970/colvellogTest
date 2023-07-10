/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DadesContacte implements Serializable {

    private static final long serialVersionUID = 1L;
    //Telefon
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "telefon" )
    protected String telefon;
    //Movil
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "movil" )
    protected String movil;
    //FAX
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "fax" )
    protected String fax;
    //Email
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "email" )
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",  message="{invalid.email}")
    protected String email;

    public DadesContacte() {
    }

    public DadesContacte(String telefon, String movil, String fax, String email) {
        this.telefon = telefon;
        this.movil = movil;
        this.fax = fax;
        this.email = email;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getMovil() {
        return movil;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }
    
}
