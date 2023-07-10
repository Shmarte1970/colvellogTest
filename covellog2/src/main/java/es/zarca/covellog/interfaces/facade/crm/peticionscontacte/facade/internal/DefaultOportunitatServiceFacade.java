/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.internal;

import es.zarca.covellog.application.crm.AlgoritmeReparticioOportunitatsService;
import es.zarca.covellog.application.crm.OportunitatService;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.comercials.divisiocomercial.DivisioComercial;
import es.zarca.covellog.domain.model.crm.oportunitat.EstatOportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.Oportunitat;
import es.zarca.covellog.domain.model.crm.oportunitat.OportunitatRepository;
import es.zarca.covellog.domain.model.crm.oportunitat.OrigenOportunitat;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.OportunitatServiceFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@ApplicationScoped
public class DefaultOportunitatServiceFacade implements OportunitatServiceFacade, Serializable {
    private static final Logger logger = Logger.getLogger(DefaultOportunitatServiceFacade.class.getName());
    private static final long serialVersionUID = 1L;
    @Inject
    private AlgoritmeReparticioOportunitatsService algoritmeService;
    @Inject
    private OportunitatService oportunitatService;
    @Inject
    private OportunitatRepository oportunitatRepository;
    @Inject
    private ComercialRepository comercialRepository;
    
    @Override
    public List<Oportunitat> llistarOportunitats(int start, int size) {
        return oportunitatRepository.findAll(start, size);
    }

    @Override
    public int llistarOportunitatsTotalCount() {
        return oportunitatRepository.findAllTotalCount();
    }

    @Override
    public List<Oportunitat> llistarOportunitats(int start, int size, Ordre ordre, Map<String, Object> filters) {
        return oportunitatRepository.findAll(start, size, ordre, filters);
    }

    @Override
    public int llistarOportunitatsTotalCount(Map<String, Object> filters) {
        return oportunitatRepository.findAllTotalCount(filters);
    }

    @Override
    public Oportunitat buscarPerId(Integer id) {
        return oportunitatRepository.find(id);
    }

    @Override
    public void altaOportunitat(Oportunitat oportunitat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarOportunitat(Oportunitat oportunitat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void baixaOportunitat(Oportunitat oportunitat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DivisioComercial> getDivisionsComercialsPosibles(Oportunitat oportunitat) {
        if (oportunitat == null) {
            return new ArrayList();
        }
        return oportunitatService.getDivisionsComercialsPosibles(oportunitat);
    }

    @Override
    public List<EstatOportunitat> getEstatsPosibles(Oportunitat oportunitat) {
        if (oportunitat == null) {
            return new ArrayList();
        }
        return oportunitatService.getEstatsPosibles(oportunitat);
    }

    @Override
    public List<OrigenOportunitat> getOrigensPosibles(Oportunitat oportunitat) {
        if (oportunitat == null) {
            return new ArrayList();
        }
        return oportunitatService.getOrigensPosibles(oportunitat);
    }
    
    @Override
    public List<Comercial> getComercialsPosibles(Oportunitat oportunitat) {
        if (oportunitat == null) {
            return new ArrayList();
        }
        return oportunitatService.getComercialsPosibles(oportunitat);
    }

    @Override
    public Comercial buscarComercialPerId(Integer id) {
        return comercialRepository.find(id);
    }

    
    
}
