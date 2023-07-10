/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.email;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ServidorEmailRepository {

    ServidorEmail find(String id);
        
    List<ServidorEmail> findAll(int start, int size);

    int findAllTotalCount();
    
    List<ServidorEmail> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(ServidorEmail servidorEmail);
    
    void remove(ServidorEmail servidorEmail);
    
}
