/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.notificacions;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface NotificacioRepository {

    Notificacio find(Integer id);
        
    List<Notificacio> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Notificacio> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Notificacio notificacio);
    
    void remove(Notificacio notificacio);

    public List<Notificacio> findPendents();
   
}
