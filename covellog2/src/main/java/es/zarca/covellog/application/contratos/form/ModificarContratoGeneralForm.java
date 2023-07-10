package es.zarca.covellog.application.contratos.form;

import es.zarca.covellog.application.adreces.form.DireccionForm;
import es.zarca.covellog.application.empresas.form.DobleObservacionForm;
import java.util.Date;

/**
 *
 * @author francisco
 */
public class ModificarContratoGeneralForm {    
    private Date fecha;
    private String codigoPedidoCliente;
    private String codigoPedidoProveedor;
    private Integer direccionEnvioId;
    private DobleObservacionForm observaciones;
    private Integer previsionMesesAlquiler;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoPedidoCliente() {
        return codigoPedidoCliente;
    }

    public void setCodigoPedidoCliente(String codigoPedidoCliente) {
        this.codigoPedidoCliente = codigoPedidoCliente;
    }

    public String getCodigoPedidoProveedor() {
        return codigoPedidoProveedor;
    }

    public void setCodigoPedidoProveedor(String codigoPedidoProveedor) {
        this.codigoPedidoProveedor = codigoPedidoProveedor;
    }

    public Integer getDireccionEnvioId() {
        return direccionEnvioId;
    }

    public void setDireccionEnvioId(Integer direccionEnvioId) {
        this.direccionEnvioId = direccionEnvioId;
    }

    public DobleObservacionForm getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacionForm observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getPrevisionMesesAlquiler() {
        return previsionMesesAlquiler;
    }

    public void setPrevisionMesesAlquiler(Integer previsionMesesAlquiler) {
        this.previsionMesesAlquiler = previsionMesesAlquiler;
    }

}
