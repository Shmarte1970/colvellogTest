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
public class PoblacioDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nom;
    private String codiMunicipi;
    private short dc;
    private ProvinciaDTO provincia;

    public PoblacioDTO() {
    }
    
    public PoblacioDTO(Integer id, String nom, String codiMunicipi, short dc, ProvinciaDTO provincia) {
        this.id = id;
        this.nom = nom;
        this.codiMunicipi = codiMunicipi;
        this.dc = dc;
        this.provincia = provincia;
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

    public String getCodiMunicipi() {
        return codiMunicipi;
    }

    public void setCodiMunicipi(String codiMunicipi) {
        this.codiMunicipi = codiMunicipi;
    }

    public short getDc() {
        return dc;
    }

    public void setDc(short dc) {
        this.dc = dc;
    }

    public ProvinciaDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaDTO provincia) {
        this.provincia = provincia;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoblacioDTO)) {
            return false;
        }
        PoblacioDTO other = (PoblacioDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.zarca.covellog.interfaces.adreces.facade.dto.PoblacioDTO[ id=" + id + " ]";
    }
}
