package es.zarca.covellog.domain.model.comercials.comercial;

import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.usuarios.Usuario;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "comercial")
@PrimaryKeyJoinColumn(referencedColumnName="id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comercial.findAll", query = "SELECT c FROM Comercial c")
    , @NamedQuery(name = "Comercial.findById", query = "SELECT c FROM Comercial c WHERE c.id = :id")
    , @NamedQuery(name = "Comercial.findByNombre", query = "SELECT c FROM Comercial c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Comercial.findByApellidos", query = "SELECT c FROM Comercial c WHERE c.apellidos = :apellidos")})
public class Comercial extends Usuario {

    private static final long serialVersionUID = 1L;
    
    @JoinColumn(name = "divisio_comercial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DivisioComercial divisioComercial;

    public Comercial() {
    }

    public Comercial(Integer id) {
        super(id);
    }

    public Comercial(String username, String password, String email, String nombre, String apellidos) {
        super(username, password, email, nombre, apellidos);
    }

    public DivisioComercial getDivisioComercial() {
        return divisioComercial;
    }

    public void setDivisioComercial(DivisioComercial divisioComercial) {
        this.divisioComercial = divisioComercial;
    }

}
