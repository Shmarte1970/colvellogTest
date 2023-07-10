/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.crm.peticiocontacte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "wp_cf7dbplugin_st")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PeticioContacte.findAll", query = "SELECT p FROM PeticioContacte p")
    , @NamedQuery(name = "PeticioContacte.findBySubmitTime", query = "SELECT p FROM PeticioContacte p WHERE p.submitTime = :submitTime")
    , @NamedQuery(name = "PeticioContacte.findBySubmitTimeMajor", query = "SELECT p FROM PeticioContacte p WHERE p.submitTime > :submitTime")})
public class PeticioContacte implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "submit_time")
    private BigDecimal submitTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "submit_time", referencedColumnName = "submit_time",updatable = false, insertable = false, nullable = false)
    private Collection<AtributPeticioContacte> atributs;
    
    public PeticioContacte() {
    }

    public PeticioContacte(BigDecimal submitTime) {
        this.submitTime = submitTime;
    }

    public BigDecimal getSubmitTime() {
        return submitTime;
    }
    
    public Date getSubmitTimeDate() {
        return new Date(submitTime.multiply(new BigDecimal(1000)).longValue());
    }

    public void setSubmitTime(BigDecimal submitTime) {
        this.submitTime = submitTime;
    }
    
    public String getForm() {
        if (atributs.isEmpty()) {
            return null;
        }
        return atributs.iterator().next().getFormName();
    }
    
    private String getAtribut(String nom) {
        for (AtributPeticioContacte atribut : atributs) {
            if (atribut.getFieldName().equals(nom)) {
                return atribut.getFieldValue();
            }
        }
        return "[INDEFINIDO]";
    }
    
    public String getNom() {
        return getAtribut("your-name");
    }
    
    public String getEmail() {
        return getAtribut("your-email");
    }
    
    public String getEmpresa() {
        return getAtribut("empresa");
    }
    
    public String getTelefon() {
        return getAtribut("tel");
    }
    
    public String getAssumpte() {
        return getAtribut("your-subject");
    }
    
    public String getMissatje() {
        return getAtribut("your-message");
    }
    
    public String getIp() {
        return getAtribut("Submitted From");
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (submitTime != null ? submitTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeticioContacte)) {
            return false;
        }
        PeticioContacte other = (PeticioContacte) object;
        return !((this.submitTime == null && other.submitTime != null) || (this.submitTime != null && !this.submitTime.equals(other.submitTime)));
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.peticiocontacte.PeticioContacte[ submitTime=" + submitTime + " ]";
    }
    
}
