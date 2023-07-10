/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.internal;

import es.zarca.covellog.interfaces.web.crm.peticonscontacte.controller.GestioPeticionsContacteController;
import es.zarca.covellog.domain.model.adreces.poblacio.Poblacio;
import es.zarca.covellog.domain.model.adreces.poblacio.PoblacioRepository;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PoblacioDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import es.zarca.covellog.interfaces.facade.adreces.facade.PoblacioServiceFacade;
import es.zarca.covellog.application.adreces.PoblacioService;
import es.zarca.covellog.application.adreces.form.AltaPoblacioForm;
import es.zarca.covellog.application.adreces.form.BaixaPoblacioForm;
import es.zarca.covellog.application.adreces.form.EditarPoblacioForm;
import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.pais.PaisRepository;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.adreces.provincia.ProvinciaRepository;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.PaisDtoAssembler;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.PoblacioDtoAssembler;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.ProvinciaDtoAssembler;
import java.util.Collection;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class DefaultPoblacioServiceFacade implements PoblacioServiceFacade, Serializable {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private PoblacioService poblacioService;
    @Inject
    private PoblacioRepository poblacioRepository;
    @Inject
    private ProvinciaRepository provinciaRepository;
    @Inject
    private PaisRepository paisRepository;
    
    private static final Logger logger = Logger.getLogger(GestioPeticionsContacteController.class.getName());
    
    @Override
    public List<PoblacioDTO> llistarPoblacions() {
        List<Poblacio> poblacions = poblacioRepository.findAll();
        List<PoblacioDTO> poblacionsDTO = new ArrayList<>(poblacions.size());

        PoblacioDtoAssembler assembler = new PoblacioDtoAssembler();  
        poblacions.forEach((poblacio) -> {
            poblacionsDTO.add(assembler.toDto(poblacio));
        });
        logger.log(Level.INFO, "{0}.llistarPoblacions() => {1}", new Object[]{getClass().getName(), String.valueOf(poblacions.size())});
        return poblacionsDTO;
    }

    @Override
    public List<PoblacioDTO> llistarPoblacions(int start, int size) {
        List<Poblacio> poblacions = poblacioRepository.findAll(start, size);
        List<PoblacioDTO> poblacionsDTO = new ArrayList<>(poblacions.size());

        PoblacioDtoAssembler assembler = new PoblacioDtoAssembler();  
        poblacions.forEach((poblacio) -> {
            poblacionsDTO.add(assembler.toDto(poblacio));
        });
        logger.log(Level.INFO, "{0}.llistarPoblacions(start:{1},size:{2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(poblacions.size())});
        return poblacionsDTO;
    }

    @Override
    public int llistarPoblacionsTotalCount() {
        return poblacioRepository.findAllTotalCount();
    }
    
    @Override
    public List<PoblacioDTO> llistarPoblacions(int start, int size, Ordre ordre, Map<String, Object> filters) {
        List<Poblacio> poblacions = poblacioRepository.findAll(start, size, ordre, filters);
        List<PoblacioDTO> poblacionsDTO = new ArrayList<>(poblacions.size());

        PoblacioDtoAssembler assembler = new PoblacioDtoAssembler();  
        poblacions.forEach((poblacio) -> {
            poblacionsDTO.add(assembler.toDto(poblacio));
        });
        return poblacionsDTO;
    }

    @Override
    public int llistarPoblacionsTotalCount(Map<String, Object> filters) {
        return poblacioRepository.findAllTotalCount(filters);
    }

    @Override
    public List<ProvinciaDTO> getProvinciesPosibles(Integer idPais) {
        Pais pais = paisRepository.find(idPais);  
        if (pais == null) {
            return new ArrayList<>();
        }    
        Collection<Provincia> provincies = pais.getProvinciaCollection();
        List<ProvinciaDTO> provinciesDTO = new ArrayList<>(provincies.size());
        ProvinciaDtoAssembler assembler = new ProvinciaDtoAssembler(); 
        for (Provincia provincia : provincies) {
            provinciesDTO.add(assembler.toDto(provincia));
        }
        return provinciesDTO;
    }
    
    @Override
    public List<PaisDTO> getPaisosPosibles() {
        List<Pais> paisos = paisRepository.findAll();
        List<PaisDTO> paisosDTO = new ArrayList<>(paisos.size());
        PaisDtoAssembler assembler = new PaisDtoAssembler();  
        paisos.forEach((pais) -> {
            paisosDTO.add(assembler.toDto(pais));
        });
        return paisosDTO;
    }
    
    @Override
    public PoblacioDTO buscarPerId(Integer id) {
        PoblacioDtoAssembler assembler = new PoblacioDtoAssembler();
        return assembler.toDto(poblacioRepository.find(id));
    }
    
    @Override
    public void altaPoblacio(AltaPoblacioForm formulari) {
        poblacioService.altaPoblacio(formulari);
    }

    @Override
    public void editarPoblacio(EditarPoblacioForm formulari) {
        poblacioService.editarPoblacio(formulari);
    }

    @Override
    public void baixaPoblacio(BaixaPoblacioForm formulari) {
        poblacioService.baixaPoblacio(formulari);
    }

    
    @Override
    public List<PoblacioDTO> llistarPoblacions(Map<String, Object> filters) {
        List<Poblacio> poblacions = poblacioRepository.findAll();
        List<PoblacioDTO> poblacionsDTO = new ArrayList<>(poblacions.size());

        PoblacioDtoAssembler assembler = new PoblacioDtoAssembler();  
        poblacions.forEach((poblacio) -> {
            poblacionsDTO.add(assembler.toDto(poblacio));
        });
        return poblacionsDTO;
    }
           
}
