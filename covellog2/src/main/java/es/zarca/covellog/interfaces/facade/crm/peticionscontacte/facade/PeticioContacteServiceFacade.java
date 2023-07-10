/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.dto.PeticioContacteDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PeticioContacteServiceFacade {
    
    List<PeticioContacteDTO> llistarPeticionsContacte(int start, int size);
    
    int llistarPeticionsContacteTotalCount();
    
    List<PeticioContacteDTO> llistarPeticionsContacte(int start, int size, Ordre ordre, Map<String, Object> filters);
    
    int llistarPeticionsContacteTotalCount(Map<String, Object> filters);
    
    PeticioContacteDTO buscarPerSubmitTime(BigDecimal submitTime);
    
}
