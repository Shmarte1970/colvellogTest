package es.zarca.covellog.domain.model.empresa.cliente;

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
@Table(name = "empresa_cliente_forma_pago_linea_alquiler")
@XmlRootElement
public class ClienteFormaPagoLineaAlquiler extends FormaPagoLinea implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "empresa_id", referencedColumnName = "empresa_id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    
    public ClienteFormaPagoLineaAlquiler() {
    }
    
    public ClienteFormaPagoLineaAlquiler(Cliente cliente, Integer numLinea, Integer porcentaje, TipoPago tipoPago, String cuenta, TipoVencimiento tipoVencimiento, Integer diaPago) {
        super(numLinea, porcentaje, tipoPago, cuenta, tipoVencimiento, diaPago);
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
}
