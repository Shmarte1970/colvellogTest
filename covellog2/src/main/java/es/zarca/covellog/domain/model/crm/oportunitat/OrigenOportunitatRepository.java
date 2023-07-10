/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.domain.model.crm.oportunitat;

import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface OrigenOportunitatRepository {

    OrigenOportunitat find(String id);
        
    List<OrigenOportunitat> findAll();

    int findAllTotalCount();
    
}
