/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.crm.oportunitat;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface OportunitatRepository {

    Oportunitat find(Integer id);
        
    List<Oportunitat> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Oportunitat> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Oportunitat oportunitat);
    
    void remove(Oportunitat oportunitat);
    
    List<EstatOportunitat> findAllEstatsPosibles();
   
}
