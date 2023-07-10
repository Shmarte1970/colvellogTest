/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.crm;

import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat;
import java.util.List;


/**
 *
 * @author Francisco Torralbo
 */
public interface OportunitatService {
    void altaOportunitat(Oportunitat oportunitat);
    void editarOportunitat(Oportunitat oportunitat);
    void baixaOportunitat(Oportunitat oportunitat);
    List<DivisioComercial> getDivisionsComercialsPosibles(Oportunitat oportunitat);
    List<EstatOportunitat> getEstatsPosibles(Oportunitat oportunitat);
    List<OrigenOportunitat> getOrigensPosibles(Oportunitat oportunitat);
    List<Comercial> getComercialsPosibles(Oportunitat oportunitat);
}
