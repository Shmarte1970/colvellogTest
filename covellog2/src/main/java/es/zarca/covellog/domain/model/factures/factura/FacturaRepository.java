/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.factures.factura;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface FacturaRepository {

    Factura find(Integer id);
        
    List<Factura> findAll();
    
    List<Factura> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Factura> findAll(Map<String, Object> filters);
    
    List<Factura> findAll(int start, int size, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Factura factura);
    
    void remove(Factura factura);
   
}
