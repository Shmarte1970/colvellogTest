/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.clients.client;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ClientRepository {

    Client find(Integer id);
        
    List<Client> findAll();
    
    List<Client> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Client> findAll(Map<String, Object> filters);
    
    List<Client> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Client factura);
    
    void remove(Client factura);
   
}
