package es.zarca.covellog.domain.model.transporte;

import es.zarca.covellog.domain.model.empresa.DobleObservacion;
import es.zarca.covellog.domain.model.empresa.Empresa;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "transporte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transporte.findAll", query = "SELECT t FROM Transporte t"),
    @NamedQuery(name = "Transporte.findById", query = "SELECT t FROM Transporte t WHERE t.id = :id"),
    @NamedQuery(name = "Transporte.findByNombre", query = "SELECT t FROM Transporte t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Transporte.findByCapacidad", query = "SELECT t FROM Transporte t WHERE t.capacidad = :capacidad"),
    @NamedQuery(name = "Transporte.findByFechaBaja", query = "SELECT t FROM Transporte t WHERE t.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "Transporte.findByCreatedAt", query = "SELECT t FROM Transporte t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Transporte.findByUpdatedAt", query = "SELECT t FROM Transporte t WHERE t.updatedAt = :updatedAt")})
public class Transporte implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacidad")
    private int capacidad;
    @Basic(optional = false)
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    private DobleObservacion observaciones;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @NotNull
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa proveedor;
    
    public Transporte() {
    }

    public Transporte(Integer id) {
        this.id = id;
    }

    public Transporte(Integer id, String nombre, int capacidad, Date fechaBaja, Date createdAt) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.fechaBaja = fechaBaja;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void baja() {
        fechaBaja = new Date();
    }
    
    public void anularBaja() {
        fechaBaja = null;
    }
    
    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacion observaciones) {
        this.observaciones = observaciones;
    }

    public Empresa getProveedor() {
        return proveedor;
    }

    public void setProveedor(Empresa proveedor) {
        this.proveedor = proveedor;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transporte)) {
            return false;
        }
        Transporte other = (Transporte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.application.transporte.Transporte[ id=" + id + " ]";
    }

    

    
    
}
