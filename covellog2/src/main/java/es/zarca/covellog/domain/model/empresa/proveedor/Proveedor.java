package es.zarca.covellog.domain.model.empresa.proveedor;
import es.zarca.covellog.domain.model.adreces.adreça.DireccionPostal;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.ContactoRol;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.EmpresaContacto;
import es.zarca.covellog.domain.model.empresa.EmpresaRol;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_proveedor")
@XmlRootElement
public class Proveedor extends EmpresaRol implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(Proveedor.class.getName());
    private static final long serialVersionUID = 1L;
    private static final String CONTACTOS_KEY = "PROVEEDOR";
    
    @Embedded
    private CodigoProveedor codigoProveedor;
    /*@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "contacto_principal_id", referencedColumnName = "id")
    private EmpresaContacto contacto;*/
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "facturacion_contacto_id", referencedColumnName = "id")
    private EmpresaContacto contactoFacturacion;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="att", column=@Column(name="direccion_postal_att")),
        @AttributeOverride(name="direccion", column=@Column(name="direccion_postal_direccion")),
        @AttributeOverride(name="direccion2", column=@Column(name="direccion_postal_direccion2")),
        @AttributeOverride(name="codigoPostal.codigo", column=@Column(name="direccion_postal_codigo_postal")),
    })
    @AssociationOverrides({
        @AssociationOverride(name = "poblacion", joinColumns = @JoinColumn(name = "direccion_postal_poblacion_id"))
    })
    private DireccionPostal direccionPostal;
    @ManyToMany
    @JoinTable(
        name = "empresa_proveedor_comerciales", 
        joinColumns = @JoinColumn(name = "empresa_id"), 
        inverseJoinColumns = @JoinColumn(name = "comercial_id")
    )
    private List<Comercial> comerciales;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "proveedor", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<ProveedorFormaPagoLinea> formaPago = new ArrayList<>();
    
    public Proveedor() {
    }

    public Proveedor(CodigoProveedor codigoProveedor, List<Contacto> contactos, Contacto contactoFacturacion, DireccionPostal direccionPostal, DobleObservacion observaciones) {
        this.codigoProveedor = codigoProveedor;
        empresa.contactosModificarRoles(contactos, new ContactoRol(CONTACTOS_KEY));
     //xxxx   this.contactoFacturacion = empresa.getC contactoFacturacion;
        this.direccionPostal = direccionPostal;
        this.observaciones = observaciones;
    }
    
    public CodigoProveedor getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(CodigoProveedor codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public List<Contacto> getContactos() {
        return empresa.getContactos(new ContactoRol(CONTACTOS_KEY));
    }

    public void setContactos(List<Contacto> contactos) {
        empresa.contactosModificarRoles(contactos, new ContactoRol(CONTACTOS_KEY));
    }

    public Contacto getContactoFacturacion() {
        return contactoFacturacion;
    }

    public void setContactoFacturacion(Contacto contactoFacturacion) {
        //xxxx this.contactoFacturacion = contactoFacturacion;
    }

    public DireccionPostal getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostal direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public List<Comercial> getComerciales() {
        return comerciales;
    }

    public void setComerciales(List<Comercial> comerciales) {
        this.comerciales = comerciales;
    }

    public FormaPago getFormaPago() {
        if ((formaPago == null) || (formaPago.isEmpty())) {
            return null;
        }
        List<FormaPagoLinea> lineas = new ArrayList<>();
        formaPago.forEach((linea) -> {
            lineas.add(linea);
        });
        return new FormaPago(lineas);
    }

    public void setFormaPago(FormaPago formaPago) {
        int i = 0;
        if (formaPago != null) {
            for (i = 0; i < formaPago.getLineas().size(); i++) {
                FormaPagoLinea lineaNueva = formaPago.getLineas().get(i);
                ProveedorFormaPagoLinea lineaVieja;
                if (i < this.formaPago.size()) {
                    lineaVieja = this.formaPago.get(i);
                } else {
                    lineaVieja = new ProveedorFormaPagoLinea();
                    this.formaPago.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setProveedor(this);            
                lineaVieja.setPorcentaje(lineaNueva.getPorcentaje());
                lineaVieja.setTipoPago(lineaNueva.getTipoPago());
                lineaVieja.setCuenta(lineaNueva.getCuenta());
                lineaVieja.setTipoVencimiento(lineaNueva.getTipoVencimiento());
                lineaVieja.setDiaPago(lineaNueva.getDiaPago());
            }
        }
        while (i < this.formaPago.size()) {
            this.formaPago.remove(i);
        }
    }
    
    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.Proveedor[ id=" + empresaId + " ]";
    }

    public void bloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        fechaBloqueo = new Date();
        log.finish();
    }

    public void desbloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        fechaBloqueo = null;
        log.finish();
    }
    
}
