package es.zarca.covellog.domain.model.empresa.cliente;

import es.zarca.covellog.domain.model.adreces.adreça.DireccionPostal;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Embeddable
public class DetalleFacturacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detfact_exento_iva")
    final private boolean exentoIva;
    /*@ManyToOne(optional = true)
    @JoinColumn(name = "detfact_contacto_id", referencedColumnName = "id")
    final private Contacto contacto;
    @ManyToMany
    @JoinTable(
        name = "empresa_cliente_contactos_facturacion", 
        joinColumns = @JoinColumn(name = "empresa_id"), 
        inverseJoinColumns = @JoinColumn(name = "contacto_id")
    )*/
    @Transient
    final private List<Contacto> contactos;
    @Transient
    final private FormaPago formaPagoVenta;
    @Transient
    final private FormaPago formaPagoAlquiler;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="att", column=@Column(name="detfact_direccion_att")),
        @AttributeOverride(name="direccion", column=@Column(name="detfact_direccion_direccion")),
        @AttributeOverride(name="direccion2", column=@Column(name="detfact_direccion_direccion2")),
        @AttributeOverride(name="codigoPostal.codigo", column=@Column(name="detfact_direccion_codigo_postal")),
    })
    @AssociationOverrides({
        @AssociationOverride(name = "poblacion", joinColumns = @JoinColumn(name = "detfact_direccion_poblacion_id"))
    })
    private DireccionPostal direccionPostal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "detfact_mal_pagador")
    final private boolean malPagador;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="obsInternas", column=@Column(name="detfact_obs_internas")),
        @AttributeOverride(name="obsPublicas", column=@Column(name="detfact_obs_publicas")),
    })
    protected DobleObservacion observaciones;

    public DetalleFacturacion() {
        exentoIva = false;
        //contacto = null;
        formaPagoVenta = null;
        formaPagoAlquiler = null;
        malPagador = false;
        contactos = new ArrayList<>();
    }
    
    public DetalleFacturacion(boolean exentoIva, List<Contacto> contactos, FormaPago formaPagoVenta, FormaPago formaPagoAlquiler, DireccionPostal direccionPostal, boolean malPagador, DobleObservacion observaciones) {
        this.exentoIva = exentoIva;
        //this.contacto = contacto;
        this.contactos = contactos;
        this.formaPagoVenta = formaPagoVenta;
        this.formaPagoAlquiler = formaPagoAlquiler;
        this.direccionPostal = direccionPostal;
        this.malPagador = malPagador;
        this.observaciones = observaciones;
    }
    
    public boolean isExentoIva() {
        return exentoIva;
    }

    /*public Contacto getContacto() {
        return contacto;
    }
*/
    public List<Contacto> getContactos() {
        return contactos;
    }

    public FormaPago getFormaPagoVenta() {
        return formaPagoVenta;
    }

    public FormaPago getFormaPagoAlquiler() {
        return formaPagoAlquiler;
    }
    
    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }

    public boolean isMalPagador() {
        return malPagador;
    }

    public DobleObservacion getObservaciones() {
        return observaciones;
    }

}