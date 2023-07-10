/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.internal;

import es.zarca.covellog.interfaces.web.adreces.controller.GestioPaisController;
import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.pais.PaisRepository;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.PaisDtoAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import es.zarca.covellog.interfaces.facade.adreces.facade.PaisServiceFacade;
import es.zarca.covellog.application.adreces.PaisService;
import es.zarca.covellog.application.adreces.form.AltaPaisForm;
import es.zarca.covellog.application.adreces.form.BaixaPaisForm;
import es.zarca.covellog.application.adreces.form.EditarPaisForm;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@RolesAllowed("ADMIN")
//@ApplicationScoped
@Stateless
public class DefaultPaisServiceFacade implements PaisServiceFacade, Serializable {

    private static final Logger LOGGER = Logger.getLogger(GestioPaisController.class.getName());
    private static final long serialVersionUID = 1L;
    

    @Inject
    private PaisService paisService;
    @Inject
    private PaisRepository paisRepository;
      
    @Override
    public void altaPais(AltaPaisForm form) {
        paisService.altaPais(form);
    }

    @Override
    public void editarPais(EditarPaisForm form) {
       paisService.modificarPais(form);
    }

    @Override
    public void baixaPais(BaixaPaisForm form) {
        paisService.baixaPais(form);
    }
    
    @Override
    public PaisDTO buscarPerId(Integer id) {
        PaisDtoAssembler assembler = new PaisDtoAssembler();
        return assembler.toDto(paisRepository.find(id));
    }
  
    @Override
    public List<PaisDTO> llistarPaisos() {
        List<Pais> paisos = paisRepository.findAll();
        List<PaisDTO> paisosDTO = new ArrayList<>(paisos.size());

        PaisDtoAssembler assembler = new PaisDtoAssembler();  
        paisos.forEach((pais) -> {
            paisosDTO.add(assembler.toDto(pais));
        });
        LOGGER.log(Level.INFO, "{0}.llistarPaisos() => {1}", new Object[]{getClass().getName(), String.valueOf(paisos.size())});
        return paisosDTO;
    }
    
    @Override
    public List<PaisDTO> llistarPaisos(int start, int size) {
        List<Pais> paisos = paisRepository.findAll(start, size);
        List<PaisDTO> paisosDTO = new ArrayList<>(paisos.size());

        PaisDtoAssembler assembler = new PaisDtoAssembler();  
        paisos.forEach((pais) -> {
            paisosDTO.add(assembler.toDto(pais));
        });
        LOGGER.log(Level.INFO, "{0}.llistarPaisos(start:{1},size:{2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(paisos.size())});
        return paisosDTO;
    }
    
    @Override
    public List<PaisDTO> llistarPaisos(Map<String, Object> filters) {
        List<Pais> paisos = paisRepository.findAll();
        List<PaisDTO> paisosDTO = new ArrayList<>(paisos.size());

        PaisDtoAssembler assembler = new PaisDtoAssembler();  
        paisos.forEach((pais) -> {
            paisosDTO.add(assembler.toDto(pais));
        });
        return paisosDTO;
    }

    @Override
    public List<PaisDTO> llistarPaisos(int start, int size, Map<String, Object> filters) {
        
//        System.out.println("jodienda: " + principal.getName());
        List<Pais> paisos = paisRepository.findAll(start, size, filters);
        List<PaisDTO> paisosDTO = new ArrayList<>(paisos.size());

        PaisDtoAssembler assembler = new PaisDtoAssembler();  
        paisos.forEach((pais) -> {
            paisosDTO.add(assembler.toDto(pais));
        });
        return paisosDTO;
    }

    @Override
    public int llistarPaisosTotalCount() {
        return paisRepository.findAllTotalCount();
    }
    
    @Override
    public int llistarPaisosTotalCount(Map<String, Object> filters) {
       return paisRepository.findAllTotalCount(filters);
    }

}
