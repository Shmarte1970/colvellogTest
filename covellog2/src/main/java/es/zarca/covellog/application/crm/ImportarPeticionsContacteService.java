/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.application.crm;

import java.math.BigDecimal;

/**
 *
 * @author Francisco Torralbo
 */
public interface ImportarPeticionsContacteService {
    
    public Integer importarPeticionsContactePendents();  
    public BigDecimal getUltimaPeticioContacteConvertida();
}
