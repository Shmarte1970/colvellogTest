/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.adreces.poblacio;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PoblacioRepository {

    Poblacio find(Integer id);
    
    List<Poblacio> findAll();
    
    List<Poblacio> findAll(int start, int size);

    int findAllTotalCount();
    
    List<Poblacio> findAll(Map<String, Object> filters);
    
    List<Poblacio> findAll(int start, int size, Ordre ordre, Map<String, Object> filters);

    int findAllTotalCount(Map<String, Object> filters);
    
    void store(Poblacio poblacio);
    
    void remove(Poblacio poblacio);
   
}
