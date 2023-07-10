/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.dto;

import java.io.Serializable;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ProvinciaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nom;
    private String codi;
    private String codiPostal;
    private PaisDTO pais;
    
    public ProvinciaDTO() {
    }

    public ProvinciaDTO(Integer id) {
        this.id = id;
    }

    public ProvinciaDTO(Integer id, String nom, String codi, String codiPostal, PaisDTO pais) {
        this.id = id;
        this.nom = nom;
        this.codi = codi;
        this.codiPostal = codiPostal;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }
    
    public PaisDTO getPais() {
        return pais;
    }

    public void setPais(PaisDTO pais) {
        this.pais = pais;
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
        if (!(object instanceof ProvinciaDTO)) {
            return false;
        }
        ProvinciaDTO other = (ProvinciaDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.interfaces.adreces.facade.dto.ProvinciaDTO[ id=" + id + " ]";
    }
    
}
