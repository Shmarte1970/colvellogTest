    package es.zarca.covellog.domain.model.empresa;

import es.zarca.covellog.domain.model.empresa.cliente.Cliente;
import es.zarca.covellog.application.empresas.exception.ContactoNotExistException;
import es.zarca.covellog.application.empresas.exception.EmpresaNoTieneClienteException;
import es.zarca.covellog.application.empresas.exception.EmpresaNoTienePotencialException;
import es.zarca.covellog.application.empresas.exception.EmpresaNoTieneProveedorException;
import es.zarca.covellog.application.empresas.exception.EmpresaYaEraClienteException;
import es.zarca.covellog.application.empresas.exception.EmpresaYaEraPotencialException;
import es.zarca.covellog.application.empresas.exception.EmpresaYaEraProveedorException;
import es.zarca.covellog.application.empresas.exception.IntentoEliminarContactoEnUsoException;
import es.zarca.covellog.application.exception.UndefinedBussinesException;
import es.zarca.covellog.domain.model.empresa.proveedor.Proveedor;
import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.domain.model.adreces.adreça.DireccionPostal;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.empresa.cliente.CodigoCliente;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.domain.model.empresa.cliente.TipoCliente;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.proveedor.CodigoProveedor;
import es.zarca.covellog.domain.model.exception.ContactoEliminarInexistenteException;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findById", query = "SELECT e FROM Empresa e WHERE e.id = :id"),
    @NamedQuery(name = "Empresa.findByCif", query = "SELECT e FROM Empresa e WHERE e.cif = :cif"),
    @NamedQuery(name = "Empresa.findByNombre", query = "SELECT e FROM Empresa e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empresa.findByAlias", query = "SELECT e FROM Empresa e WHERE e.alias = :alias"),
    @NamedQuery(name = "Empresa.findByWeb", query = "SELECT e FROM Empresa e WHERE e.web = :web")
})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(Empresa.class.getName());
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    private Cif cif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;    
    @Basic(optional = false)
    @Size(max = 100)
    @Column(name = "empresa_alias")
    private String alias;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="direccion", column=@Column(name="fiscal_direccion")),
        @AttributeOverride(name="direccion2", column=@Column(name="fiscal_direccion2")),
        @AttributeOverride(name="codigoPostal.codigo", column=@Column(name="fiscal_codigo_postal")),
    })
    @AssociationOverrides({
        @AssociationOverride(name = "poblacion", joinColumns = @JoinColumn(name = "fiscal_poblacion_id"))
    })
    private Direccion direccionFiscal;
    @Size(max = 200)
    @Column(name = "horario")
    private String horario;
    @JoinColumn(name = "idioma_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Idioma idioma;
    @Size(max = 200)
    @Column(name = "web")
    private String web;
    @Embedded
    private PalabrasClave palabrasClave;
   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa", fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "empresa_empresa_tipo_rol", 
        joinColumns = @JoinColumn(name = "empresa_id"), 
        inverseJoinColumns = @JoinColumn(name = "tipo_rol_id")
    )
    private List<EmpresaTipoRol> roles = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("prioridad ASC")
    private List<EmpresaContacto> contactos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<EmpresaDireccionEnvio> direccionesEnvio;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "empresa", fetch = FetchType.EAGER, orphanRemoval = true)
    private Potencial potencial;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "empresa", fetch = FetchType.EAGER, orphanRemoval = true)
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "empresa", fetch = FetchType.EAGER, orphanRemoval = true)
    private Proveedor proveedor;
    
    public Empresa() {
        alias = "";
        web = "";
        horario = "";
        palabrasClave = new PalabrasClave();
         
    }

    public Empresa(Integer id) {
        this.id = id;
    }

    public Empresa(Integer id, String nombre, String alias) {
        this.id = id;
        this.nombre = nombre;
        this.alias = alias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cif getCif() {
        return cif;
    }

    public void setCif(Cif cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public String getAliasNombre() {
        if (alias.isEmpty()) return nombre;
        return alias;
    }

    public Direccion getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(Direccion direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
    
    public PalabrasClave getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(PalabrasClave palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public List<EmpresaTipoRol> getRoles() {
        return roles;
    }
/*
    public void setRoles(List<EmpresaTipoRol> roles) {
        this.roles = roles;
    }
  */  

    public List<Contacto> getContactos() {
        return new ArrayList(contactos);
    }
    
    public List<Contacto> getContactos(ContactoRol rol) {      
        List<ContactoContactoRolRelation> relaciones = new ArrayList<>();
        contactos.forEach(contacto -> {
            if (contacto.hasRole(rol)) {
                relaciones.add(contacto.getRolRelation(rol));                
            }           
        });
        relaciones.sort(Comparator.comparing(ContactoContactoRolRelation::getNivel));
        List<Contacto> contactosFiltrados = new ArrayList<>(relaciones.size());
        relaciones.forEach(relacion -> {
            contactosFiltrados.add(relacion.getContacto());
        });
        return contactosFiltrados;
    }
    
    public Contacto getContacto(Integer contactoId) {
        for (Contacto contacto : contactos) {
            if (Objects.equals(contacto.getId(), contactoId)) {
                return contacto;
            }
        }
        throw new ContactoNotExistException(this, contactoId);
    }
    
    private EmpresaContacto getEmpresaContacto(Integer contactoId) {
        for (EmpresaContacto contacto : contactos) {
            if (Objects.equals(contacto.getId(), contactoId)) {
             return contacto;
            }
        }
        throw new ContactoNotExistException(this, contactoId);
    }
    
    private List<EmpresaContacto> getEmpresaContactos(List<Contacto> contactos) {
        List<EmpresaContacto> contactosEmpresa = new ArrayList();
        for (Contacto contacto : contactos) {
            contactosEmpresa.add(getEmpresaContacto(contacto.getId()));
        }
        return contactosEmpresa;
    }
    
    public void contactosModificarRoles(List<? extends Contacto> contactos, ContactoRol rol) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        int i = 0;
        //Anade los contactos nuevos
        for (Contacto contactoNuevo: contactos) {
            EmpresaContacto contacto = getEmpresaContacto(contactoNuevo.getId());
            contacto.setRole(rol, i);
            i++;
        }
        getContactos(rol).stream().filter(contactoViejo -> (!contactos.contains(contactoViejo))).forEachOrdered(contactoViejo -> {
            contactoViejo.removeRole(rol);
        });
        log.finish();
    }
            
    public void contactoAnadir(String nombre, String apellidos, CanalesContacto canalesContacto, String descripcion, Idioma idioma, String observaciones, String horario) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        EmpresaContacto contactoNuevo = new EmpresaContacto(nombre, apellidos, canalesContacto, descripcion, idioma, observaciones, horario);
        contactoNuevo.setPrioridad(contactos.size() + 1);
        contactoNuevo.setEmpresa(this);
        contactos.add(contactoNuevo);
        log.finish();
    }
    
    public void contactoModificar(Integer contactoId, String nombre, String apellidos, CanalesContacto canalesContacto, String descripcion, Idioma idioma, String observaciones, String horario) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        EmpresaContacto contactoModif = getEmpresaContacto(contactoId);
        LOGGER.log(Level.INFO, "Modificar Contacto: {0}:{1}", new Object[]{contactoModif.getId(), contactoModif.getNombre()});
        contactoModif.setEmpresa(this);
        contactoModif.setNombre(nombre);
        contactoModif.setApellidos(apellidos);
        contactoModif.setCanalesContacto(canalesContacto);
        contactoModif.setDescripcion(descripcion);
        contactoModif.setIdioma(idioma);
        contactoModif.setObservaciones(observaciones);
        contactoModif.setHorario(horario);
        log.finish();
    }
    
    private boolean seUsaEnDireccionEnvio(Contacto contacto) {
        for (EmpresaDireccionEnvio direccionEnvio : direccionesEnvio) {
            if (direccionEnvio.getContactos().contains(contacto)) {
                return true;
            }
        }
        return false;
    }
    
    public void contactoEliminar(Contacto contacto) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        EmpresaContacto empresaContacto = null;
        Integer pos = contactos.indexOf(contacto);
        if (pos >= 0) {
            empresaContacto = contactos.get(pos);
        }
        if (empresaContacto == null) {
            throw new ContactoEliminarInexistenteException(this, contacto);
        }
        if (seUsaEnDireccionEnvio(empresaContacto)) {
            throw new IntentoEliminarContactoEnUsoException(contacto, "Direccion Envio");
        }
                    /*if (getProveedor() != null) {
                        if (getProveedor().getContactos().contains(contacto)) {
                            throw new IntentoEliminarContactoEnUsoException(contacto, "Proveedor - EmpresaContacto");
                        }
                    }
                    if (getCliente() != null) {
                        if (getCliente().getContactos().contains(contacto)) {
                            throw new IntentoEliminarContactoEnUsoException(contacto, "Cliente - EmpresaContacto");
                        }
                        if (getCliente().getDetalleFacturacion().getContactos().contains(contacto)) {
                            throw new IntentoEliminarContactoEnUsoException(contacto, "Cliente - EmpresaContacto facturación");
                        }
                    }*/
        contactos.remove(empresaContacto);
        log.finish();
    }
    
    public void contactoSubir(Contacto contacto) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        Integer pos = contactos.indexOf(contacto);
        if (pos < 0) {
            throw new UndefinedBussinesException("El contacto no pertenece a la empresa");
        }
        if (pos == 0) {
            throw new UndefinedBussinesException("El contacto ya tiene la máxima prioridad.");
        }
        EmpresaContacto desplazado = contactos.get(pos - 1);
        contactos.set(pos - 1, contactos.get(pos));
        contactos.set(pos, desplazado);        
        for(int i=0;i<contactos.size();i++) {
            contactos.get(i).setPrioridad(i);
        }
        log.finish();
    }
    
    public void contactoBajar(Contacto contacto) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        Integer pos = contactos.indexOf(contacto);
        if (pos < 0) {
            throw new UndefinedBussinesException("El contacto no pertenece a la empresa");
        }
        if (pos == contactos.size()) {
            throw new UndefinedBussinesException("El contacto ya tiene la mínima prioridad.");
        }
        EmpresaContacto desplazado = contactos.get(pos + 1);
        contactos.set(pos + 1, contactos.get(pos));
        contactos.set(pos, desplazado);
        for(int i=0;i<contactos.size();i++) {
            contactos.get(i).setPrioridad(i);
        }
        log.finish();
    }
    

    public Potencial getPotencial() {
        return potencial;
    }
    
    public void setPotencial(Potencial potencial) {
        this.potencial = potencial;
        if (potencial == null) {
            EmpresaTipoRol rol = new EmpresaTipoRol(2);
            if (roles.contains(rol)) {
                roles.remove(rol);
            }
            contactosModificarRoles(new ArrayList(), new ContactoRol("POTENCIAL"));
        } else {
            EmpresaTipoRol rol = new EmpresaTipoRol(2);
            if (!roles.contains(rol)) {
                roles.add(rol);
            }
            setCliente(null);
        }
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente == null) {
            EmpresaTipoRol rol = new EmpresaTipoRol(3);
            if (roles.contains(rol)) {
                roles.remove(rol);
            }
            contactosModificarRoles(new ArrayList(), new ContactoRol("CLIENTE"));
            contactosModificarRoles(new ArrayList(), new ContactoRol("CLI_FACTU"));
        } else {
            EmpresaTipoRol rol = new EmpresaTipoRol(3);
            if (!roles.contains(rol)) {
                roles.add(rol);
            }
            setPotencial(null);
        }
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }
    
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        if (proveedor == null) {
            EmpresaTipoRol rol = new EmpresaTipoRol(1);
            if (roles.contains(rol)) {
                roles.remove(rol);
            }
            contactosModificarRoles(new ArrayList<Contacto>(), new ContactoRol("PROVEEDOR"));
            contactosModificarRoles(new ArrayList<Contacto>(), new ContactoRol("PROV_FACTU"));
        } else {
            EmpresaTipoRol rol = new EmpresaTipoRol(1);
            if (!roles.contains(rol)) {
                roles.add(rol);
            }
        }
    }
    
    public List<EmpresaDireccionEnvio> getDireccionesEnvio() {
        return direccionesEnvio;
    }
    
    public EmpresaDireccionEnvio getDireccionEnvio(Integer direccionEnvioId) {
        for (EmpresaDireccionEnvio direccionEnvio : direccionesEnvio) {
            if (Objects.equals(direccionEnvio.getId(), direccionEnvioId)) {
             return direccionEnvio;
            }
        }
        throw new ContactoNotExistException(this, direccionEnvioId);
    }

    public void direccionEnvioCrear(
        String nombre,
        String descripcion,
        List<Contacto> contactos,
        String horario,
        Direccion direccion,
        DobleObservacion observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        ArgumentValidator.required(direccion, "direccion");
        //Assert.required(contactoPrincipal, "contacto");
        EmpresaDireccionEnvio direccionEnvio = new EmpresaDireccionEnvio();
        direccionEnvio.setEmpresa(this);
        direccionEnvio.setNombre(nombre);
        direccionEnvio.setDescripcion(descripcion);
        direccionEnvio.setContactos(contactos);
        direccionEnvio.setHorario(horario);
        direccionEnvio.setDireccion(direccion);
        direccionEnvio.setObservaciones(observaciones);
        if (direccionesEnvio == null) {
            direccionesEnvio = new ArrayList<>();
        }
        direccionesEnvio.add(direccionEnvio);
        log.finish();
        
    }
    
    public void direccionEnvioModificar(
        Integer id,
        String nombre,
        String descripcion,
        List<Contacto> contactos,
        String horario,
        Direccion direccion,
        DobleObservacion observaciones
    ) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        ArgumentValidator.required(direccion, "direccion");
        //Assert.required(contactoPrincipal, "contacto");
        EmpresaDireccionEnvio direccionEnvio = getDireccionEnvio(id);
        direccionEnvio.setEmpresa(this);
        direccionEnvio.setNombre(nombre);
        direccionEnvio.setDescripcion(descripcion);
        direccionEnvio.setContactos(contactos);
        direccionEnvio.setHorario(horario);
        direccionEnvio.setDireccion(direccion);
        direccionEnvio.setObservaciones(observaciones);
        log.finish();
    }

    
    public void direccionEnvioEliminar(EmpresaDireccionEnvio direccionEnvio) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        direccionesEnvio.remove(direccionEnvio);
        log.finish();
    }

    public Boolean isCliente() {
        return (getCliente() != null);
    }
    
    public Boolean isProveedor() {
        return (getProveedor() != null);
    }
    
    public Boolean isPotencial() {
        return (getPotencial() != null);
    }
    
    public Boolean isPosibleCrearRolPotencial() {
        return (getCliente() == null) && (getPotencial() == null);
    }
    
    public Boolean isPosibleConvertirPotencialEnCliente() {
        return (getCliente() == null) && (getPotencial() != null);
    }
    
    public Boolean isPosibleCrearRolCliente() {
        return (getCliente() == null) && (getPotencial() == null);
    }
    
    public Boolean isPosibleConvertirClienteEnComercial() {
        return (getCliente() != null) && (getPotencial() == null);
    }
    
    public Boolean isPosibleCrearRolProveedor() {
        return (getProveedor() == null);
    }
        
    
    public void potencialCrear(List<Comercial> comerciales, List<Contacto> contactos, DobleObservacion observaciones) {
        if (getPotencial() != null) {
            throw new EmpresaYaEraPotencialException(this);
        }
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        Potencial potencialAux = new Potencial();
        potencialAux.setEmpresa(this);
        potencialAux.setContactos(contactos);
        comerciales.forEach(comercial -> {
            potencialAux.addComercial(comercial);
        });
        potencialAux.setObservaciones(observaciones);
        setPotencial(potencialAux);
        log.finish();
    }
    
    public void potencialModificar(List<Comercial> comerciales, List<Contacto> contactos, DobleObservacion observaciones) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        Potencial potencialAux = getPotencial();
        if (potencialAux == null) {
            throw new EmpresaNoTienePotencialException(this);
        }
        potencialAux.setContactos(contactos);
        potencialAux.setComerciales(comerciales);
        potencialAux.setObservaciones(observaciones);
        setPotencial(potencialAux);
        log.finish();
    }
    
    public void potencialEliminar() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        Potencial potencialAux = getPotencial();
        if (potencialAux == null) {
            throw new EmpresaNoTienePotencialException(this);
        }
        setPotencial(null);
        log.finish();
    }

    public void potencialBloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getPotencial() == null) {
            throw new EmpresaNoTienePotencialException(this);
        }
        getPotencial().bloquear();
        log.finish();
    }

    public void potencialDesbloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getPotencial() == null) {
            throw new EmpresaNoTienePotencialException(this);
        }
        getPotencial().desbloquear();
        log.finish();
    }

    
    public void clienteCrear(CodigoCliente codigoCliente, TipoCliente tipoCliente, List<Comercial> comerciales, List<Contacto> contactos, DobleObservacion dobleObservacion) {
        if (getCliente() != null) {
            throw new EmpresaYaEraClienteException(this);
        }
        Cliente clienteAux = new Cliente();
        clienteAux.setEmpresa(this);
        //datos formulario
        clienteAux.setCodigoCliente(codigoCliente);
        clienteAux.setTipoCliente(tipoCliente);
        clienteAux.setComerciales(comerciales);
        clienteAux.setContactos(contactos);
        clienteAux.setObservaciones(dobleObservacion);
        //datos por defecto
        clienteAux.setDetalleContratacion(new DetalleContratacion());
        clienteAux.setDetalleFacturacion(new DetalleFacturacion());
        setCliente(clienteAux);
    }

    public void clienteModificar(CodigoCliente codigoCliente, TipoCliente tipoCliente, List<Comercial> comerciales, List<Contacto> contactos, DobleObservacion dobleObservacion) {
        if (getCliente() == null) {
            throw new EmpresaNoTieneClienteException(this);
        }
        Cliente clienteAux = getCliente();
        clienteAux.setCodigoCliente(codigoCliente);
        clienteAux.setTipoCliente(tipoCliente);
        clienteAux.setComerciales(comerciales);
        clienteAux.setContactos(contactos);
        clienteAux.setObservaciones(dobleObservacion);
        setCliente(clienteAux);
    }
    
    public void clienteEliminar() {
        if (getCliente() == null) {
            throw new EmpresaNoTieneClienteException(this);
        }
        setCliente(null);
    }

    public void clienteBloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getCliente() == null) {
            throw new EmpresaNoTieneClienteException(this);
        }
        getCliente().bloquear();
        log.finish();
    }

    public void clienteDesbloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getCliente() == null) {
            throw new EmpresaNoTieneClienteException(this);
        }
        getCliente().desbloquear();
        log.finish();
    }
    
    public void clienteConvertirEnPotencial() {
        if (getCliente() == null) {
            throw new EmpresaNoTieneClienteException(this);
        }
        Potencial potencialAux = new Potencial();
        potencialAux.setEmpresa(this);
        potencialAux.setComerciales(getCliente().getComerciales());
        potencialAux.setContactos(getCliente().getContactos());
        potencialAux.setObservaciones(getCliente().getObservaciones());
        setPotencial(potencialAux);
    }
    
    
    public void proveedorCrear(CodigoProveedor codigoProveedor, List<Comercial> comerciales, List<Contacto> contactos, FormaPago formaPago, DireccionPostal direccion, DobleObservacion dobleObservacion) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getProveedor() != null) {
            throw new EmpresaYaEraProveedorException(this);
        }
        //Assert.required(contacto, "contacto");
        Proveedor proveedorAux = new Proveedor();
        proveedorAux.setEmpresa(this);
        //datos formulario
        proveedorAux.setCodigoProveedor(codigoProveedor);
        proveedorAux.setComerciales(comerciales);
        proveedorAux.setContactos(contactos);
        proveedorAux.setFormaPago(formaPago);
        proveedorAux.setDireccionPostal(direccion);
        proveedorAux.setObservaciones(dobleObservacion);
        setProveedor(proveedorAux);
        log.finish();
    }
    
    public void proveedorModificar(CodigoProveedor codigoProveedor, List<Comercial> comerciales, List<Contacto> contactos, FormaPago formaPago, DireccionPostal direccion, DobleObservacion dobleObservacion) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getProveedor() == null) {
            throw new EmpresaNoTieneProveedorException(this);
        }
        //Assert.required(contacto, "contacto");
        //datos formulario
        Proveedor proveedorAux = getProveedor();
        proveedorAux.setCodigoProveedor(codigoProveedor);
        proveedorAux.setComerciales(comerciales);
        proveedorAux.setContactos(contactos);
        proveedorAux.setFormaPago(formaPago);
        proveedorAux.setDireccionPostal(direccion);
        proveedorAux.setObservaciones(dobleObservacion);
        setProveedor(proveedorAux);
        log.finish();
    }

    public void proveedorEliminar() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getProveedor() == null) {
            throw new EmpresaNoTieneProveedorException(this);
        }
        setProveedor(null);
        log.finish();
    }

    public void proveedorBloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getProveedor() == null) {
            throw new EmpresaNoTieneProveedorException(this);
        }
        getProveedor().bloquear();
        log.finish();
    }

    public void proveedorDesbloquear() {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        if (getProveedor() == null) {
            throw new EmpresaNoTieneProveedorException(this);
        }
        getProveedor().desbloquear();
        log.finish();
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.Empresa[ id=" + id + " ]";
    }

    public String getFriendlyId() {
        return "CL" + StringUtil.padLeftZeros(getId().toString(), 6);
    }

}