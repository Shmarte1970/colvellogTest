/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.internal;

import es.zarca.covellog.application.crm.AlgoritmeReparticioOportunitatsService;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.comercials.comercial.Comercial;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitats;
import es.zarca.covellog.domain.model.crm.repartidoroportunitats.ReglaReparticioOportunitatsRepository;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.AlgoritmeReparticioOportunitatsServiceFacade;
import java.io.Serializable;
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
public class DefaultAlgoritmeReparticioOportunitatsServiceFacade implements AlgoritmeReparticioOportunitatsServiceFacade, Serializable {
    private static final Logger logger = Logger.getLogger(DefaultAlgoritmeReparticioOportunitatsServiceFacade.class.getName());
    private static final long serialVersionUID = 1L;
    @Inject
    private AlgoritmeReparticioOportunitatsService algoritmeService;
    @Inject
    private ReglaReparticioOportunitatsRepository reglaReparticioOportunitatsRepository;
    
    @Override
    public List<ReglaReparticioOportunitats> llistarRegles(int start, int size) {
        return reglaReparticioOportunitatsRepository.findAll(start, size);
    }

    @Override
    public int llistarReglesTotalCount() {
        return reglaReparticioOportunitatsRepository.findAllTotalCount();
    }

    @Override
    public List<ReglaReparticioOportunitats> llistarRegles(int start, int size, Ordre ordre, Map<String, Object> filters) {
        return reglaReparticioOportunitatsRepository.findAll(start, size, ordre, filters);
    }

    @Override
    public int llistarReglesTotalCount(Map<String, Object> filters) {
        return reglaReparticioOportunitatsRepository.findAllTotalCount(filters);
    }

    @Override
    public ReglaReparticioOportunitats buscarPerId(Integer id) {
        return reglaReparticioOportunitatsRepository.find(id);
    }

    @Override
    public void altaRegla(ReglaReparticioOportunitats regla) {
        System.out.println("altaRegla: " + regla.getComercial());
        algoritmeService.altaRegla(regla);
    }

    @Override
    public void editarRegla(ReglaReparticioOportunitats regla) {
        System.out.println("****************");
        System.out.println("****************");
        System.out.println("****************");
        System.out.println("****************");
        System.out.println(regla.getComercial());
        algoritmeService.editarRegla(regla);
    }

    @Override
    public void baixaRegla(ReglaReparticioOportunitats regla) {
        algoritmeService.baixaRegla(regla);
    }

    @Override
    public List<Comercial> getComercialsPosibles() {
        return algoritmeService.getComercialsPosibles();
    }
    
}
