/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.empresa.formapago;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "tipo_vencimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoVencimiento.findAll", query = "SELECT t FROM TipoVencimiento t"),
    @NamedQuery(name = "TipoVencimiento.findById", query = "SELECT t FROM TipoVencimiento t WHERE t.id = :id"),
    @NamedQuery(name = "TipoVencimiento.findByNombre", query = "SELECT t FROM TipoVencimiento t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoVencimiento.findByDias", query = "SELECT t FROM TipoVencimiento t WHERE t.dias = :dias")})
public class TipoVencimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dias")
    private short dias;

    public TipoVencimiento() {
    }

    public TipoVencimiento(String id) {
        this.id = id;
    }

    public TipoVencimiento(String id, String nombre, short dias) {
        this.id = id;
        this.nombre = nombre;
        this.dias = dias;
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

    public short getDias() {
        return dias;
    }

    public void setDias(short dias) {
        this.dias = dias;
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
        if (!(object instanceof TipoVencimiento)) {
            return false;
        }
        TipoVencimiento other = (TipoVencimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.formapago.TipoVencimiento[ id=" + id + " ]";
    }
    
}
