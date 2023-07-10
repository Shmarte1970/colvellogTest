package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.application.stock.exception.DocumentoNotExistException;
import es.zarca.covellog.comercial.domain.oferta.exception.OfertaLineaNotExistException;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.contrato.ContratoTipoOperacion;
import es.zarca.covellog.domain.model.contrato.TransporteConPrecio;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.domain.model.empresa.exception.EmpresaNoEsClienteException;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.domain.model.generic.Documento;
import es.zarca.covellog.domain.model.generic.DocumentoRO;
import es.zarca.covellog.domain.model.helpers.TimestampEntity;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "oferta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT c FROM Oferta c"),
    @NamedQuery(name = "Oferta.findById", query = "SELECT c FROM Oferta c WHERE c.id = :id"),
    @NamedQuery(name = "Oferta.findByFechaOferta", query = "SELECT c FROM Oferta c WHERE c.fechaOferta = :fechaOferta"),
    @NamedQuery(name = "Oferta.findByCodigoPedidoCliente", query = "SELECT c FROM Oferta c WHERE c.codigoPedidoCliente = :codigoPedidoCliente"),
    @NamedQuery(name = "Oferta.findByCodigoProveedor", query = "SELECT c FROM Oferta c WHERE c.codigoProveedor = :codigoProveedor")})
public class Oferta extends TimestampEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private Empresa cliente;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_oferta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOferta;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "codigo_pedido_cliente")
    private String codigoPedidoCliente;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 50)
    @Column(name = "codigo_proveedor")
    private String codigoProveedor;
    
    @NotNull
    @Column(name = "prevision_meses_alquiler")
    private Integer previsionMesesAlquiler;
    
    @Embedded
    private DobleObservacion observaciones;
    
    @NotNull
    @Column(name = "estado")
    private OfertaEstado estado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFecha;
    
    @Embedded
    private DetalleContratacion condiciones;
    
    @Embedded
    private DetalleFacturacion detalleFacturacion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oferta", fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy("nivel ASC")
    private final List<OfertaFacturacionContactoRelation> contactosFacturacionRelation = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "oferta", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<OfertaFormaPagoLineaVenta> formaPagoVenta = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "oferta", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<OfertaFormaPagoLineaAlquiler> formaPagoAlquiler = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "oferta_comerciales", 
        joinColumns = @JoinColumn(name = "oferta_id"), 
        inverseJoinColumns = @JoinColumn(name = "comercial_id")
    )
    private List<Comercial> comerciales;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oferta", fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy("nivel ASC")
    private final List<OfertaContactoRelation> contactosRelation = new ArrayList<>(5);
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "oferta", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<OfertaDocumento> documentos = new ArrayList<>(10);
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "oferta", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private List<OfertaLinea> lineas;

    public Oferta() {
    }

    public Oferta(Integer id, Empresa cliente, Date fechaOferta, String codigoPedidoCliente, String codigoProveedor, DobleObservacion observaciones) {
//        this.id = id;
        
    }

    public Oferta(Date fechaOferta, Empresa cliente) {
        ArgumentValidator.isNotNull(cliente, "El cliente es obligatorio.");
        ArgumentValidator.isNotNull(cliente.getCliente(), "La empresa no tiene datos de cliente.");
        this.cliente = cliente;
        this.fechaOferta = fechaOferta;
        this.codigoPedidoCliente = "";
        this.codigoProveedor = "";
        this.observaciones = null;
        setEstado(OfertaEstado.BORRADOR);
        condiciones = cliente.getCliente().getDetalleContratacion();
        detalleFacturacion = cliente.getCliente().getDetalleFacturacion();
    }

    public Integer getId() {
        return id;
    }

    public String getFriendlyId() {
        return "OF" + StringUtil.padLeftZeros(getId().toString(), 6);
    }
    
    public Empresa getCliente() {
        return cliente;
    }

    public void cambiarCliente(Empresa cliente) {
        if (!cliente.isCliente()) {
            throw new EmpresaNoEsClienteException(cliente);
        }
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fechaOferta;
    }

    public void modificarFechaOferta(Date fechaOferta) {
        this.fechaOferta = fechaOferta;
    }

    public String getCodigoPedidoCliente() {
        return codigoPedidoCliente;
    }

    public void modificarCodigoPedidoCliente(String codigoPedidoCliente) {
        this.codigoPedidoCliente = codigoPedidoCliente;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void modificarCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public Integer getPrevisionMesesAlquiler() {
        return previsionMesesAlquiler;
    }

    public void modificarPrevisionMesesAlquiler(Integer previsionMesesAlquiler) {
        this.previsionMesesAlquiler = previsionMesesAlquiler;
    }
    
    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void modificarObservaciones(DobleObservacion observaciones) {
        this.observaciones = observaciones;
    }

    public OfertaEstado getEstado() {
        return estado;
    }
    
    private void setEstado(OfertaEstado estado) {
        this.estado = estado;
        estadoFecha = new Date();
    }


    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public DetalleContratacion getCondiciones() {
        if (condiciones == null) {
            condiciones = cliente.getCliente().getDetalleContratacion();
        }
        return condiciones;
        
        
    }

    public void modificarCondiciones(DetalleContratacion condiciones) {
        this.condiciones = condiciones;
    }

    public DetalleFacturacion getDetalleFacturacion() {
        if (detalleFacturacion == null) {
            detalleFacturacion = cliente.getCliente().getDetalleFacturacion();
        }
        return new DetalleFacturacion(
            detalleFacturacion.isExentoIva(), 
            //detalleFacturacion.getContactoFacturacion(), 
            //detalleFacturacion.getContactosFacturacion(), 
            getContactosFacturacion(),
            getFormaPagoVenta(), 
            getFormaPagoAlquiler(),
            detalleFacturacion.getDireccionPostal(),
            detalleFacturacion.isMalPagador(),
            detalleFacturacion.getObservaciones()
        );
    }

    public void modificarDetalleFacturacion(DetalleFacturacion detalleFacturacion) {
        this.detalleFacturacion = detalleFacturacion;
        formaPagoToFormaPagoAlquiler(detalleFacturacion.getFormaPagoAlquiler());
        formaPagoToFormaPagoVenta(detalleFacturacion.getFormaPagoVenta());
        cambiarContactosFacturacion(detalleFacturacion.getContactos());
    }
    
    
    
    private FormaPago getFormaPagoAlquiler() {
        if ((formaPagoAlquiler == null) || (formaPagoAlquiler.isEmpty())) {
            return null;
        }
        List<FormaPagoLinea> lineasFp = new ArrayList<>();
        formaPagoAlquiler.forEach((linea) -> {
            lineasFp.add(linea);
        });
        return new FormaPago(lineasFp);
    }

    private void formaPagoToFormaPagoAlquiler(FormaPago formaPago) {
        int i = 0;
        if (formaPago != null) {
            for (i = 0; i < formaPago.getLineas().size(); i++) {
                FormaPagoLinea lineaNueva = formaPago.getLineas().get(i);
                OfertaFormaPagoLineaAlquiler lineaVieja;
                if (i < this.formaPagoAlquiler.size()) {
                    lineaVieja = this.formaPagoAlquiler.get(i);
                } else {
                    lineaVieja = new OfertaFormaPagoLineaAlquiler();
                    this.formaPagoAlquiler.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setOferta(this);            
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
        List<FormaPagoLinea> lineasFp = new ArrayList<>();
        formaPagoVenta.forEach((linea) -> {
            lineasFp.add(linea);
        });
        return new FormaPago(lineasFp);
    }

    private void formaPagoToFormaPagoVenta(FormaPago formaPago) {
        int i = 0;
        if (formaPago != null) {
            for (i = 0; i < formaPago.getLineas().size(); i++) {
                FormaPagoLinea lineaNueva = formaPago.getLineas().get(i);
                OfertaFormaPagoLineaVenta lineaVieja;
                if (i < this.formaPagoVenta.size()) {
                    lineaVieja = this.formaPagoVenta.get(i);
                } else {
                    lineaVieja = new OfertaFormaPagoLineaVenta();
                    this.formaPagoVenta.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setOferta(this);            
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
    
    private List<Contacto> getContactosFacturacion() {
        List<Contacto> contactos = new ArrayList<>();
        contactosFacturacionRelation.sort(Comparator.comparing(OfertaFacturacionContactoRelation::getNivel));
        contactosFacturacionRelation.forEach(contactoRelation -> {
            contactos.add(contactoRelation.getContacto());
        });
        return contactos;
    }

    private OfertaFacturacionContactoRelation getContactosFacturacionRelation(Contacto contacto) {
        for (OfertaFacturacionContactoRelation contactoRelation : contactosFacturacionRelation) {
            if (contactoRelation.getContacto().equals(contacto)) {
                return contactoRelation;
            }
        }
        return null;
    }
    
    private void cambiarContactosFacturacion(List<Contacto> contactos) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        int i = 0;
        for (Contacto contacto: contactos) {
            OfertaFacturacionContactoRelation relation = getContactosFacturacionRelation(contacto);
            if (relation == null) {
                contactosFacturacionRelation.add(new OfertaFacturacionContactoRelation(this, contacto, i));
            } else {
                relation.setNivel(i);
            }
            i++;
        }
        List<OfertaFacturacionContactoRelation> eliminar = new ArrayList<>();
        contactosFacturacionRelation.stream().filter(relation -> (!contactos.contains(relation.getContacto()))).forEachOrdered(relation -> {
            eliminar.add(relation);
        });
        eliminar.forEach(relation -> {
            contactosFacturacionRelation.remove(relation);
        });
     // **  log.finish();
    }
    
    public List<Contacto> getContactos() {
        List<Contacto> contactos = new ArrayList<>();
        contactosRelation.sort(Comparator.comparing(OfertaContactoRelation::getNivel));
        contactosRelation.forEach(contactoRelation -> {
            contactos.add(contactoRelation.getContacto());
        });
        
        return contactos;
    }

    private OfertaContactoRelation getContactosRelation(Contacto contacto) {
        for (OfertaContactoRelation contactoRelation : contactosRelation) {
            if (contactoRelation.getContacto().equals(contacto)) {
                return contactoRelation;
            }
        }
        return null;
    }
    
    public void cambiarContactos(List<Contacto> contactos) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        int i = 0;
        for (Contacto contacto: contactos) {
            OfertaContactoRelation relation = getContactosRelation(contacto);
            if (relation == null) {
                contactosRelation.add(new OfertaContactoRelation(this, contacto, i));
            } else {
                relation.setNivel(i);
            }
            i++;
        }
        List<OfertaContactoRelation> eliminar = new ArrayList<>();
        contactosRelation.stream().filter(relation -> (!contactos.contains(relation.getContacto()))).forEachOrdered(relation -> {
            eliminar.add(relation);
        });
        eliminar.forEach(relation -> {
            contactosRelation.remove(relation);
        });
        log.finish();
    }

    public List<Comercial> getComerciales() {
        if (comerciales == null) {
            comerciales = new ArrayList<>(0);
        }
        return Collections.unmodifiableList(comerciales);
    }

    public void cambiarComerciales(List<Comercial> comerciales) {
        this.comerciales = new ArrayList<>(comerciales);
    }

    public List<Documento> getDocumentos() {
        return Collections.unmodifiableList(documentos);
    }

    public List<IOfertaLinea> getLineas() {
        return Collections.unmodifiableList(lineas);
    }
    
    
    public void eliminarDocumento(OfertaDocumento documento) {
        documentos.remove(documento);
        asignarOrdenTodosItemsListaDocumentos();
    }
    
    public void eliminarDocumento(Integer documentoId) {
        documentos.remove(getDocumentoById(documentoId));
        asignarOrdenTodosItemsListaDocumentos();
    }
    
    public DocumentoRO getDocumento(Integer documentoId) {
        return getDocumentoById(documentoId);
    }
    
    private OfertaDocumento getDocumentoById(Integer documentoId) {
        if (documentoId != null) {
            for (OfertaDocumento documento : documentos) {
                if (documentoId.equals(documento.getId())) {
                    return documento;
                }
            }
        }
        throw new DocumentoNotExistException(id, documentoId);
    }
    
    private int getPosicionDocumento(OfertaDocumento documento) {
        return documentos.indexOf(documento);
    }
    
    private void asignarOrdenTodosItemsListaDocumentos() {
        int indiceLinea = 0;
        for (OfertaDocumento documento : documentos) {
            documento.setOrden(indiceLinea);
            indiceLinea++;
        }
    }
    
    public void setPosicionDocumento(OfertaDocumento documento, Integer posicion) {
        System.err.println("MUEVO: " + documento.getNombre() + "-" + posicion );
        Integer posicionAnterior = documentos.indexOf(documento);
        if ((posicion < documentos.size()) && (posicionAnterior >= 0) && (!posicionAnterior.equals(posicion))) {
            OfertaDocumento documentoSecundario = documentos.get(posicion);
            documentos.set(posicion, documento);
            documentos.set(posicionAnterior, documentoSecundario);        
            asignarOrdenTodosItemsListaDocumentos();
        }
    }
    
    public void anadirDocumento(String nombre, byte[] datos) {
        OfertaDocumento ofertaDocumento = new OfertaDocumento(nombre, datos);
        ofertaDocumento.setOferta(this);
        ofertaDocumento.setOrden(documentos.size());
        documentos.add(ofertaDocumento);
    }
  
    public void subirDocumento(Integer documentoId) {
        OfertaDocumento documento = getDocumentoById(documentoId);
        int indiceLinea = getPosicionDocumento(documento);
        if (indiceLinea > 0) {
            documentos.set(indiceLinea, documentos.get(indiceLinea - 1));
            documentos.set(indiceLinea - 1, documento);
        }
        asignarOrdenTodosItemsListaDocumentos();
    }

    public void bajarDocumento(Integer documentoId) {
        OfertaDocumento documento = getDocumentoById(documentoId);
        int indiceLinea = getPosicionDocumento(documento);
        if (indiceLinea - 1 < documentos.size()) {
            documentos.set(indiceLinea, documentos.get(indiceLinea + 1));
            documentos.set(indiceLinea + 1, documento);
        }
        asignarOrdenTodosItemsListaDocumentos();
    }
    protected void anadirLinea(OfertaLinea linea) {
        lineas.add(linea);
        linea.setNumLinea(lineas.size());
        linea.setOferta(this);
      //  actualizarEstadoAtributo();
    }
    
    public IOfertaLinea anadirLineaAlquiler(
        Date fechaEntregaPrevista, 
        SeleccionProducto seleccionProducto,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        TransporteConPrecio transporteRecogida,
        List<IGastoTipoProducto> complementos,
        List<IGastoTipoProducto> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        OfertaLinea linea = new OfertaLinea(
            ContratoTipoOperacion.ALQUILER,
            fechaEntregaPrevista,
            seleccionProducto,
            concepto,
            importe,
            transporteEntrega,
            transporteRecogida,
            complementos,
            gastosAdicionales,
            observaciones
        );
        anadirLinea(linea);
        return linea;
    }
    
    public IOfertaLinea anadirLineaAlquiler(
        Date fechaEntregaPrevista, 
        Stock producto,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        TransporteConPrecio transporteRecogida,
        List<IGastoTipoProducto> complementos,
        List<IGastoTipoProducto> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        OfertaLinea linea = new OfertaLinea(
            ContratoTipoOperacion.ALQUILER,
            fechaEntregaPrevista,
            producto,
            concepto,
            importe,
            transporteEntrega,
            transporteRecogida,
            complementos,
            gastosAdicionales,
            observaciones
                
        );
          
        anadirLinea(linea);
        return linea;
    }
            
            
    public IOfertaLinea anadirLineaVenta(
        Date fechaEntregaPrevista, 
        SeleccionProducto seleccionProducto,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        List<IGastoTipoProducto> complementos,
        List<IGastoTipoProducto> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        OfertaLinea linea = new OfertaLinea(
            ContratoTipoOperacion.VENTA,
            fechaEntregaPrevista,
            seleccionProducto,
            concepto,
            importe,
            transporteEntrega,
            null,
            complementos,
            gastosAdicionales,
            observaciones
        );
        anadirLinea(linea);
        return linea;
    }
    
   /* public OfertaLineaRO anadirLineaVenta(
        Date fechaEntregaPrevista, 
        Stock producto,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        List<OfertaLineaComplementoVO> complementos,
        List<OfertaLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        OfertaLinea linea = new OfertaLinea(
            TipoOperacion.VENTA,
            fechaEntregaPrevista,
            producto,
            concepto,
            importe,
            transporteEntrega,
            null,
            complementos,
            gastosAdicionales,
            observaciones
        );
        anadirLinea(linea);
        return linea;
    }
    */
  
    public Integer getLineaNumLinea(Integer lineaId) {
         return getLineaById(lineaId).getNumLinea();
    }

    public ContratoTipoOperacion getLineaTipoOperacion(Integer lineaId) {
        return getLineaById(lineaId).getTipoOperacion();
    }

    public void lineaCambiarTipoOperacion(int lineaId, ContratoTipoOperacion tipoOperacion) {
        getLineaById(lineaId).cambiarTipoOperacion(tipoOperacion);
    }

    public TipoProducto getLineaTipoProducto(int lineaId) {
        return getLineaById(lineaId).getTipoProducto();
    }

    public Ubicacion getLineaUbicacion(int lineaId) {
        return getLineaById(lineaId).getUbicacion();
    }

    public IOfertaLinea getLinea(Integer lineaId) {
        for (OfertaLinea linea : lineas) {
            if (linea.getId().equals(lineaId)) {
                return linea;
            }
        }
        return null;
    }
    
    private OfertaLinea getLineaById(Integer lineaId) {
        for (OfertaLinea linea : lineas) {
            if (linea.getId().equals(lineaId)) {
                return linea;
            }
        }
        return null;
    }
    
    private OfertaLinea getLineaByBooking(String booking) {
        ArgumentValidator.isNotEmpty(booking, "No puede buscar una linea de oferta por booking sin especificar el booking.");
        for (OfertaLinea linea : lineas) {
            if (booking.equals(linea.getBooking())) {
                return linea;
            }
            if (linea.getBooking().equals(getFriendlyId() + "-" + booking)) {
                return linea;
            }
        }
        return null;
    }

    private OfertaLinea getLineaProducto(Stock producto) {
        for (OfertaLinea linea : lineas) {
            if (linea.getProducto().equals(producto)) {
                return linea;
            }
        }
        return null;
    }
    
    private OfertaLinea getLineaProductoOrFail(Stock producto) {
        OfertaLinea linea = getLineaProducto(producto);
        if (linea == null) {
            throw new OfertaLineaNotExistException(id, producto);
        }
        return linea;
    }
        
    public void lineaModificarConcepto(int lineaId, String concepto) {
        ArgumentValidator.isNotEmpty(concepto, "El concepto no puede estar vacio.");
        getLineaById(lineaId).modificarConcepto(concepto);
        onUpdate();
    }

    public void lineaModificarImporte(int lineaId, BigDecimal importe) {
        getLineaById(lineaId).modificarImporte(importe);
        onUpdate();
    }
    
    public void lineaModificarFechaEntregaPrevista(int index, Date fecha) {
        getLineaById(index).modificarFechaEntregaPrevista(fecha);
        onUpdate();
    }
    
    public void lineaModificarFechaEntregaPrevista(List<Integer> lineaIds, Date fecha) {
        lineaIds.forEach(lineaId -> {
            lineaModificarFechaEntregaPrevista(lineaId, fecha);
        });
    }
    
    public void lineaModificarObservaciones(int lineaId, DobleObservacion observaciones) {
        getLineaById(lineaId).modificarObservaciones(observaciones);
        onUpdate();
    }
    
    public void lineaModificarTransporteEntregaConPrecio(int lineaId, TransporteConPrecio transporteConPrecio) {
        getLineaById(lineaId).modificarTransporteEntregaConPrecio(transporteConPrecio);
        onUpdate();
    }
    
    public void lineaModificarTransporteRecogidaConPrecio(int lineaId, TransporteConPrecio transporteConPrecio) {
        getLineaById(lineaId).modificarTransporteRecogidaConPrecio(transporteConPrecio);
        onUpdate();
    }
    
    public void lineaAsignarProducto(int lineaId, Stock producto) {        
        getLineaById(lineaId).asignarProducto(producto);
        onUpdate();
    }
    
    public void lineaAsignarProducto(int lineaId, SeleccionProducto seleccionProducto) {
        getLineaById(lineaId).asignarProducto(seleccionProducto);
        onUpdate();
    }
    
    /**
     * 
     * @param lineaId
     * @param tipoProducto
     * @param concepto
     * @param cantidad
     * @param importe 
     */
    public void lineaAnadirComplemento(int lineaId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        getLineaById(lineaId).anadirComplemento(tipoProducto, concepto, cantidad, importe);
        onUpdate();
    }
    
    public void lineaModificarComplemento(int lineaId, int complementoId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        getLineaById(lineaId).modificarComplemento(complementoId, tipoProducto, concepto, cantidad, importe);
        onUpdate();
    }
    
    public void lineaModificarComplementos(int lineaId, List<IGastoTipoProducto> complementos) {
        getLineaById(lineaId).modificarComplementos(complementos);
        onUpdate();
    }
    
    public void lineaEliminarComplementos(int lineaId) {
        getLineaById(lineaId).eliminarComplementos();
        onUpdate();
    }
    
    public void lineaEliminarComplemento(int lineaId, int complementoId) {
        getLineaById(lineaId).eliminarComplemento(complementoId);
        onUpdate();
    }
    
    public void lineaAnadirGastoAdicional(int lineaId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        getLineaById(lineaId).anadirGastoAdicional(tipoProducto, concepto, cantidad, importe);
        onUpdate();
    }
    
    public void lineaModificarGastoAdicional(int lineaId, int gastoAdicionalId, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe) {
        getLineaById(lineaId).modificarGastoAdicional(gastoAdicionalId, tipoProducto, concepto, cantidad, importe);
        onUpdate();
    }
    
    public void lineaModificarGastosAdicionales(int lineaId, Collection<IGastoTipoProducto> gastosAdicionales) {
        getLineaById(lineaId).modificarGastosAdicionales(gastosAdicionales);
        onUpdate();
    }
    
    public void lineaEliminarGastosAdicionales(int lineaId) {
        getLineaById(lineaId).eliminarGastosAdicionales();
        onUpdate();
    }
    
    public void lineaEliminarGastoAdicional(int lineaId, int gastoAdicionalId) {
        getLineaById(lineaId).eliminarGastoAdicional(gastoAdicionalId);
        onUpdate();
    }
    
    private void lineaEliminar(OfertaLinea linea) {
        linea.eliminar();
        lineas.remove(linea);
    }
    
    private void lineasEliminar(List<OfertaLinea> lineas) {
        lineas.forEach(linea -> {
            lineaEliminar(linea);
        });
    }
    
    public void lineaEliminar(int lineaId) {
        lineaEliminar(getLineaById(lineaId));
        
    }
    
    public void lineaEliminar(List<Integer> lineaIds) {        
        lineaIds.forEach(lineaId -> {
            lineaEliminar(lineaId);
        });
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
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.oferta.Oferta[ id=" + id + " ]";
    }

    private int bookingToLineaId(String booking) {
        if (booking.startsWith(getFriendlyId())) {
            booking = booking.substring(getFriendlyId().length() + 1);
        }
        return Integer.parseInt(booking);
    }  
    
    /*private List<OfertaLineaComplemento> getLineaComplementoVO(OfertaLinea linea) {
        List<OfertaLineaComplementoVO> complementos = new ArrayList();
        linea.getComplementos().forEach(complemento -> {
            complementos.add(
                new OfertaLineaComplementoVO(
                    complemento.getId(),
                    complemento.getTipoProducto(),
                    complemento.getConcepto(),
                    complemento.getCantidad(),
                    complemento.getImporte()
                )
            );
        });
        return complementos;
    }
    */
    /*private List<OfertaLineaGastoAdicionalVO> getLineaGastosAdicionalesVO(OfertaLinea linea) {
        List<OfertaLineaGastoAdicionalVO> gastosAdicionales = new ArrayList();
        linea.getGastosAdicionales().forEach(gastosAdicional -> {
            gastosAdicionales.add(
                new OfertaLineaGastoAdicionalVO(
                    gastosAdicional.getId(),
                    gastosAdicional.getTipoProducto(),
                    gastosAdicional.getConcepto(),
                    gastosAdicional.getCantidad(),
                    gastosAdicional.getImporte()
                )
            );
        });
        return gastosAdicionales;
    }*/
    
    public Oferta copiar() {
    /*    Oferta ofertaNuevo = new Oferta(new Date(), cliente);
        ofertaNuevo.modificarCodigoPedidoCliente(codigoPedidoCliente);
        ofertaNuevo.modificarCodigoProveedor(codigoProveedor);
        ofertaNuevo.modificarObservaciones(observaciones);
        ofertaNuevo.cambiarDireccionEnvio(direccionEnvio);
        ofertaNuevo.modificarCondiciones(condiciones);
        ofertaNuevo.modificarDetalleFacturacion(detalleFacturacion);
        ofertaNuevo.cambiarComerciales(comerciales);
        documentos.forEach(documento -> {
            ofertaNuevo.anadirDocumento(documento.getNombre(), documento.getDatos());
        });
        lineas.forEach(linea -> {
            if (TipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                copiarLineaAlquilerEnOtroOferta(ofertaNuevo, linea);
            } else {
                copiarLineaVentaEnOtroOferta(ofertaNuevo, linea);
            }
        });
        return ofertaNuevo;*/
        return null;
    }
    
    public BigDecimal getTotalAlquiler() {
        BigDecimal result = BigDecimal.ZERO;        
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotal());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalBaseAlquiler() {
        BigDecimal result = BigDecimal.ZERO;        
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotalBase());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalComplementosAlquiler() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result =  result.add(linea.getComplementosImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalGastosAdicionalesAlquiler() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result =  result.add(linea.getGastosAdicionalesImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalTransporteAlquiler() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result =  result.add(linea.getImporteTransportes());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotal());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalBaseVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotalBase());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalComplementosVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getComplementosImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalGastosAdicionalesVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getGastosAdicionalesImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalTransporteVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTransportes());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotal() {
        BigDecimal result = BigDecimal.ZERO;        
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotal().multiply(new BigDecimal(previsionMesesAlquiler)));                
            } else {
                result = result.add(linea.getImporteTotal());
            }
            result = result.add(linea.getGastosAdicionalesImporte());
            result = result.add(linea.getImporteTransportes());
        }
        return result;
    }
    
    public BigDecimal getTotalBase() {
        BigDecimal result = BigDecimal.ZERO;        
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotalBase().multiply(new BigDecimal(previsionMesesAlquiler)));
            } else {
                result = result.add(linea.getImporteTotalBase());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalComplementos() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getComplementosImporte().multiply(new BigDecimal(previsionMesesAlquiler)));
            } else {
                result = result.add(linea.getComplementosImporte());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalGastosAdicionales() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            result =  result.add(linea.getGastosAdicionalesImporte());
        }
        return result;
    }
    
    public BigDecimal getTotalTransporte() {
        BigDecimal result = BigDecimal.ZERO;
        for(OfertaLinea linea:lineas) {
            result =  result.add(linea.getImporteTransportes());
        }
        return result;
    }
    
}
