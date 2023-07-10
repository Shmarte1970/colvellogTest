/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.comercials.comercial;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ComercialRepository {

    Comercial find(Integer id);
    Comercial findOrFail(Integer id);
    
    List<Comercial> findAll();
    
    List<Comercial> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Comercial> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Comercial comercial);
    
    void remove(Comercial comercial);
   
}
