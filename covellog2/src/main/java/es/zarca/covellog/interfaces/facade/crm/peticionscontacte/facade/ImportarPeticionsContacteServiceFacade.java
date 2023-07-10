/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ImportarPeticionsContacteServiceFacade {

    public Integer importarPeticionsContactePendents();

    public Date getUltimaPeticioContacteConvertida();

}
