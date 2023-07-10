/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.domain.model.crm.peticiocontacte;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francisco
 */
@Embeddable
public class AtributPeticioContactePK implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "submit_time")
    private BigDecimal submitTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 127)
    @Column(name = "field_name")
    private String fieldName;

    public AtributPeticioContactePK() {
    }

    public AtributPeticioContactePK(BigDecimal submitTime, String fieldName) {
        this.submitTime = submitTime;
        this.fieldName = fieldName;
    }

    public BigDecimal getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(BigDecimal submitTime) {
        this.submitTime = submitTime;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (submitTime != null ? submitTime.hashCode() : 0);
        hash += (fieldName != null ? fieldName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtributPeticioContactePK)) {
            return false;
        }
        AtributPeticioContactePK other = (AtributPeticioContactePK) object;
        if ((this.submitTime == null && other.submitTime != null) || (this.submitTime != null && !this.submitTime.equals(other.submitTime))) {
            return false;
        }
        if ((this.fieldName == null && other.fieldName != null) || (this.fieldName != null && !this.fieldName.equals(other.fieldName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.domain.model.peticiocontacte.AtributPeticioContactePK[ submitTime=" + submitTime + ", fieldName=" + fieldName + " ]";
    }
    
}
