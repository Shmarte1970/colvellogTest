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
@Table(name = "tipo_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPago.findAll", query = "SELECT t FROM TipoPago t"),
    @NamedQuery(name = "TipoPago.findById", query = "SELECT t FROM TipoPago t WHERE t.id = :id"),
    @NamedQuery(name = "TipoPago.findByNombre", query = "SELECT t FROM TipoPago t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoPago.findByRequiereCuentaEgreso", query = "SELECT t FROM TipoPago t WHERE t.requiereCuentaEgreso = :requiereCuentaEgreso"),
    @NamedQuery(name = "TipoPago.findByRequiereCuentaIngreso", query = "SELECT t FROM TipoPago t WHERE t.requiereCuentaIngreso = :requiereCuentaIngreso")})
public class TipoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "requiere_cuenta_egreso")
    private Short requiereCuentaEgreso;
    @Column(name = "requiere_cuenta_ingreso")
    private Short requiereCuentaIngreso;

    public TipoPago() {
    }

    public TipoPago(String id) {
        this.id = id;
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

    public Short getRequiereCuentaEgreso() {
        return requiereCuentaEgreso;
    }

    public void setRequiereCuentaEgreso(Short requiereCuentaEgreso) {
        this.requiereCuentaEgreso = requiereCuentaEgreso;
    }

    public Short getRequiereCuentaIngreso() {
        return requiereCuentaIngreso;
    }

    public void setRequiereCuentaIngreso(Short requiereCuentaIngreso) {
        this.requiereCuentaIngreso = requiereCuentaIngreso;
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
        if (!(object instanceof TipoPago)) {
            return false;
        }
        TipoPago other = (TipoPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.empresa.formapago.TipoPago[ id=" + id + " ]";
    }
    
}
