package es.zarca.covellog.domain.model.empresa;

import es.zarca.covellog.domain.model.adreces.adreça.Direccion;
import es.zarca.covellog.infrastructure.log.Capa;
import es.zarca.covellog.infrastructure.log.FunctionLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "empresa_ubicacion")
@XmlRootElement
public class EmpresaUbicacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private Empresa empresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubicacion", fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy("nivel ASC")
    private final List<EmpresaUbicacionContactoRelation> contactosRelation = new ArrayList<>();
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    private Direccion direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "horario")
    private String horario;
    private DobleObservacion observaciones;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    
    public EmpresaUbicacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<EmpresaContacto> getContactos() {
        List<EmpresaContacto> contactos = new ArrayList<>();
        contactosRelation.sort(Comparator.comparing(EmpresaUbicacionContactoRelation::getNivel));
        contactosRelation.forEach(contactoRelation -> {
            contactos.add(contactoRelation.getContacto());
        });
        
        return contactos;
    }

    private EmpresaUbicacionContactoRelation getRelation(EmpresaContacto contacto) {
        for (EmpresaUbicacionContactoRelation contactoRelation : contactosRelation) {
            if (contactoRelation.getContacto().equals(contacto)) {
                return contactoRelation;
            }
        }
        return null;
    }
    
    public void setContactos(List<EmpresaContacto> contactos) {
        FunctionLog log = new FunctionLog(Capa.DOMAIN);
        int i = 0;
        for (EmpresaContacto contacto: contactos) {
            EmpresaUbicacionContactoRelation relation = getRelation(contacto);
            if (relation == null) {
                contactosRelation.add(new EmpresaUbicacionContactoRelation(this, contacto, i));
            } else {
                relation.setNivel(i);
            }
            i++;
        }
        List<EmpresaUbicacionContactoRelation> eliminar = new ArrayList<>();

        for (EmpresaUbicacionContactoRelation relation : contactosRelation) {
            if (!contactos.contains(relation.getContacto())) {
                eliminar.add(relation);
            }
        }
        for (EmpresaUbicacionContactoRelation relation : eliminar) {
            contactosRelation.remove(relation);
        }
        log.finish();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public DobleObservacion getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(DobleObservacion observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }
    
    public void baja() {
        this.fechaBaja = new Date();
    }
    
    public void anularBaja() {
        this.fechaBaja = null;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpresaUbicacion other = (EmpresaUbicacion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
