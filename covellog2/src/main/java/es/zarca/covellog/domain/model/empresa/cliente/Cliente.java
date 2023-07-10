package es.zarca.covellog.domain.model.empresa.cliente;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.ContactoRol;
import es.zarca.covellog.domain.model.empresa.EmpresaRol;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "empresa_cliente")
@XmlRootElement
public class Cliente extends EmpresaRol implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CONTACTOS_KEY = "CLIENTE";
    @Embedded
    private CodigoCliente codigoCliente;
    /*@ManyToOne(optional = true)
    @JoinColumn(name = "contacto_principal_id", referencedColumnName = "id")
    private Contacto contacto;*/
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "tipo_cliente_id")
    private TipoCliente tipoCliente;
    @Embedded
    private DetalleContratacion detalleContratacion;
    @Embedded
    private DetalleFacturacion detalleFacturacion;
    @ManyToMany
    @JoinTable(
        name = "empresa_cliente_comerciales", 
        joinColumns = @JoinColumn(name = "empresa_id"), 
        inverseJoinColumns = @JoinColumn(name = "comercial_id")
    )
    private List<Comercial> comerciales;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<ClienteFormaPagoLineaVenta> formaPagoVenta = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<ClienteFormaPagoLineaAlquiler> formaPagoAlquiler = new ArrayList<>();
    
    public Cliente() {
    }

    public CodigoCliente getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(CodigoCliente codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public List<Contacto> getContactos() {
        return empresa.getContactos(new ContactoRol(CONTACTOS_KEY));
    }

    public void setContactos(List<Contacto> contactos) {
        empresa.contactosModificarRoles(contactos, new ContactoRol(CONTACTOS_KEY));
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public DetalleContratacion getDetalleContratacion() {
        return detalleContratacion;
    }

    public void setDetalleContratacion(DetalleContratacion detalleContratacion) {
        this.detalleContratacion = detalleContratacion;
    }

    public DetalleFacturacion getDetalleFacturacion() {
        
        /*for (Contacto contacto: empresa.getContactos(new ContactoRol("CLI_FACTU"))) {
            System.err.println("zzz XXX getDetalleFacturacion " + contacto.getNombre());
        }*/
        return new DetalleFacturacion(
            detalleFacturacion.isExentoIva(), 
            //detalleFacturacion.getContacto(), 
            //detalleFacturacion.getContactos(), 
            empresa.getContactos(new ContactoRol("CLI_FACTU")),
            getFormaPagoVenta(), 
            getFormaPagoAlquiler(),
            detalleFacturacion.getDireccionPostal(),
            detalleFacturacion.isMalPagador(),
            detalleFacturacion.getObservaciones()
        );
    }

    public void setDetalleFacturacion(DetalleFacturacion detalleFacturacion) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        ContactoRol rolFactu = new ContactoRol("CLI_FACTU");
        setFormaPagoAlquiler(detalleFacturacion.getFormaPagoAlquiler());
        setFormaPagoVenta(detalleFacturacion.getFormaPagoVenta());
        empresa.contactosModificarRoles(detalleFacturacion.getContactos(), rolFactu);
        this.detalleFacturacion = detalleFacturacion;
        log.finish();
    }

    public List<Comercial> getComerciales() {
        return comerciales;
    }

    public void setComerciales(List<Comercial> comerciales) {
        this.comerciales = comerciales;
    }
    
    private FormaPago getFormaPagoAlquiler() {
        if ((formaPagoAlquiler == null) || (formaPagoAlquiler.isEmpty())) {
            return null;
        }
        List<FormaPagoLinea> lineas = new ArrayList<>();
        formaPagoAlquiler.forEach((linea) -> {
            lineas.add(linea);
        });
        return new FormaPago(lineas);
    }

    private void setFormaPagoAlquiler(FormaPago formaPago) {
        int i = 0;
        if (formaPago != null) {
            for (i = 0; i < formaPago.getLineas().size(); i++) {
                FormaPagoLinea lineaNueva = formaPago.getLineas().get(i);
                ClienteFormaPagoLineaAlquiler lineaVieja;
                if (i < this.formaPagoAlquiler.size()) {
                    lineaVieja = this.formaPagoAlquiler.get(i);
                } else {
                    lineaVieja = new ClienteFormaPagoLineaAlquiler();
                    this.formaPagoAlquiler.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setCliente(this);            
                lineaVieja.setPorcentaje(lineaNueva.getPorcentaje());
                lineaVieja.setTipoPago(lineaNueva.getTipoPago());
                lineaVieja.setCuenta(lineaNueva.getCuenta());
                lineaVieja.setTipoVencimiento(lineaNueva.getTipoVencimiento());
                lineaVieja.setDiaPago(lineaNueva.getDiaPago());
            }
        }
        while (i < formaPagoAlquiler.size()) {
            formaPagoAlquiler.remove(i);
        }
    }
    
    private FormaPago getFormaPagoVenta() {
        if ((formaPagoVenta == null) || (formaPagoVenta.isEmpty())) {
            return null;
        }
        List<FormaPagoLinea> lineas = new ArrayList<>();
        formaPagoVenta.forEach((linea) -> {
            lineas.add(linea);
        });
        return new FormaPago(lineas);
    }

    private void setFormaPagoVenta(FormaPago formaPago) {
        int i = 0;
        if (formaPago != null) {
            for (i = 0; i < formaPago.getLineas().size(); i++) {
                FormaPagoLinea lineaNueva = formaPago.getLineas().get(i);
                ClienteFormaPagoLineaVenta lineaVieja;
                if (i < this.formaPagoVenta.size()) {
                    lineaVieja = this.formaPagoVenta.get(i);
                } else {
                    lineaVieja = new ClienteFormaPagoLineaVenta();
                    this.formaPagoVenta.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setCliente(this);            
                lineaVieja.setPorcentaje(lineaNueva.getPorcentaje());
                lineaVieja.setTipoPago(lineaNueva.getTipoPago());
                lineaVieja.setCuenta(lineaNueva.getCuenta());
                lineaVieja.setTipoVencimiento(lineaNueva.getTipoVencimiento());
                lineaVieja.setDiaPago(lineaNueva.getDiaPago());
            }
        }
        while (i < formaPagoVenta.size()) {
            formaPagoVenta.remove(i);
        }
    }
    
    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.Cliente[ id=" + empresaId + " ]";
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