/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.factures.facade;

import es.zarca.covellog.interfaces.facade.factures.facade.dto.FacturaDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface FacturaServiceFacade {

    void crearFactura(FacturaDTO pais);
    
    void editarFactura(FacturaDTO pais);
    
    void eliminarFactura(FacturaDTO pais);
    
    FacturaDTO buscarPerId(Integer id);
    
    List<FacturaDTO> llistarFactures();
    
    List<FacturaDTO> llistarFactures(int start, int size);

    List<FacturaDTO> llistarFactures(Map<String, Object> filters);
    
    List<FacturaDTO> llistarFactures(int start, int size, Map<String, Object> filters);
    
    int llistarFacturesTotalCount();
    
    int llistarFacturesTotalCount(Map<String, Object> filters);

}
