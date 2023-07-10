package es.zarca.covellog.comercial.domain.oferta;

import es.zarca.covellog.domain.model.generic.Documento;
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
@Table(name = "oferta_documento")
@XmlRootElement
public class OfertaDocumento extends Documento implements  Serializable {

    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "oferta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Oferta oferta;
    
    public OfertaDocumento() {
    }

    public OfertaDocumento(String nombre, byte[] datos) {
        super(nombre, datos);
    }
    
    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }
    
}