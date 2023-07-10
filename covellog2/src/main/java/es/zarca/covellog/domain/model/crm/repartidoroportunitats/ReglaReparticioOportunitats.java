/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.crm.repartidoroportunitats;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "regla_reparticio_oportunitats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReglaReparticioOportunitats.findAll", query = "SELECT r FROM ReglaReparticioOportunitats r")
    , @NamedQuery(name = "ReglaReparticioOportunitats.findById", query = "SELECT r FROM ReglaReparticioOportunitats r WHERE r.id = :id")
    , @NamedQuery(name = "ReglaReparticioOportunitats.findByMaxim", query = "SELECT r FROM ReglaReparticioOportunitats r WHERE r.maxim = :maxim")
    , @NamedQuery(name = "ReglaReparticioOportunitats.findByQuantitat", query = "SELECT r FROM ReglaReparticioOportunitats r WHERE r.quantitat = :quantitat")})
public class ReglaReparticioOportunitats implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxim")
    private int maxim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantitat")
    private int quantitat;
    @JoinColumn(name = "repartidor_oportunitats_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RepartidorOportunitats repartidorOportunitats;
    @JoinColumn(name = "comercial_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Comercial comercial;

    public ReglaReparticioOportunitats() {
    }

    public ReglaReparticioOportunitats(Integer id) {
        this.id = id;
    }

    public ReglaReparticioOportunitats(Integer id, int maxim, int quantitat) {
        this.id = id;
        this.maxim = maxim;
        this.quantitat = quantitat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMaxim() {
        return maxim;
    }

    public void setMaxim(int maxim) {
        this.maxim = maxim;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public RepartidorOportunitats getRepartidorOportunitats() {
        return repartidorOportunitats;
    }

    public void setRepartidorOportunitats(RepartidorOportunitats repartidorOportunitats) {
        this.repartidorOportunitats = repartidorOportunitats;
    }

    public Comercial getComercial() {
        return comercial;
    }

    public void setComercial(Comercial comercial) {
        this.comercial = comercial;
    }
    
    public Boolean esPlena() {
        return quantitat >= maxim;
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
        if (!(object instanceof ReglaReparticioOportunitats)) {
            return false;
        }
        ReglaReparticioOportunitats other = (ReglaReparticioOportunitats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitats[ id=" + id + " ]";
    }

    void consumir() {
        quantitat = quantitat + 1;
    }
    
}
