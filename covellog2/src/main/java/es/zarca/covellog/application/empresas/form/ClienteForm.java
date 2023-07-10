package es.zarca.covellog.application.empresas.form;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.DetalleFacturacionDto;
import es.zarca.covellog.application.adreces.form.DireccionPostalForm;

/**
 *
 * @author francisco
 */
public class ClienteForm extends PotencialForm{
    
    private Integer empresaId;
    private String codigoCliente;
    private Integer contactoId;
    private Integer contactoFacturacionId;
    private String tipoClienteId;
    private Integer descuento;
    private DireccionPostalForm direccionPostal;
    private DetalleFacturacionDto detalleFacturacion;

    public ClienteForm() {
    }

    public ClienteForm(Integer empresaId, String codigoCliente, Integer contactoId, Integer contactoFacturacionId, String tipoClienteId, Integer descuento, DireccionPostalForm direccionPostal, DetalleFacturacionDto detalleFacturacion, DobleObservacionForm observaciones) {
        super(observaciones);
        this.empresaId = empresaId;
        this.codigoCliente = codigoCliente;
        this.contactoId = contactoId;
        this.contactoFacturacionId = contactoFacturacionId;
        this.tipoClienteId = tipoClienteId;
        this.descuento = descuento;
        this.direccionPostal = direccionPostal;
        this.detalleFacturacion = detalleFacturacion;
    }
    
    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Integer getContactoId() {
        return contactoId;
    }

    public void setPrioridad(Integer contactoId) {
        this.contactoId = contactoId;
    }

    public Integer getContactoFacturacionId() {
        return contactoFacturacionId;
    }

    public void setContactoFacturacionId(Integer contactoFacturacionId) {
        this.contactoFacturacionId = contactoFacturacionId;
    }

    public String getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(String tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public DireccionPostalForm getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(DireccionPostalForm direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public DetalleFacturacionDto getDetalleFacturacion() {
        return detalleFacturacion;
    }

    public void setDetalleFacturacion(DetalleFacturacionDto detalleFacturacion) {
        this.detalleFacturacion = detalleFacturacion;
    }

}
