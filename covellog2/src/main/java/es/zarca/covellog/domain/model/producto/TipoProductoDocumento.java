package es.zarca.covellog.domain.model.producto;

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
@Table(name = "tipo_producto_documento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoProductoDocumento.findAll", query = "SELECT t FROM TipoProductoDocumento t"),
    @NamedQuery(name = "TipoProductoDocumento.findById", query = "SELECT t FROM TipoProductoDocumento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoProductoDocumento.findByOrden", query = "SELECT t FROM TipoProductoDocumento t WHERE t.orden = :orden"),
    @NamedQuery(name = "TipoProductoDocumento.findByNombre", query = "SELECT t FROM TipoProductoDocumento t WHERE t.nombre = :nombre")})
public class TipoProductoDocumento extends Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoProducto tipoProducto;

    public TipoProductoDocumento() {
    }

    public TipoProductoDocumento(String nombre, byte[] datos) {
        super(nombre, datos);
    }
    
    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.producto.TipoProductoDocumento[ id=" + getId() + " ]";
    }
    
}
