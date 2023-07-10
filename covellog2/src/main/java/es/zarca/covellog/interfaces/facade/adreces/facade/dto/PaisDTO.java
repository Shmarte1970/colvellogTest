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
public class PaisDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final Integer id;
    private String codiIso2;
    private String nom;

    public PaisDTO() {
        this.id = null;
        this.codiIso2 = "";
        this.nom = "";
    }
    
    public PaisDTO(Integer id, String codiIso2, String nom) {
        this.id = id;
        this.codiIso2 = codiIso2;
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public String getCodiIso2() {
        return codiIso2;
    }

    public void setCodiIso2(String codiIso2) {
        this.codiIso2 = codiIso2;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaisDTO)) {
            return false;
        }
        PaisDTO other = (PaisDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "es.zarca.covellog.interfaces.adreces.facade.dto.PaisDTO[ id=" + id + " ]";
    }
    
}
