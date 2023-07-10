package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.comercial.domain.base.GastoTipoProducto;
import es.zarca.covellog.domain.model.producto.TipoProducto;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "oferta_linea_complemento")
@XmlRootElement
public class OfertaLineaComplemento extends GastoTipoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "oferta_linea_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OfertaLinea linea;   
    
    public OfertaLineaComplemento() {
    }

    public OfertaLineaComplemento(OfertaLinea ofertaLinea, TipoProducto tipoProducto, String concepto, int cantidad, BigDecimal importe, Integer orden) {
        super(tipoProducto, concepto, cantidad, importe, orden);
        this.linea = ofertaLinea;
    }
    
    public OfertaLinea getLinea() {
        return linea;
    }

    public void setLinea(OfertaLinea linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.oferta.OfertaLineaComplemento[ id=" + getId() + " ]";
    }
    
}
