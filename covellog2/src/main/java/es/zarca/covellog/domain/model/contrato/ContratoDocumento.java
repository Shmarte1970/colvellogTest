package es.zarca.covellog.domain.model.contrato;

import es.zarca.covellog.domain.model.generic.Documento;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "contrato_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContratoDocumento.findAll", query = "SELECT t FROM ContratoDocumento t"),
    @NamedQuery(name = "ContratoDocumento.findById", query = "SELECT t FROM ContratoDocumento t WHERE t.id = :id"),
    @NamedQuery(name = "ContratoDocumento.findByOrden", query = "SELECT t FROM ContratoDocumento t WHERE t.orden = :orden"),
    @NamedQuery(name = "ContratoDocumento.findByNombre", query = "SELECT t FROM ContratoDocumento t WHERE t.nombre = :nombre")})
class ContratoDocumento extends Documento implements ContratoDocumentoRO, Serializable {

    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "contrato_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contrato contrato;
    
    public ContratoDocumento() {
    }

    public ContratoDocumento(String nombre, byte[] datos) {
        super(nombre, datos);
    }
    
    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
}
