package es.zarca.covellog.interfaces.web.empresas.contactos.model;

import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */

public class ContactoBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(ContactoBean.class.getName());

    private Integer id;
    @Size(min = 1, max = 50)
    private String nombre;
    @Size(max = 50)
    @Size(max = 250)
    private String descripcion;
    private String telefono;
    @Size(max = 50)
    private String telefono2;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    private String email;
    private Idioma idioma;
    @Size(max = 200)
    private String horario;
    @Size(max = 200)
    private String observaciones;

    public ContactoBean() {
    }

    public ContactoBean(Integer id, String nombre, String descripcion, String telefono, String telefono2, String email, Idioma idioma, String horario, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.email = email;
        this.idioma = idioma;
        this.horario = horario;
        this.observaciones = observaciones;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "ContactoBean{" + "id=" + id + ", nombre=" + nombre + '}';
    }
    
}