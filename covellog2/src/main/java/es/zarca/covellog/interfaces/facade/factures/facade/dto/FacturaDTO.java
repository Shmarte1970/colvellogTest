/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.factures.facade.dto;

import java.io.Serializable;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public class FacturaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final Integer id;

    public FacturaDTO() {
        this.id = null;
    }
     
    public Integer getId() {
        return id;
    }
   
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDTO)) {
            return false;
        }
        FacturaDTO other = (FacturaDTO) object;
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
