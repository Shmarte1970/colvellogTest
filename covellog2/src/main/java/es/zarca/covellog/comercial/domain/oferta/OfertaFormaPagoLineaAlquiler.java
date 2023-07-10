package es.zarca.covellog.comercial.domain.oferta;

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
@Table(name = "oferta_forma_pago_linea_alquiler")
@XmlRootElement
public class OfertaFormaPagoLineaAlquiler extends FormaPagoLinea implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "oferta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oferta oferta;
    
    public OfertaFormaPagoLineaAlquiler() {
    }
    
    public OfertaFormaPagoLineaAlquiler(Oferta oferta, Integer numLinea, Integer porcentaje, TipoPago tipoPago, String cuenta, TipoVencimiento tipoVencimiento, Integer diaPago) {
        super(numLinea, porcentaje, tipoPago, cuenta, tipoVencimiento, diaPago);
        this.oferta = oferta;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }
    
}