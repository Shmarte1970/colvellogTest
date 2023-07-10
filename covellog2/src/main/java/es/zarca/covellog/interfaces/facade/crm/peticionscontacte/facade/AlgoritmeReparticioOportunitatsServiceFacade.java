/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitats;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface AlgoritmeReparticioOportunitatsServiceFacade {
    
    List<ReglaReparticioOportunitats> llistarRegles(int start, int size);
    
    int llistarReglesTotalCount();
    
    List<ReglaReparticioOportunitats> llistarRegles(int start, int size, Ordre ordre, Map<String, Object> filters);
    
    int llistarReglesTotalCount(Map<String, Object> filters);
    
    ReglaReparticioOportunitats buscarPerId(Integer id);
    
    void altaRegla(ReglaReparticioOportunitats regla);
    void editarRegla(ReglaReparticioOportunitats regla);
    void baixaRegla(ReglaReparticioOportunitats regla);
    
    List<Comercial> getComercialsPosibles();
    
}
