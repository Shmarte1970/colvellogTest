/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.internal;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacte;
import es.zarca.covellog.domain.model.crm.peticiocontacte.PeticioContacteRepository;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.internal.assembler.PeticioContacteDtoAssembler;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.PeticioContacteServiceFacade;
import es.zarca.covellog.interfaces.facade.crm.peticionscontacte.facade.dto.PeticioContacteDTO;
import java.math.BigDecimal;
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
public class DefaultPeticioContacteServiceFacade implements PeticioContacteServiceFacade {
    
    @Inject
    private PeticioContacteRepository peticioContacteRepository;
    
    private static final Logger LOGGER = Logger.getLogger(DefaultPeticioContacteServiceFacade.class.getName());
    
    private List<PeticioContacteDTO> crearListaDTO(List<PeticioContacte> peticionsContacte) {
        List<PeticioContacteDTO> peticionsContacteDTO = new ArrayList<>(peticionsContacte.size());
        PeticioContacteDtoAssembler assembler = new PeticioContacteDtoAssembler();  
        peticionsContacte.forEach((peticioContacte) -> {
            peticionsContacteDTO.add(assembler.toDto(peticioContacte));
        });
        return peticionsContacteDTO;
    }
    
    @Override
    public List<PeticioContacteDTO> llistarPeticionsContacte(int start, int size) {
        return crearListaDTO(peticioContacteRepository.findAll(start, size));
    }

    @Override
    public int llistarPeticionsContacteTotalCount() {
        return peticioContacteRepository.findAllTotalCount();
    }

    @Override
    public List<PeticioContacteDTO> llistarPeticionsContacte(int start, int size, Ordre ordre, Map<String, Object> filters) {
        return crearListaDTO(peticioContacteRepository.findAll(start, size, ordre, filters));
    }

    @Override
    public int llistarPeticionsContacteTotalCount(Map<String, Object> filters) {
       return peticioContacteRepository.findAllTotalCount(filters);
    }

    @Override
    public PeticioContacteDTO buscarPerSubmitTime(BigDecimal submitTime) {
        PeticioContacteDtoAssembler assembler = new PeticioContacteDtoAssembler();
        return assembler.toDto(peticioContacteRepository.find(submitTime));
    }
    
}
