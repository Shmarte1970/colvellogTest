/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface OportunitatServiceFacade {
    
    List<Oportunitat> llistarOportunitats(int start, int size);
    
    int llistarOportunitatsTotalCount();
    
    List<Oportunitat> llistarOportunitats(int start, int size, Ordre ordre, Map<String, Object> filters);
    
    int llistarOportunitatsTotalCount(Map<String, Object> filters);
    
    Oportunitat buscarPerId(Integer id);
    
    void altaOportunitat(Oportunitat oportunitat);
    void editarOportunitat(Oportunitat oportunitat);
    void baixaOportunitat(Oportunitat oportunitat);
    List<DivisioComercial> getDivisionsComercialsPosibles(Oportunitat oportunitat);
    List<EstatOportunitat> getEstatsPosibles(Oportunitat oportunitat);
    List<OrigenOportunitat> getOrigensPosibles(Oportunitat oportunitat);
    List<Comercial> getComercialsPosibles(Oportunitat oportunitat);

    Comercial buscarComercialPerId(Integer id);
       
}
