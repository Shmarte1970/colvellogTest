/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.notificacions;

import es.zarca.covellog.domain.model.usuarios.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "notificacio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificacio.findAll", query = "SELECT n FROM Notificacio n")
    , @NamedQuery(name = "Notificacio.findById", query = "SELECT n FROM Notificacio n WHERE n.id = :id")
    , @NamedQuery(name = "Notificacio.findByTitol", query = "SELECT n FROM Notificacio n WHERE n.titol = :titol")
    , @NamedQuery(name = "Notificacio.findPendents", query = "SELECT n FROM Notificacio n WHERE n.dataEnviament IS NULL")
    , @NamedQuery(name = "Notificacio.findByDataEnviament", query = "SELECT n FROM Notificacio n WHERE n.dataEnviament = :dataEnviament")})
public class Notificacio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "titol")
    private String titol;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 16777215)
    @Column(name = "detall")
    private String detall;
    @Column(name = "dataEnviament")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnviament;
    @JoinColumn(name = "destinatari_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario destinatari;

    public Notificacio() {
    }

    public Notificacio(Integer id) {
        this.id = id;
    }

    public Notificacio(Usuario destinatari, String titol, String detall) {
        this.destinatari = destinatari;
        this.titol = titol;
        this.detall = detall;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }
    
    public String getDetall() {
        return detall;
    }

    public void setDetall(String detall) {
        this.detall = detall;
    }

    public Date getDataEnviament() {
        return dataEnviament;
    }

    public void setDataEnviament(Date dataEnviament) {
        this.dataEnviament = dataEnviament;
    }

    public Usuario getDestinatari() {
        return destinatari;
    }

    public void setDestinatari(Usuario destinatari) {
        this.destinatari = destinatari;
    }
    
    public void marcarEnviada() {
        dataEnviament = new Date();
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
        if (!(object instanceof Notificacio)) {
            return false;
        }
        Notificacio other = (Notificacio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.notificacions.Notificacio[ id=" + id + " ]";
    }

    
    
}
