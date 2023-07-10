/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class PeticioContacteDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private BigDecimal submitTime;
    private String form;
    private String nom;
    private String email;
    private String empresa;
    private String telefon;
    private String assumpte;
    private String missatje;
    private String ip;
    
    public PeticioContacteDTO() {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAssumpte() {
        return assumpte;
    }

    public void setAssumpte(String assumpte) {
        this.assumpte = assumpte;
    }

    public String getMissatje() {
        return missatje;
    }

    public void setMissatje(String missatje) {
        this.missatje = missatje;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.submitTime);
        hash = 11 * hash + Objects.hashCode(this.nom);
        hash = 11 * hash + Objects.hashCode(this.email);
        hash = 11 * hash + Objects.hashCode(this.empresa);
        hash = 11 * hash + Objects.hashCode(this.telefon);
        hash = 11 * hash + Objects.hashCode(this.assumpte);
        hash = 11 * hash + Objects.hashCode(this.missatje);
        hash = 11 * hash + Objects.hashCode(this.ip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PeticioContacteDTO other = (PeticioContacteDTO) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.telefon, other.telefon)) {
            return false;
        }
        if (!Objects.equals(this.assumpte, other.assumpte)) {
            return false;
        }
        if (!Objects.equals(this.missatje, other.missatje)) {
            return false;
        }
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        if (!Objects.equals(this.submitTime, other.submitTime)) {
            return false;
        }
        return true;
    }
    
    
    
}
