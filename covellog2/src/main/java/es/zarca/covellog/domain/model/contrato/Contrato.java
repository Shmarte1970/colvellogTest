package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.application.stock.exception.DocumentoNotExistException;
import es.zarca.covellog.domain.model.albaran.Albaran;
import es.zarca.covellog.domain.model.albaran.AlbaranEntrega;
import es.zarca.covellog.domain.model.albaran.AlbaranRecogida;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.contrato.exception.ContratoLineaNotExistException;
import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleContratacion;
import es.zarca.covellog.domain.model.empresa.cliente.DetalleFacturacion;
import es.zarca.covellog.domain.model.empresa.exception.EmpresaNoEsClienteException;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPago;
import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.domain.model.generic.DocumentoRO;
import es.zarca.covellog.domain.model.helpers.TimestampEntity;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import es.zarca.covellog.domain.model.stock.Stock;
import es.zarca.covellog.domain.model.ubicacion.Ubicacion;
import es.zarca.covellog.infrastructure.assertions.ArgumentValidator;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import es.zarca.covellog.infrastructure.persistence.converter.TipoOperacionConverter;
import es.zarca.covellog.interfaces.web.helpers.StringUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.OneToOne;
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
@Table(name = "contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c"),
    @NamedQuery(name = "Contrato.findById", query = "SELECT c FROM Contrato c WHERE c.id = :id"),
    @NamedQuery(name = "Contrato.findByFechaContrato", query = "SELECT c FROM Contrato c WHERE c.fechaContrato = :fechaContrato"),
    @NamedQuery(name = "Contrato.findByCodigoPedidoCliente", query = "SELECT c FROM Contrato c WHERE c.codigoPedidoCliente = :codigoPedidoCliente"),
    @NamedQuery(name = "Contrato.findByCodigoProveedor", query = "SELECT c FROM Contrato c WHERE c.codigoProveedor = :codigoProveedor")})
public class Contrato extends TimestampEntity implements Serializable {

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
    @Column(name = "fecha_contrato")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaContrato;
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
    private DobleObservacion observaciones;
    @NotNull
    @Column(name = "estado")
    private ContratoEstado estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estadoFecha;
    @NotNull
    @Column(name = "pago_estado")
    private ContratoPagoEstado pagoEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pago_importe_pagado")
    private BigDecimal pagoImportePagado;
    @Column(name = "pago_estado_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pagoEstadoFecha;
    @JoinColumn(name = "direccion_envio_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Ubicacion direccionEnvio;
    @OneToOne(optional = false, mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private ContratoDireccionEnvio direccionEnvioCopia;
    
    @Embedded
    private DetalleContratacion condiciones;
    @Embedded
    private DetalleFacturacion detalleFacturacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contrato", fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy("nivel ASC")
    private final List<ContratoFacturacionContactoRelation> contactosFacturacionRelation = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contrato", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<ContratoFormaPagoLineaVenta> formaPagoVenta = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contrato", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private final List<ContratoFormaPagoLineaAlquiler> formaPagoAlquiler = new ArrayList<>();
    @ManyToMany
    @JoinTable(
        name = "contrato_comerciales", 
        joinColumns = @JoinColumn(name = "contrato_id"), 
        inverseJoinColumns = @JoinColumn(name = "comercial_id")
    )
    private List<Comercial> comerciales;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="firmante_id")
    private Contacto firmante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contrato", fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy("nivel ASC")
    private final List<ContratoContactoRelation> contactosRelation = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contrato", orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<ContratoDocumento> documentos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contrato", orphanRemoval = true)
    @OrderBy("numLinea ASC")
    private List<ContratoLinea> lineas;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contrato")
    private List<AlbaranEntrega> albaranesEntrega;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contrato")
    private List<AlbaranRecogida> albaranesRecogida;

    public Contrato() {
    }

    public Contrato(Integer id, Empresa cliente, Date fechaContrato, String codigoPedidoCliente, String codigoProveedor, DobleObservacion observaciones) {
        this.id = id;
        
    }

    public Contrato(Date fechaContrato, Empresa cliente) {
        ArgumentValidator.isNotNull(cliente, "El cliente es obligatorio.");
        ArgumentValidator.isNotNull(cliente.getCliente(), "La empresa no tiene datos de cliente.");
        this.cliente = cliente;
        this.fechaContrato = fechaContrato;
        this.codigoPedidoCliente = "";
        this.codigoProveedor = "";
        this.observaciones = null;
        setPagoEstado(ContratoPagoEstado.PENDIENTE);
        this.pagoImportePagado = BigDecimal.ZERO;
        setEstado(ContratoEstado.BORRADOR);
        condiciones = cliente.getCliente().getDetalleContratacion();
        detalleFacturacion = cliente.getCliente().getDetalleFacturacion();
    }

    public Integer getId() {
        return id;
    }

    public String getFriendlyId() {
        return "C" + StringUtil.padLeftZeros(getId().toString(), 6);
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

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void modificarFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
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

    public ContratoEstado getEstado() {
        return estado;
    }

    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public ContratoPagoEstado getPagoEstado() {
        return pagoEstado;
    }

    public BigDecimal getPagoImportePagado() {
        return pagoImportePagado;
    }

    public void modificarPagoImportePagado(BigDecimal pagoImportePagado) {
        this.pagoImportePagado = pagoImportePagado;
    }
    
    public Date getPagoEstadoFecha() {
        return pagoEstadoFecha;
    }

    private void setEstado(ContratoEstado estado) {
        this.estado = estado;
        estadoFecha = new Date();
    }

    private void setPagoEstado(ContratoPagoEstado pagoEstado) {
        this.pagoEstado = pagoEstado;
        pagoEstadoFecha = new Date();
    }

    
    public void cambiarEstadoPago(ContratoPagoEstado estadoPago) {
        setPagoEstado(estadoPago);
    }

    public Ubicacion getDireccionEnvio() {
        /*if (!ContratoEstado.BORRADOR.equals(getEstado())) {
            return direccionEnvioCopia;
        }*/
        return direccionEnvio;
    }

    public void cambiarDireccionEnvio(Ubicacion direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
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
                ContratoFormaPagoLineaAlquiler lineaVieja;
                if (i < this.formaPagoAlquiler.size()) {
                    lineaVieja = this.formaPagoAlquiler.get(i);
                } else {
                    lineaVieja = new ContratoFormaPagoLineaAlquiler();
                    this.formaPagoAlquiler.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setContrato(this);            
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
                ContratoFormaPagoLineaVenta lineaVieja;
                if (i < this.formaPagoVenta.size()) {
                    lineaVieja = this.formaPagoVenta.get(i);
                } else {
                    lineaVieja = new ContratoFormaPagoLineaVenta();
                    this.formaPagoVenta.add(lineaVieja);

                }
                lineaVieja.setNumLinea(i + 1);
                lineaVieja.setContrato(this);            
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
        contactosFacturacionRelation.sort(Comparator.comparing(ContratoFacturacionContactoRelation::getNivel));
        contactosFacturacionRelation.forEach(contactoRelation -> {
            contactos.add(contactoRelation.getContacto());
        });
        
        return contactos;
    }

    private ContratoFacturacionContactoRelation getContactosFacturacionRelation(Contacto contacto) {
        for (ContratoFacturacionContactoRelation contactoRelation : contactosFacturacionRelation) {
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
            ContratoFacturacionContactoRelation relation = getContactosFacturacionRelation(contacto);
            if (relation == null) {
                contactosFacturacionRelation.add(new ContratoFacturacionContactoRelation(this, contacto, i));
            } else {
                relation.setNivel(i);
            }
            i++;
        }
        List<ContratoFacturacionContactoRelation> eliminar = new ArrayList<>();
        contactosFacturacionRelation.stream().filter(relation -> (!contactos.contains(relation.getContacto()))).forEachOrdered(relation -> {
            eliminar.add(relation);
        });
        eliminar.forEach(relation -> {
            contactosFacturacionRelation.remove(relation);
        });
        log.finish();
    }
    
    public List<Contacto> getContactos() {
        List<Contacto> contactos = new ArrayList<>();
        contactosRelation.sort(Comparator.comparing(ContratoContactoRelation::getNivel));
        contactosRelation.forEach(contactoRelation -> {
            contactos.add(contactoRelation.getContacto());
        });
        
        return contactos;
    }

    private ContratoContactoRelation getContactosRelation(Contacto contacto) {
        for (ContratoContactoRelation contactoRelation : contactosRelation) {
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
            ContratoContactoRelation relation = getContactosRelation(contacto);
            if (relation == null) {
                contactosRelation.add(new ContratoContactoRelation(this, contacto, i));
            } else {
                relation.setNivel(i);
            }
            i++;
        }
        List<ContratoContactoRelation> eliminar = new ArrayList<>();
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
            comerciales = new ArrayList();
        }
        return comerciales;
    }

    public void cambiarComerciales(List<Comercial> comerciales) {
        this.comerciales = comerciales;
    }

    public Contacto getFirmante() {
        return firmante;
    }

    public void cambiarFirmante(Contacto firmante) {
        this.firmante = firmante;
    }
    
    public List<DocumentoRO> getDocumentos() {
        return Collections.unmodifiableList(documentos);
    }

    public List<ContratoLineaRO> getLineas() {
        return Collections.unmodifiableList(lineas);
    }
    
    public List<ContratoLineaRO> getLineasContratoPendientesAlbaranEntrega() {
        List<ContratoLineaRO> result = new ArrayList();
        for (ContratoLinea linea : lineas) {
            if ((linea.getProducto() != null) && (linea.getAlbaranEntrega() == null)) {
                result.add(linea);
            }
        }
        return result;
    }

    
    /*public void eliminarDocumento(ContratoDocumento documento) {
        documentos.remove(documento);
        asignarOrdenTodosItemsListaDocumentos();
    }
    */
    public void eliminarDocumento(Integer documentoId) {
        documentos.remove(getDocumentoById(documentoId));
        asignarOrdenTodosItemsListaDocumentos();
    }
    
    public DocumentoRO getDocumento(Integer documentoId) {
        return getDocumentoById(documentoId);
    }
    
    private ContratoDocumento getDocumentoById(Integer documentoId) {
        if (documentoId != null) {
            for (ContratoDocumento documento : documentos) {
                System.err.println("comparo " + documento.getId() + " = " + documentoId);
                if (documentoId.equals(documento.getId())) {
                    System.err.println("retoirna " + + documento.getId());
                    return documento;
                }
            }
        }
        throw new DocumentoNotExistException(id, documentoId);
    }
    
    private int getPosicionDocumento(ContratoDocumento documento) {
        return documentos.indexOf(documento);
    }
    
    private void asignarOrdenTodosItemsListaDocumentos() {
        int indiceLinea = 0;
        for (ContratoDocumento documento : documentos) {
            documento.setOrden(indiceLinea);
            indiceLinea++;
        }
    }
    
   /* public void setPosicionDocumento(ContratoDocumento documento, Integer posicion) {
        System.err.println("MUEVO: " + documento.getNombre() + "-" + posicion );
        Integer posicionAnterior = documentos.indexOf(documento);
        if ((posicion < documentos.size()) && (posicionAnterior >= 0) && (!posicionAnterior.equals(posicion))) {
            ContratoDocumento documentoSecundario = documentos.get(posicion);
            documentos.set(posicion, documento);
            documentos.set(posicionAnterior, documentoSecundario);        
            asignarOrdenTodosItemsListaDocumentos();
        }
    }*/
    
    public void anadirDocumento(String nombre, byte[] datos) {
        ContratoDocumento contratoDocumento = new ContratoDocumento(nombre, datos);
        contratoDocumento.setContrato(this);
        contratoDocumento.setOrden(documentos.size());
        documentos.add(contratoDocumento);
    }
  
    public void subirDocumento(Integer documentoId) {
        ContratoDocumento documento = getDocumentoById(documentoId);
        int indiceLinea = getPosicionDocumento(documento);
        if (indiceLinea > 0) {
            documentos.set(indiceLinea, documentos.get(indiceLinea - 1));
            documentos.set(indiceLinea - 1, documento);
        }
        asignarOrdenTodosItemsListaDocumentos();
    }

    public void bajarDocumento(Integer documentoId) {
        ContratoDocumento documento = getDocumentoById(documentoId);
        int indiceLinea = getPosicionDocumento(documento);
        if (indiceLinea - 1 < documentos.size()) {
            documentos.set(indiceLinea, documentos.get(indiceLinea + 1));
            documentos.set(indiceLinea + 1, documento);
        }
        asignarOrdenTodosItemsListaDocumentos();
    }
    
    protected void anadirLinea(ContratoLinea linea) {
        lineas.add(linea);
        linea.setNumLinea(lineas.size());
        linea.setContrato(this);
        actualizarEstadoAtributo();
    }
    
    public ContratoLineaRO anadirLineaAlquiler(
        Date fechaEntregaPrevista, 
        TipoProducto tipoProducto, 
        Ubicacion ubicacion,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        TransporteConPrecio transporteRecogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        ContratoLinea linea = new ContratoLinea(
            ContratoTipoOperacion.ALQUILER,
            fechaEntregaPrevista,
            tipoProducto,
            ubicacion,
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
    
    public ContratoLineaRO anadirLineaAlquiler(
        Date fechaEntregaPrevista, 
        TipoProducto tipoProducto, 
        Ubicacion ubicacion,
        String lote,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        TransporteConPrecio transporteRecogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
            
            
    ) {
        ContratoLinea linea = new ContratoLinea(
            ContratoTipoOperacion.ALQUILER,
            fechaEntregaPrevista,
            tipoProducto,
            ubicacion,
            lote,
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
    
    public ContratoLineaRO anadirLineaAlquiler(
        Date fechaEntregaPrevista, 
        Stock producto,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        TransporteConPrecio transporteRecogida,
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        ContratoLinea linea = new ContratoLinea(
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
            
            
    public ContratoLineaRO anadirLineaVenta(
        Date fechaEntregaPrevista, 
        TipoProducto tipoProducto, 
        Ubicacion ubicacion,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        ContratoLinea linea = new ContratoLinea(
            ContratoTipoOperacion.VENTA,
            fechaEntregaPrevista,
            tipoProducto,
            ubicacion,
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
    
    public ContratoLineaRO anadirLineaVenta(
        Date fechaEntregaPrevista, 
        TipoProducto tipoProducto, 
        Ubicacion ubicacion,
        String concepto,
        String lote,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        ContratoLinea linea = new ContratoLinea(
            ContratoTipoOperacion.VENTA,
            fechaEntregaPrevista,
            tipoProducto,
            ubicacion,
            lote,
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
    
    public ContratoLineaRO anadirLineaVenta(
        Date fechaEntregaPrevista, 
        Stock producto,
        String concepto,
        BigDecimal importe, 
        TransporteConPrecio transporteEntrega, 
        List<ContratoLineaComplementoVO> complementos,
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales,
        DobleObservacion observaciones
    ) {
        ContratoLinea linea = new ContratoLinea(
            ContratoTipoOperacion.VENTA,
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
    
   /* public Integer getLineaId(int indiceLinea) {
        return getLinea(indiceLinea).getId();
    }*/
  
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

    public ContratoLineaRO getLinea(Integer lineaId) {
        for (ContratoLinea linea : lineas) {
            if (linea.getId().equals(lineaId)) {
                return linea;
            }
        }
        return null;
    }
    
    private ContratoLinea getLineaById(Integer lineaId) {
        for (ContratoLinea linea : lineas) {
            if (linea.getId().equals(lineaId)) {
                return linea;
            }
        }
        return null;
    }
    
    private ContratoLinea getLineaByBooking(String booking) {
        ArgumentValidator.isNotEmpty(booking, "No puede buscar una linea de contrato por booking sin especificar el booking.");
        for (ContratoLinea linea : lineas) {
            System.err.println("ContratoLinea.getLineaByBooking: " + booking + " = " + linea.getBooking());
            if (booking.equals(linea.getBooking())) {
                return linea;
            }
            System.err.println("ContratoLinea.getLineaByBooking: " + getFriendlyId() + "-" + booking + " = " + linea.getBooking());
            if (linea.getBooking().equals(getFriendlyId() + "-" + booking)) {
                return linea;
            }
        }
        return null;
    }

    private ContratoLinea getLineaProducto(Stock producto) {
        for (ContratoLinea linea : lineas) {
            if (linea.getProducto().equals(producto)) {
                return linea;
            }
        }
        return null;
    }
    
    private ContratoLinea getLineaProductoOrFail(Stock producto) {
        ContratoLinea linea = getLineaProducto(producto);
        if (linea == null) {
            throw new ContratoLineaNotExistException(id, producto);
        }
        return linea;
    }
        
    public void onMovimientoEntregaRealizado(String booking, Stock stockEntregado, Date fecha) {
        ArgumentValidator.isNotEmpty(booking, "Error en el contrato \"" + getFriendlyId() + "\" porque ha recibido una notificacion sin booking.");
        ArgumentValidator.isNotNull(stockEntregado, "Error en el contrato \"" + getFriendlyId() + "\" porque ha recibido una notificacion de producto entregado con booking \"" + booking + "\" sin especificar el producto.");
        ArgumentValidator.isNotNull(stockEntregado, "Error en el contrato \"" + getFriendlyId() + "\" porque ha recibido una notificacion de producto entregado con booking \"" + booking + "\" y producto \"" + stockEntregado.getNumeroSerie() + "\" pero sin especificar la fecha.");
        ContratoLinea linea = getLineaByBooking(booking);
        if (linea != null) {
            if (!stockEntregado.equals(linea.getProducto())) {
                linea.asignarProducto(stockEntregado);
            }
            lineaEntregar(linea, fecha);
        } 
    }
    
    public void onMovimientoRecogidaRealizado(String booking, Date fecha) {
        ArgumentValidator.isNotEmpty(booking, "Error en el contrato \"" + getFriendlyId() + "\" porque ha recibido una notificacion sin booking.");
        ContratoLinea linea = getLineaByBooking(booking);
        if (linea != null) {
            linea.recoger(fecha);
        }
    }
    
    public void productoModificarFechaRecogidaPrevista(Stock producto, Date fecha) {
        ArgumentValidator.isNotNull(producto, "No se puede cambiar la fecha de recogida prevista porque no hay producto asignado.");
        ArgumentValidator.isNotNull(fecha, "La fecha de recogida es obligatoria.");
        getLineaProductoOrFail(producto).modificarFechaEntregaPrevista(fecha);
    }
    
    /*public void onMovimientoRecogidaRealizado(Stock producto, Date fecha, Ubicacion ubicacion) {
        ArgumentValidator.isNotNull(producto, "No se puede cambiar la fecha de recogida porque no hay producto asignado.");
        ArgumentValidator.isNotNull(fecha, "La fecha de recogida es obligatoria.");
        getLineaProductoOrFail(producto).recoger(fecha, ubicacion);
    }*/

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
    
    public void lineaModificarFechaEntrega(int index, Date fecha) {
        getLineaById(index).modificarFechaEntrega(fecha);
    }
    
    public void lineaModificarFechaEntrega(List<Integer> lineaIds, Date fecha) {
        lineaIds.forEach(lineaId -> {
            lineaModificarFechaEntrega(lineaId, fecha);
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
    
    public void lineaAsignarProducto(int lineaId, TipoProducto tipoProducto, Ubicacion ubicacion, String lote) {
        getLineaById(lineaId).asignarProducto(tipoProducto, ubicacion, lote);
        onUpdate();
    }

   /* public void lineaAsignarProducto(int lineaId, TipoProducto tipoProducto, Ubicacion ubicacion) {
        getLineaById(lineaId).asignarProducto(tipoProducto, ubicacion);
        onUpdate();
    }*/
    
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
    
    public void lineaModificarComplementos(int lineaId, List<ContratoLineaComplementoVO> complementos) {
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
    
    public void lineaModificarGastosAdicionales(int lineaId, List<ContratoLineaGastoAdicionalVO> gastosAdicionales) {
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
    
    public void lineaEntregar(int lineaId, Date fecha) {
        lineaEntregar(getLineaById(lineaId), fecha);
    }
    
    public void lineaEntregar(List<Integer> lineaIds, Date fecha) {        
        lineaIds.forEach(lineaId -> {
            lineaEntregar(lineaId, fecha);
        });
    }
    
    private void lineaEntregar(ContratoLinea linea, Date fecha) {
        linea.entregar(fecha);
        actualizarEstadoAtributo();
    }
    
    public void lineaCancelarEntrega(int lineaId) {
        getLineaById(lineaId).cancelarEntrega();
    }
    
    public void onMovimientoEntregaAnulado(String booking) {
        ContratoLinea linea = getLineaByBooking(booking);
        ArgumentValidator.isNotNull(linea, "El contrato \"" + getFriendlyId() + "\" no tiene ninguna linea con booking \"" + getFriendlyId() + "-" + booking + "\".");
        linea.cancelarEntrega();
        setEstado(ContratoEstado.PENDIENTE);
    }
    
    public void lineaCancelarEntrega(List<Integer> lineaIds) {
        lineaIds.forEach(lineaId -> {
            lineaCancelarEntrega(lineaId);
        });
    }
    
    public void lineaSolicitarRecogida(int lineaId, Date fecha) {
        getLineaById(lineaId).solicitarRecogida(fecha);
    }
    
    public void lineaSolicitarRecogida(List<Integer> lineaIds, Date fecha) {        
        lineaIds.forEach(lineaId -> {
            lineaSolicitarRecogida(lineaId, fecha);
        });
    }
    
    public void lineaCancelarSolicitudRecogida(int lineaId) {
        getLineaById(lineaId).cancelarSolicitudRecogida();
    }
    
    public void lineaCancelarSolicitudRecogida(List<Integer> lineaIds) {
        lineaIds.forEach(lineaId -> {
            lineaCancelarSolicitudRecogida(lineaId);
        });
    }
    
    public void lineaRecoger(int lineaId, Date fecha, Ubicacion ubicacionDestino) {
        getLineaById(lineaId).recoger(fecha, ubicacionDestino);
        actualizarEstadoAtributo();
    }
    
    public void lineaRecoger(List<Integer> lineaIds, Date fecha, Ubicacion ubicacionDestino) {        
        lineaIds.forEach(lineaId -> {
            lineaRecoger(lineaId, fecha, ubicacionDestino);
        });
    }
    
    public void lineaModificarFechaRecogida(int lineaId, Date fecha, Ubicacion ubicacionDestino) {
        getLineaById(lineaId).modificarFechaRecogida(fecha);
    }
    
    public void lineaModificarFechaRecogida(List<Integer> lineaIds, Date fecha, Ubicacion ubicacionDestino) {        
        lineaIds.forEach(lineaId -> {
            lineaModificarFechaRecogida(lineaId, fecha, ubicacionDestino);
        });
    }
    
    public void lineaCancelarRecogida(int lineaId) {
        getLineaById(lineaId).cancelarRecogida();
        actualizarEstadoAtributo();
    }
    
    public void lineaCancelarRecogida(List<Integer> lineaIds) {
        lineaIds.forEach(lineaId -> {
            lineaCancelarRecogida(lineaId);
        });
    }
    
    public void onMovimientoRecogidaAnulado(String booking) {
        ContratoLinea linea = getLineaByBooking(booking);
        if (linea == null) {
            ArgumentValidator.isNotNull(linea, "El contrato \"" + getFriendlyId() + "\" no tiene ninguna linea con booking \"" + getFriendlyId() + "-" + booking + "\".");
                
        }
        linea.cancelarRecogida();
        setEstado(ContratoEstado.PENDIENTE);
    }
    
    private void lineaEliminar(ContratoLinea linea) {
        linea.eliminar();
        lineas.remove(linea);
        actualizarEstadoAtributo();
    }
    
    private void lineasEliminar(List<ContratoLinea> lineas) {
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
    
    public void lineaActualizarAlbaranSiTiene(Integer lineaId) {
        ContratoLinea linea = getLineaById(lineaId);        
        linea.lineaActualizarAlbaranEntregaSiTiene();
    }

    public List<AlbaranEntrega> getAlbaranesEntrega() {
        return Collections.unmodifiableList(albaranesEntrega);
    }
    
    public List<AlbaranRecogida> getAlbaranesRecogida() {
        return Collections.unmodifiableList(albaranesRecogida);
    }
    
    public void asignarAlbaranEntrega(int lineaId, AlbaranEntrega albaranEntrega) {
        getLineaById(lineaId).asignarAlbaranEntrega(albaranEntrega);
    }

    public void asignarAlbaranRecogida(int lineaId, AlbaranRecogida albaranRecogida) {
        getLineaById(lineaId).asignarAlbaranRecogida(albaranRecogida);
    }
    
    public void crearAlbaranEntrega(List<Integer> lineasContratoIds) {
        ArgumentValidator.isNotEmpty(lineasContratoIds, "Debe especificar almenos una linea para crear un albaran.");
        AlbaranEntrega albaranEntrega = new AlbaranEntrega();
        ContratoLinea linea = getLineaById(lineasContratoIds.get(0));
        if (linea.getProducto() != null) {
            albaranEntrega.asignarOrigen(linea.getProducto().getUbicacion());
        }
        albaranEntrega.asignarContrato(this);
        if (direccionEnvio != null) {
            albaranEntrega.asignarDestino(direccionEnvio);
        }
        albaranEntrega.modificarFecha(linea.getFechaEntregaPrevista());
        if ((linea.getTransporteEntregaConPrecio() != null) && (linea.getTransporteEntregaConPrecio().getTransporte() != null)) {
            albaranEntrega.cambiarTransporte(linea.getTransporteEntregaConPrecio().getTransporte());
        }
        for (Integer lineaContratoId : lineasContratoIds) {
            getLineaById(lineaContratoId).asignarAlbaranEntrega(albaranEntrega);
        }
        albaranesEntrega.add(albaranEntrega);
    }
    
    public void crearAlbaranRecogida(List<Integer> lineasContratoIds) {
        ArgumentValidator.isNotEmpty(lineasContratoIds, "Debe especificar almenos una linea para crear un albaran.");
        AlbaranRecogida albaranRecogida = new AlbaranRecogida();
        ContratoLinea linea = getLineaById(lineasContratoIds.get(0));
        albaranRecogida.asignarOrigen(linea.getProducto().getUbicacion());
        albaranRecogida.asignarContrato(this);
        albaranRecogida.modificarFecha(linea.getFechaDevolucionPrevista());
        if ((linea.getTransporteRecogidaConPrecio() != null) && (linea.getTransporteRecogidaConPrecio().getTransporte() != null)) {
            albaranRecogida.cambiarTransporte(linea.getTransporteRecogidaConPrecio().getTransporte());
        }
        for (Integer lineaContratoId : lineasContratoIds) {
            getLineaById(lineaContratoId).asignarAlbaranRecogida(albaranRecogida);
        }
        albaranesRecogida.add(albaranRecogida);
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
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.contrato.Contrato[ id=" + id + " ]";
    }

    private int bookingToLineaId(String booking) {
        if (booking.startsWith(getFriendlyId())) {
            booking = booking.substring(getFriendlyId().length() + 1);
        }
        return Integer.parseInt(booking);
    }
    
    public void onAlbaranEntregaLineaEliminada(String booking) {
        ArgumentValidator.isNotEmpty(booking, "El evento de linea de albaran eliminada requiere que se informe el booking.");
        ContratoLinea linea = getLineaById(Integer.parseInt(booking));
        if (linea != null) {
            linea.desasociarAlbaranEntrega();
        }
    }
    
    public void onAlbaranRecogidaLineaEliminada(String booking) {
        ArgumentValidator.isNotEmpty(booking, "El evento de linea de albaran eliminada requiere que se informe el booking.");
        ContratoLinea linea = getLineaById(bookingToLineaId(booking));
        if (linea != null) {
            linea.desasociarAlbaranRecogida();
        }
    }
    
    public void eliminarAlbaranEntrega(AlbaranEntrega albaran) {
        albaranesEntrega.remove(albaran);
    }


    public void onAlbaranFinalizado(Albaran aThis) {
        System.err.println("COJONES: onAlbaranFinalizado");
    }

    private void actualizarEstadoAtributo() {
        setEstado(getEstadoCalculado());
    }
    
    private ContratoEstado getEstadoCalculado() {
        //Si hay algo entrega el estado sera PENDIENTE
        //Si esta todo entregado pero hay algun alquiler sein devolver ACTIVO
        //Si 
        if (ContratoEstado.ANULADO.equals(estado)) {
            return ContratoEstado.ANULADO;
        } else if (hayAlgoPendiente()) {
            return ContratoEstado.PENDIENTE;
        } else if (hayAlquilerActivo()) {
            return ContratoEstado.ACTIVO;
        } else if (estaFinalizado()) {
            return ContratoEstado.FINALIZADO;
        }
        return ContratoEstado.BORRADOR;
    }
    
    private Boolean hayAlgoPendiente() {
        for (ContratoLinea linea : lineas) {
            if (linea.getFechaEntrega() == null) {
                return true;
            }
        }
        return false;
    }
    
    private Boolean hayAlquilerActivo() {
        for (ContratoLinea linea : lineas) {
            if ((linea.getTipoOperacion().equals(ContratoTipoOperacion.ALQUILER)) && (linea.getFechaEntrega() != null) && (linea.getFechaDevolucion() == null)) {
                return true;
            }
        }
        return false;
    }
    
    private Boolean estaFinalizado() {
        for (ContratoLinea linea : lineas) {
            if ((linea.getFechaEntrega() == null)) {
                return false;
            } else if ((linea.getTipoOperacion().equals(ContratoTipoOperacion.ALQUILER)) && (linea.getFechaDevolucion() == null)) {
                return false;
            }
        }
        return true;
    }
    
    private List<ContratoLineaComplementoVO> getLineaComplementoVO(ContratoLinea linea) {
        List<ContratoLineaComplementoVO> complementos = new ArrayList();
        linea.getComplementos().forEach(complemento -> {
            complementos.add(
                new ContratoLineaComplementoVO(
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
    
    private List<ContratoLineaGastoAdicionalVO> getLineaGastosAdicionalesVO(ContratoLinea linea) {
        List<ContratoLineaGastoAdicionalVO> gastosAdicionales = new ArrayList();
        linea.getGastosAdicionales().forEach(gastosAdicional -> {
            gastosAdicionales.add(
                new ContratoLineaGastoAdicionalVO(
                    gastosAdicional.getId(),
                    gastosAdicional.getTipoProducto(),
                    gastosAdicional.getConcepto(),
                    gastosAdicional.getCantidad(),
                    gastosAdicional.getImporte()
                )
            );
        });
        return gastosAdicionales;
    }
    

    public Contrato traspasar(Empresa cliente, Date fecha) {
        Contrato contratoNuevo = new Contrato(fecha, cliente);
        contratoNuevo.modificarCodigoPedidoCliente(codigoPedidoCliente);
        contratoNuevo.modificarCodigoProveedor(codigoProveedor);
        contratoNuevo.modificarObservaciones(observaciones);
        contratoNuevo.cambiarDireccionEnvio(direccionEnvio);
        contratoNuevo.modificarCondiciones(condiciones);
        contratoNuevo.modificarDetalleFacturacion(detalleFacturacion);
        contratoNuevo.cambiarComerciales(comerciales);
        for (ContratoDocumento documento : documentos) {
            contratoNuevo.anadirDocumento(documento.getNombre(), documento.getDatos());
        }
        List<ContratoLinea> lineasParaEliminar = new ArrayList();
        for (ContratoLinea linea : lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                //Solo traspasa si no estan devueltos
                if (linea.getFechaDevolucion() == null) {
                    if (linea.getFechaEntrega() != null) {
                        linea.recoger(fecha);
                        lineaEntregar(copiarLineaAlquilerEnOtroContrato(contratoNuevo, linea).getId(), fecha);
                    } else {
                        copiarLineaAlquilerEnOtroContrato(contratoNuevo, linea);
                    }
                    lineasParaEliminar.add(linea);
                }
            } else if (linea.getFechaEntrega() == null) {
                lineasParaEliminar.add(linea);
                copiarLineaVentaEnOtroContrato(contratoNuevo, linea);
            }
        }
        lineasEliminar(lineasParaEliminar);
        return contratoNuevo;
    }
    
    public Contrato copiar() {
        Contrato contratoNuevo = new Contrato(new Date(), cliente);
        contratoNuevo.modificarCodigoPedidoCliente(codigoPedidoCliente);
        contratoNuevo.modificarCodigoProveedor(codigoProveedor);
        contratoNuevo.modificarObservaciones(observaciones);
        contratoNuevo.cambiarDireccionEnvio(direccionEnvio);
        contratoNuevo.modificarCondiciones(condiciones);
        contratoNuevo.modificarDetalleFacturacion(detalleFacturacion);
        contratoNuevo.cambiarComerciales(comerciales);
        documentos.forEach(documento -> {
            contratoNuevo.anadirDocumento(documento.getNombre(), documento.getDatos());
        });
        lineas.forEach(linea -> {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                copiarLineaAlquilerEnOtroContrato(contratoNuevo, linea);
            } else {
                copiarLineaVentaEnOtroContrato(contratoNuevo, linea);
            }
        });
        return contratoNuevo;
    }
    
    private ContratoLineaRO copiarLineaAlquilerEnOtroContrato(Contrato contratoNuevo, ContratoLinea linea) {
        if (linea.getProducto() != null) {                        
            contratoNuevo.anadirLinea(linea);
            return contratoNuevo.anadirLineaAlquiler(
                linea.getFechaEntregaPrevista(), 
                linea.getProducto(),
                linea.getConcepto(),
                linea.getImporte(), 
                linea.getTransporteEntregaConPrecio(), 
                linea.getTransporteRecogidaConPrecio(),
                getLineaComplementoVO(linea),
                getLineaGastosAdicionalesVO(linea),
                linea.getObservaciones()
            );
        } else if (linea.getLote() != null) {
            return contratoNuevo.anadirLineaAlquiler(
                linea.getFechaEntregaPrevista(), 
                linea.getTipoProducto(),
                linea.getUbicacion(),
                linea.getConcepto(),
                linea.getLote(),
                linea.getImporte(), 
                linea.getTransporteEntregaConPrecio(), 
                linea.getTransporteEntregaConPrecio(), 
                getLineaComplementoVO(linea),
                getLineaGastosAdicionalesVO(linea),
                linea.getObservaciones()
            );
        } else {
            return contratoNuevo.anadirLineaAlquiler(
                linea.getFechaEntregaPrevista(), 
                linea.getTipoProducto(),
                linea.getUbicacion(),
                linea.getConcepto(),
                linea.getImporte(), 
                linea.getTransporteEntregaConPrecio(), 
                linea.getTransporteEntregaConPrecio(), 
                getLineaComplementoVO(linea),
                getLineaGastosAdicionalesVO(linea),
                linea.getObservaciones()
            );
        }
    }

    private void copiarLineaVentaEnOtroContrato(Contrato contratoNuevo, ContratoLinea linea) {
        if (linea.getProducto() != null) {                    
            contratoNuevo.anadirLineaVenta(
                linea.getFechaEntregaPrevista(), 
                linea.getProducto(),
                linea.getConcepto(),
                linea.getImporte(), 
                linea.getTransporteEntregaConPrecio(), 
                getLineaComplementoVO(linea),
                getLineaGastosAdicionalesVO(linea),
                linea.getObservaciones()
            );
        } else if (linea.getLote() != null) {
            contratoNuevo.anadirLineaVenta(
                linea.getFechaEntregaPrevista(), 
                linea.getTipoProducto(),
                linea.getUbicacion(),
                linea.getConcepto(),
                linea.getLote(),
                linea.getImporte(), 
                linea.getTransporteEntregaConPrecio(), 
                getLineaComplementoVO(linea),
                getLineaGastosAdicionalesVO(linea),
                linea.getObservaciones()
            );
        } else {
            contratoNuevo.anadirLineaVenta(
                linea.getFechaEntregaPrevista(), 
                linea.getTipoProducto(),
                linea.getUbicacion(),
                linea.getConcepto(),
                linea.getImporte(), 
                linea.getTransporteEntregaConPrecio(), 
                getLineaComplementoVO(linea),
                getLineaGastosAdicionalesVO(linea),
                linea.getObservaciones()
            );
        }
    }
    
    public BigDecimal getTotalAlquiler() {
        BigDecimal result = BigDecimal.ZERO;        
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotal());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalBaseAlquiler() {
        BigDecimal result = BigDecimal.ZERO;        
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotalBase());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalComplementosAlquiler() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result =  result.add(linea.getComplementosImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalGastosAdicionalesAlquiler() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result =  result.add(linea.getGastosAdicionalesImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalTransporteAlquiler() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.ALQUILER.equals(linea.getTipoOperacion())) {
                result =  result.add(linea.getImporteTransportes());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotal());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalBaseVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTotalBase());
            }
        }
        return result;
    }
    
    public BigDecimal getTotalComplementosVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getComplementosImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalGastosAdicionalesVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getGastosAdicionalesImporte());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotalTransporteVenta() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            if (ContratoTipoOperacion.VENTA.equals(linea.getTipoOperacion())) {
                result = result.add(linea.getImporteTransportes());
            } 
        }
        return result;
    }
    
    public BigDecimal getTotal() {
        BigDecimal result = BigDecimal.ZERO;        
        for(ContratoLinea linea:lineas) {
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
        for(ContratoLinea linea:lineas) {
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
        for(ContratoLinea linea:lineas) {
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
        for(ContratoLinea linea:lineas) {
            result =  result.add(linea.getGastosAdicionalesImporte());
        }
        return result;
    }
    
    public BigDecimal getTotalTransporte() {
        BigDecimal result = BigDecimal.ZERO;
        for(ContratoLinea linea:lineas) {
            result =  result.add(linea.getImporteTransportes());
        }
        return result;
    }
    
}
