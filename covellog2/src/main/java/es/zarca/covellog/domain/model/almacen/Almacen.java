package es.zarca.covellog.domain.model.almacen;

import es.zarca.covellog.domain.model.ubicacion.UbicacionConContactosRef;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "almacen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a"),
    @NamedQuery(name = "Almacen.findById", query = "SELECT a FROM Almacen a WHERE a.id = :id"),
    @NamedQuery(name = "Almacen.findByNombre", query = "SELECT a FROM Almacen a WHERE a.nombre = :nombre"),
})
public class Almacen extends UbicacionConContactosRef implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    
    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion", fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy("orden ASC") //Parece que solo ordena cuando se lo coge de la BD, luego en cache no funciona. Por eso se ordena a mano
    //private final List<UbicacionConContactoRefRelation> contactosRelation = new ArrayList<>();

}