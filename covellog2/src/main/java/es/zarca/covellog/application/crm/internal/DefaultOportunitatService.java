/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.crm.internal;

import es.zarca.covellog.application.crm.OportunitatService;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.crm.exception.OportunitatNotFoundException;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitatRepository;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OportunitatRepository;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitatRepository;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercialRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultOportunitatService implements OportunitatService {
    @Inject
    private OportunitatRepository oportunitatRepository;
    @Inject
    private ComercialRepository comercialRepository;
    @Inject
    private DivisioComercialRepository divisioComercialRepository;
    @Inject
    private EstatOportunitatRepository estatOportunitatRepository;
    @Inject
    private OrigenOportunitatRepository origenOportunitatRepository;

    @Override
    public void altaOportunitat(Oportunitat oportunitat) {
        oportunitatRepository.store(oportunitat);
    }

    @Override
    public void editarOportunitat(Oportunitat oportunitat) {
        Oportunitat oportunitatAntiga = oportunitatRepository.find(oportunitat.getId());
        if (oportunitatAntiga == null) {
            throw new OportunitatNotFoundException(oportunitat.getId());
        }
    }

    @Override
    public void baixaOportunitat(Oportunitat oportunitat) {
        Oportunitat oportunitatAntiga = oportunitatRepository.find(oportunitat.getId());
        if (oportunitatAntiga == null) {
            throw new OportunitatNotFoundException(oportunitat.getId());
        }
        oportunitatRepository.remove(oportunitat);
    }

    @Override
    public List<DivisioComercial> getDivisionsComercialsPosibles(Oportunitat oportunitat) {
        return divisioComercialRepository.findAll();
    }

    @Override
    public List<EstatOportunitat> getEstatsPosibles(Oportunitat oportunitat) {
        return estatOportunitatRepository.findAll();
    }

    @Override
    public List<OrigenOportunitat> getOrigensPosibles(Oportunitat oportunitat) {
        return origenOportunitatRepository.findAll();
    }

    @Override
    public List<Comercial> getComercialsPosibles(Oportunitat oportunitat) {
        return comercialRepository.findAll();
    }
    
}
