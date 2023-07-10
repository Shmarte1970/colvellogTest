/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.comercials.divisiocomercial;

import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface DivisioComercialRepository {

    DivisioComercial find(String id);
        
    List<DivisioComercial> findAll();

    int findAllTotalCount();
    
}
