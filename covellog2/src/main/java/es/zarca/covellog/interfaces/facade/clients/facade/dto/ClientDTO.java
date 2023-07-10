/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.clients.facade.dto;

import java.io.Serializable;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final Integer id;
    private final String cif;
    private final String nom;
    private final String poblacio;
    private final String provincia;
    
    public ClientDTO() {
        this.id = 0;
        this.cif = "";
        this.nom = "";
        this.poblacio = "";
        this.provincia = ""; 
    }
    
    public ClientDTO(Integer id, String cif, String nom, String poblacio, String provincia) {
        this.id = id;
        this.cif = cif;
        this.nom = nom;
        this.poblacio = poblacio;
        this.provincia = provincia; 
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getCif() {
        return cif;
    }
    
    public String getNom() {
        return nom;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public String getProvincia() {
        return provincia;
    }
   
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientDTO)) {
            return false;
        }
        ClientDTO other = (ClientDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "es.zarca.covellog.interfaces.factures.facade.dto.FacturaDTO[ id=" + id + " ]";
    }
    
}
