/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.pais;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PaisRepository {

    Pais find(Integer id);
    
    Pais find(CodiIsoPais2 codi);
    
    List<Pais> findAll();
    
    List<Pais> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Pais> findAll(Map<String, Object> filters);
    
    List<Pais> findAll(int start, int size, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Pais pais);
    
    void remove(Pais pais);
   
}
