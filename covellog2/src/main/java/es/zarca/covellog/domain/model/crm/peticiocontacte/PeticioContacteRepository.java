/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.crm.peticiocontacte;

import es.zarca.covellog.domain.model.app.Ordre;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PeticioContacteRepository {
    
    PeticioContacte find(BigDecimal submitTime);
    
    List<PeticioContacte> findAll();
    
    List<PeticioContacte> findAll(int start, int size);

    int findAllTotalCount();
    
    List<PeticioContacte> findAll(Map<String, Object> filters);
    
    List<PeticioContacte> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(PeticioContacte peticioContacte);
    
    void remove(PeticioContacte peticioContacte);

    public List<PeticioContacte> findBySubmitTimeMajor(BigDecimal submitTime, int start, int size);
   
}
