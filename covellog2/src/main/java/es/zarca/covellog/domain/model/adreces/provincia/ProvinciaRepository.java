/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.provincia;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ProvinciaRepository {

    Provincia find(Integer id);
    
    Provincia findByNom(String nom);
            
    Provincia findByCodi(String codi);
    
    Provincia findByCodiPostal(String codiPostal);
    
    List<Provincia> findAll();
    
    List<Provincia> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Provincia> findAll(Map<String, Object> filters);
    
    List<Provincia> findAll(int start, int size, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Provincia provincia);
    
    void remove(Provincia provincia);
   
}
