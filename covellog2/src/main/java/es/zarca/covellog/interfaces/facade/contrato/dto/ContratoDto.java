package es.zarca.covellog.interfaces.facade.contrato.dto;

import es.zarca.covellog.interfaces.facade.adreces.facade.dto.DireccionDto;
import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import es.zarca.covellog.interfaces.facade.contactos.dto.ContactoDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleContratacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DobleObservacionDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.EmpresaMiniDto;
import es.zarca.covellog.interfaces.facade.helpers.dto.TimestampEntityDto;
import es.zarca.covellog.interfaces.facade.stock.dto.DocumentoDto;
import es.zarca.covellog.interfaces.facade.ubicaciones.dto.UbicacionDto;
import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco
 */
public class ContratoDto {
    private Integer id;
    private String friendlyId;
    private EmpresaMiniDto cliente;
    private Date fechaContrato;
    private String codigoPedidoCliente;
    private String codigoProveedor;
    private Integer previsionMesesAlquiler;
    private String estado;
    private Date estadoFecha;
    private String estadoPago;
    private Date estadoPagoFecha;
    private DireccionDto direccionCorrespondencia;
    private UbicacionDto direccionEnvio;
    private DobleObservacionDto observaciones;
    private DetalleContratacionDto condiciones;
    private DetalleFacturacionDto facturacion;
    private List<ComercialDto> comerciales;
    private ContactoDto firmante;
    private List<ContactoDto> contactos;
    private List<DocumentoDto> documentos;
    private List<ContratoLineaDto> lineas;
    private TimestampEntityDto auditoria;        
    private ContratoResumenDto resumen;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFriendlyId() {
        return friendlyId;
    }

    public void setFriendlyId(String friendlyId) {
        this.friendlyId = friendlyId;
    }

    public EmpresaMiniDto getCliente() {
        return cliente;
    }

    public void setCliente(EmpresaMiniDto cliente) {
        this.cliente = cliente;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    public String getCodigoPedidoCliente() {
        return codigoPedidoCliente;
    }

    public void setCodigoPedidoCliente(String codigoPedidoCliente) {
        this.codigoPedidoCliente = codigoPedidoCliente;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public Integer getPrevisionMesesAlquiler() {
        return previsionMesesAlquiler;
    }

    public void setPrevisionMesesAlquiler(Integer previsionMesesAlquiler) {
        this.previsionMesesAlquiler = previsionMesesAlquiler;
    }
    
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoPago() {
        return estadoPago;
    }
    
    public Date getEstadoFecha() {
        return estadoFecha;
    }

    public void setEstadoFecha(Date estadoFecha) {
        this.estadoFecha = estadoFecha;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Date getEstadoPagoFecha() {
        return estadoPagoFecha;
    }

    public void setEstadoPagoFecha(Date estadoPagoFecha) {
        this.estadoPagoFecha = estadoPagoFecha;
    }
    
    public DobleObservacionDto getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionDto observaciones) {
        this.observaciones = observaciones;
    }

    public DireccionDto getDireccionCorrespondencia() {
        return direccionCorrespondencia;
    }

    public void setDireccionCorrespondencia(DireccionDto direccionCorrespondencia) {
        this.direccionCorrespondencia = direccionCorrespondencia;
    }
    
    public UbicacionDto getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(UbicacionDto direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public DetalleContratacionDto getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(DetalleContratacionDto condiciones) {
        this.condiciones = condiciones;
    }

    public DetalleFacturacionDto getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(DetalleFacturacionDto facturacion) {
        this.facturacion = facturacion;
    }

    public List<ComercialDto> getComerciales() {
        return comerciales;
    }

    public void setComerciales(List<ComercialDto> comerciales) {
        this.comerciales = comerciales;
    }

    public ContactoDto getFirmante() {
        return firmante;
    }

    public void setFirmante(ContactoDto firmante) {
        this.firmante = firmante;
    }

    public List<ContactoDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactoDto> contactos) {
        this.contactos = contactos;
    }

    public List<DocumentoDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDto> documentos) {
        this.documentos = documentos;
    }

    public List<ContratoLineaDto> getLineas() {
        return lineas;
    }
    
    public ContratoLineaDto getLinea(Integer lineaId) {
        for (ContratoLineaDto linea : lineas) {
            System.err.println("COJONES: getLinea EL id es "+ linea.getId().toString());
            if (linea.getId().equals(lineaId)) {
                return linea;
            }
        }
        return null;
    }

    public void setLineas(List<ContratoLineaDto> lineas) {
        this.lineas = lineas;
    }

    public TimestampEntityDto getAuditoria() {
        return auditoria;
    }

    public void setAuditoria(TimestampEntityDto auditoria) {
        this.auditoria = auditoria;
    }

    public ContratoResumenDto getResumen() {
        return resumen;
    }

    public void setResumen(ContratoResumenDto resumen) {
        this.resumen = resumen;
    }
    

}
