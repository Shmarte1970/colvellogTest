package es.zarca.covellog.domain.model.empresa.proveedor;

import es.zarca.covellog.domain.model.empresa.formapago.FormaPagoLinea;
import es.zarca.covellog.domain.model.empresa.formapago.TipoPago;
import es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_proveedor_forma_pago_linea")
@XmlRootElement
public class ProveedorFormaPagoLinea extends FormaPagoLinea implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "empresa_id", referencedColumnName = "empresa_id")
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    
    public ProveedorFormaPagoLinea() {
    }
    
    public ProveedorFormaPagoLinea(Proveedor proveedor, Integer numLinea, Integer porcentaje, TipoPago tipoPago, String cuenta, TipoVencimiento tipoVencimiento, Integer diaPago) {
        super(numLinea, porcentaje, tipoPago, cuenta, tipoVencimiento, diaPago);
        this.proveedor = proveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
}
