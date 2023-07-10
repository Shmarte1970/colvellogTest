/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade.internal;

import es.zarca.covellog.interfaces.web.adreces.controller.GestioProvinciaController;
import es.zarca.covellog.domain.model.adreces.provincia.Provincia;
import es.zarca.covellog.domain.model.adreces.provincia.ProvinciaRepository;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.ProvinciaDtoAssembler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import es.zarca.covellog.interfaces.facade.adreces.facade.ProvinciaServiceFacade;
import es.zarca.covellog.application.adreces.ProvinciaService;
import es.zarca.covellog.application.adreces.form.AltaProvinciaForm;
import es.zarca.covellog.application.adreces.form.BaixaProvinciaForm;
import es.zarca.covellog.application.adreces.form.EditarProvinciaForm;
import es.zarca.covellog.domain.model.adreces.pais.Pais;
import es.zarca.covellog.domain.model.adreces.pais.PaisRepository;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.internal.assembler.PaisDtoAssembler;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@ApplicationScoped
public class DefaultProvinciaServiceFacade implements ProvinciaServiceFacade, Serializable {
    
    private static final long serialVersionUID = 1L;
    @Inject
    private ProvinciaService provinciaService;
    @Inject
    private ProvinciaRepository provinciaRepository;
    @Inject
    private PaisRepository paisRepository;
    
    private static final Logger LOGGER = Logger.getLogger(GestioProvinciaController.class.getName());
      
    @Override
    public void altaProvincia(AltaProvinciaForm form) {
        provinciaService.altaProvincia(form);
    }

    @Override
    public void editarProvincia(EditarProvinciaForm form) {
        provinciaService.editarProvincia(form);
    }

    @Override
    public void baixaProvincia(BaixaProvinciaForm form) {
        provinciaService.baixaProvincia(form);
    }
   
    @Override
    public ProvinciaDTO buscarPerId(Integer id) {
        ProvinciaDtoAssembler assembler = new ProvinciaDtoAssembler();
        return assembler.toDto(provinciaRepository.find(id));
    }
    
    @Override
    public List<ProvinciaDTO> llistarProvincies() {
        List<Provincia> provincies = provinciaRepository.findAll();
        List<ProvinciaDTO> provinciesDTO = new ArrayList<>(provincies.size());

        ProvinciaDtoAssembler assembler = new ProvinciaDtoAssembler();  
        provincies.forEach((provincia) -> {
            provinciesDTO.add(assembler.toDto(provincia));
        });
        LOGGER.log(Level.INFO, "{0}.llistarProvincies() => {1}", new Object[]{getClass().getName(), String.valueOf(provincies.size())});
        return provinciesDTO;
    }

    @Override
    public List<ProvinciaDTO> llistarProvincies(int start, int size) {
        List<Provincia> provincies = provinciaRepository.findAll(start, size);
        List<ProvinciaDTO> provinciesDTO = new ArrayList<>(provincies.size());

        ProvinciaDtoAssembler assembler = new ProvinciaDtoAssembler();  
        provincies.forEach((provincia) -> {
            provinciesDTO.add(assembler.toDto(provincia));
        });
        LOGGER.log(Level.INFO, "{0}.llistarProvincies(start:{1},size:{2}) => {3}", new Object[]{getClass().getName(), String.valueOf(start), String.valueOf(size), String.valueOf(provincies.size())});
        return provinciesDTO;
    }
    
    @Override
    public List<ProvinciaDTO> llistarProvincies(Map<String, Object> filters) {
        List<Provincia> provincies = provinciaRepository.findAll();
        List<ProvinciaDTO> provinciesDTO = new ArrayList<>(provincies.size());

        ProvinciaDtoAssembler assembler = new ProvinciaDtoAssembler();  
        provincies.forEach((provincia) -> {
            provinciesDTO.add(assembler.toDto(provincia));
        });
        return provinciesDTO;
    }

    @Override
    public List<ProvinciaDTO> llistarProvincies(int start, int size, Map<String, Object> filters) {
        List<Provincia> provincies = provinciaRepository.findAll(start, size, filters);
        List<ProvinciaDTO> provinciesDTO = new ArrayList<>(provincies.size());

        ProvinciaDtoAssembler assembler = new ProvinciaDtoAssembler();  
        provincies.forEach((provincia) -> {
            provinciesDTO.add(assembler.toDto(provincia));
        });
        return provinciesDTO;
    }

        @Override
    public int llistarProvinciesTotalCount() {
        return provinciaRepository.findAllTotalCount();
    }
    
    @Override
    public int llistarProvinciesTotalCount(Map<String, Object> filters) {
        return provinciaRepository.findAllTotalCount(filters);
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
           
}
