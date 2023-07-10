package es.zarca.covellog.domain.model.contrato;

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
@Table(name = "contrato_forma_pago_linea_venta")
@XmlRootElement
class ContratoFormaPagoLineaVenta extends FormaPagoLinea implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contrato contrato;
    
    public ContratoFormaPagoLineaVenta() {
    }
    
    public ContratoFormaPagoLineaVenta(Contrato contrato, Integer numLinea, Integer porcentaje, TipoPago tipoPago, String cuenta, TipoVencimiento tipoVencimiento, Integer diaPago) {
        super(numLinea, porcentaje, tipoPago, cuenta, tipoVencimiento, diaPago);
        this.contrato = contrato;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
}
