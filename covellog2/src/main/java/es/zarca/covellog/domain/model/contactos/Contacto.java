package es.zarca.covellog.domain.model.contactos;

import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.empresa.ContactoContactoRolRelation;
import es.zarca.covellog.domain.model.empresa.ContactoRol;
import es.zarca.covellog.domain.model.empresa.Empresa;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "contacto")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
public class Contacto implements ContactoRO, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    protected String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(max = 80)
    @Column(name = "apellidos")
    protected String apellidos;
    @Embedded
    protected CanalesContacto canalesContacto;
    @Size(max = 200)
    @Column(name = "descripcion")
    protected String descripcion;
    @JoinColumn(name = "idioma_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected Idioma idioma;
    @Size(max = 10000)
    @Column(name = "observaciones")
    protected String observaciones;
    @Size(max = 200)
    @Column(name = "horario")
    protected String horario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contacto", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("contactoRol ASC")
    private List<ContactoContactoRolRelation> rolesRelation;

    public Contacto() {
    }
    
    public Contacto(Contacto contacto) {
        System.err.println("COJONES QUE ME QUEMO: COPIO CONTACTO");
        nombre = contacto.getNombre();
        apellidos = contacto.getApellidos();
        canalesContacto = contacto.getCanalesContacto();
        descripcion = contacto.getDescripcion();
        idioma = contacto.getIdioma();
        observaciones = contacto.getObservaciones();
        horario = contacto.getHorario();
        int nivel = 0;
        for (ContactoRol rol : contacto.getRoles()) {
           // setRole(rol, nivel);
            rolesRelation.add(new ContactoContactoRolRelation(this, rol, nivel));
            nivel++;
        }
    }
    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    @Override
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    @Override
    public CanalesContacto getCanalesContacto() {
        return canalesContacto;
    }

    public void setCanalesContacto(CanalesContacto canalesContacto) {
        this.canalesContacto = canalesContacto;
    }
    
    @Override
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @Override
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<ContactoRol> getRoles() {
        List<ContactoRol> roles = new ArrayList<>(rolesRelation.size());
        rolesRelation.forEach(rolRelation -> {
            roles.add(rolRelation.getContactoRol());
        });
        return roles;
    }
                
    public boolean hasRole(String rol_id) {
        if (getRoles().stream().anyMatch(rol -> (rol.getId().equals(rol_id)))) {
            return true;
        }
        return false;
    }
    
    public boolean hasRole(ContactoRol rol) {
        return getRoles().contains(rol);
    }
    
    public void setRole(ContactoRol rol, int nivel) {
        ContactoContactoRolRelation relation = getRolRelation(rol);
        if (relation == null) {
            rolesRelation.add(new ContactoContactoRolRelation(this, rol, nivel));
        } else {
            relation.setNivel(nivel);
        }
    }
    
    public ContactoContactoRolRelation getRolRelation(ContactoRol rol) {
        for (ContactoContactoRolRelation rolRelation : rolesRelation) {
            if (rolRelation.getContactoRol().equals(rol)) {
                return rolRelation;
            }
        }
        return null;
    }
    
    public void removeRole(ContactoRol rol) {
        ContactoContactoRolRelation relation = getRolRelation(rol);
        if (relation != null) {
            rolesRelation.remove(relation);
        }
        int i = 0;
        for (ContactoContactoRolRelation rolRelation : rolesRelation) {
            rolRelation.setNivel(i);
        }
    }
    
    public Integer getNivelRol(ContactoRol rol) {
        ContactoContactoRolRelation relacion = getRolRelation(rol);
        if (relacion != null) {
            return relacion.getNivel();
        }
        return -1;
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
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.generic.Contacto[ id=" + id + " ]";
    }
    
}
