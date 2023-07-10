package es.zarca.covellog.domain.model.empresa.cliente;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_tipo_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCliente.findById", query = "SELECT t FROM TipoCliente t WHERE t.id = :id"),
    @NamedQuery(name = "TipoCliente.findAll", query = "SELECT t FROM TipoCliente t")
})
public class TipoCliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @Size(min = 2, max = 2)
    private String id;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;

    public TipoCliente() {
    }

    public TipoCliente(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
