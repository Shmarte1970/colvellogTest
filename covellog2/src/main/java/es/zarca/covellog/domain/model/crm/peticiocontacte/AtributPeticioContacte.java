/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.crm.peticiocontacte;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco
 */
@Entity
@Table(name = "wp_cf7dbplugin_submits")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtributPeticioContacte.findAll", query = "SELECT a FROM AtributPeticioContacte a")
    , @NamedQuery(name = "AtributPeticioContacte.findBySubmitTime", query = "SELECT a FROM AtributPeticioContacte a WHERE a.atributPeticioContactePK.submitTime = :submitTime")
    , @NamedQuery(name = "AtributPeticioContacte.findByFormName", query = "SELECT a FROM AtributPeticioContacte a WHERE a.formName = :formName")
    , @NamedQuery(name = "AtributPeticioContacte.findByFieldName", query = "SELECT a FROM AtributPeticioContacte a WHERE a.atributPeticioContactePK.fieldName = :fieldName")
    , @NamedQuery(name = "AtributPeticioContacte.findByFieldOrder", query = "SELECT a FROM AtributPeticioContacte a WHERE a.fieldOrder = :fieldOrder")})
public class AtributPeticioContacte implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AtributPeticioContactePK atributPeticioContactePK;
    @Size(max = 127)
    @Column(name = "form_name")
    private String formName;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "field_value")
    private String fieldValue;
    @Column(name = "field_order")
    private Integer fieldOrder;
    @Lob
    @Column(name = "file")
    private byte[] file;

    public AtributPeticioContacte() {
    }

    public AtributPeticioContacte(AtributPeticioContactePK atributPeticioContactePK) {
        this.atributPeticioContactePK = atributPeticioContactePK;
    }

    public AtributPeticioContacte(BigDecimal submitTime, String fieldName) {
        this.atributPeticioContactePK = new AtributPeticioContactePK(submitTime, fieldName);
    }

    public AtributPeticioContactePK getAtributPeticioContactePK() {
        return atributPeticioContactePK;
    }

    public void setAtributPeticioContactePK(AtributPeticioContactePK atributPeticioContactePK) {
        this.atributPeticioContactePK = atributPeticioContactePK;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFieldName() {
        return atributPeticioContactePK.getFieldName();
    }
    
    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getFieldOrder() {
        return fieldOrder;
    }

    public void setFieldOrder(Integer fieldOrder) {
        this.fieldOrder = fieldOrder;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atributPeticioContactePK != null ? atributPeticioContactePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtributPeticioContacte)) {
            return false;
        }
        AtributPeticioContacte other = (AtributPeticioContacte) object;
        if ((this.atributPeticioContactePK == null && other.atributPeticioContactePK != null) || (this.atributPeticioContactePK != null && !this.atributPeticioContactePK.equals(other.atributPeticioContactePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.peticiocontacte.AtributPeticioContacte[ atributPeticioContactePK=" + atributPeticioContactePK + " ]";
    }
    
}
