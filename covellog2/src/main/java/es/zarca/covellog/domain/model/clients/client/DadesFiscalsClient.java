/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.empresa.Cif;
import es.zarca.covellog.domain.model.adreces.adreça.Adreça;
import javax.persistence.Embeddable;
import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DadesFiscalsClient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Embedded
    protected Cif cif;
    @Basic(optional = false)
    @NotNull
    @Column(name = "exent_iva")
    protected Boolean exentIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iban")
    protected String iban;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="adreça",column=@Column(name="adr_fiscal_adreça")),
        @AttributeOverride(name="codigo",column=@Column(name="adr_fiscal_codi_postal"))
    })
    @AssociationOverrides({  
        @AssociationOverride(name = "poblacio", joinColumns = @JoinColumn(name = "adr_fiscal_poblacio_id"))
    })
    protected Adreça adreça;

    public DadesFiscalsClient() {
    }

    public DadesFiscalsClient(Cif cif, Boolean exentIva, String iban, Adreça adreça) {
        this.cif = cif;
        this.exentIva = exentIva;
        this.iban = iban;
        this.adreça = adreça;
    }

    public Cif getCif() {
        return cif;
    }

    public Boolean getExentIva() {
        return exentIva;
    }

    public String getIban() {
        return iban;
    }

    public Adreça getAdreça() {
        return adreça;
    }
 
}
