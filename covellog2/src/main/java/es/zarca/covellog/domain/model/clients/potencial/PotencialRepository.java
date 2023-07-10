/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.potencial;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PotencialRepository {

    Potencial2 find(Integer id);
    
    Potencial2 findByNom(String nom);
        
    List<Potencial2> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Potencial2> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Potencial2 potencial);
    
    void remove(Potencial2 potencial);
    
}
