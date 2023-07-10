package es.zarca.covellog.domain.model.usuarios;

import es.zarca.covellog.application.usuarios.exception.CambioPasswordException;
import es.zarca.covellog.application.usuarios.exception.PasswordNoAceptableException;
import es.zarca.covellog.domain.model.empresa.CanalesContacto;
import es.zarca.covellog.domain.model.notificacions.Notificacio;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "usuario")
@Inheritance(strategy=InheritanceType.JOINED)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
    , @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password")
    , @NamedQuery(name = "Usuario.findByNom", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByCognoms", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "password")
    private String password;
    @Embedded
    protected CanalesContacto canalesContacto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 45)
    @Column(name = "apellidos")
    private String apellidos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destinatari")
    private Collection<Notificacio> notificacionCollection;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @ManyToMany
    @JoinTable(
        name = "usuario_grupos", 
        joinColumns = @JoinColumn(name = "usuario_id"), 
        inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    List<Grupo> grupos;
    
    public Usuario() {
        this.grupos = new ArrayList<>();
    }

    public Usuario(Integer id) {
        this.id = id;
        this.grupos = new ArrayList<>();
    }

    public Usuario(String username, String password, String email, String nombre, String apellidos) {
        this.username = username;
        this.password = password;
        this.canalesContacto = new CanalesContacto(null, null, email);
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.grupos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    private String toMD5(String texto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = texto.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        return DatatypeConverter.printHexBinary(thedigest).toLowerCase();
    }

    public void setPassword(String password) {
        if ((password == null) || (password.length() <= 3)) {
            throw new PasswordNoAceptableException();
        }
        try {
            this.password = toMD5(password);
        } catch (Exception ex) {
            throw new CambioPasswordException(ex);
        }
    }
    
    public Boolean compararPassword(String passwordComparar) {
        try {
            return (password == null ? toMD5(passwordComparar) == null : password.equals(toMD5(passwordComparar)));
        } catch (Exception ex) {
            throw new CambioPasswordException(ex);
        }
    }

    public CanalesContacto getCanalesContacto() {
        return canalesContacto;
    }

    public void setCanalesContacto(CanalesContacto canalesContacto) {
        this.canalesContacto = canalesContacto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void addGrupo(Grupo grupo) {
        this.grupos.add(grupo);
    }
    
    public boolean perteneceAGrupo(Grupo grupo) {
        return this.grupos.contains(grupo);
    }
    
    public void removeGrupo(Grupo grupo) {
        this.grupos.remove(grupo);
    }
    
    public void baja() {
        fechaBaja = new Date();
    }
    
    public void reactivar() {
        fechaBaja = null;
    }

    @XmlTransient
    public Collection<Notificacio> getNotificacionCollection() {
        return notificacionCollection;
    }

    public void setNotificacionCollection(Collection<Notificacio> notificacionCollection) {
        this.notificacionCollection = notificacionCollection;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.usuarios.Usuario[ id=" + id + " ]";
    }
    
}
