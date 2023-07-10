/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.application.crm.internal;

import es.zarca.covellog.application.crm.AlgoritmeReparticioOportunitatsService;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.comercials.comercial.ComercialRepository;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitats;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitatsRepository;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author francisco
 */
@Stateless
public class DefaultAlgoritmeReparticioOportunitatsService implements AlgoritmeReparticioOportunitatsService {
    @Inject
    private ReglaReparticioOportunitatsRepository reglaReparticioOportunitatsRepository;
    @Inject
    private ComercialRepository comercialRepository;
    
    @Override
    public void altaRegla(ReglaReparticioOportunitats regla) {
        reglaReparticioOportunitatsRepository.store(regla);
    }

    @Override
    public void editarRegla(ReglaReparticioOportunitats regla) {
        System.out.println("editarRegla: " + regla.getComercial());
        ReglaReparticioOportunitats reglaOriginal = reglaReparticioOportunitatsRepository.find(regla.getId());
        if (reglaOriginal != null) {
            reglaOriginal.setComercial(regla.getComercial());
            reglaOriginal.setMaxim(regla.getMaxim());
            reglaOriginal.setQuantitat(regla.getQuantitat());
            reglaReparticioOportunitatsRepository.store(reglaOriginal);
        }
    }

    @Override
    public void baixaRegla(ReglaReparticioOportunitats regla) {
        reglaReparticioOportunitatsRepository.remove(regla);
    }

    @Override
    public List<Comercial> getComercialsPosibles() {
        return comercialRepository.findAll();
    }
    
}
