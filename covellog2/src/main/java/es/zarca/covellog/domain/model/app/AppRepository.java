/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.app;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
 public interface AppRepository {

    App find(String id);
        
    List<App> findAll();
    
    List<App> findAll(int start, int size);

    int findAllTotalCount();
    
    List<App> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(App app);
    
    void remove(App app);
   
}
