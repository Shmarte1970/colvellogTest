package es.zarca.covellog.domain.model.empresa;

import es.zarca.covellog.domain.model.contactos.Contacto;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "empresa_contacto")
@XmlRootElement
public class EmpresaContacto extends Contacto implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private Empresa empresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "prioridad")
    private Integer prioridad;
   

    public EmpresaContacto() {
    }

    public EmpresaContacto(
        String nombre,
        String apellidos,
        CanalesContacto canalesContacto, 
        String descripcion, 
        Idioma idioma, 
        String observaciones, 
        String horario
    ) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.canalesContacto = canalesContacto;
        this.descripcion = descripcion; 
        this.idioma = idioma;
        this.observaciones = observaciones;
        this.horario = horario;
    }    

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    
    
}
