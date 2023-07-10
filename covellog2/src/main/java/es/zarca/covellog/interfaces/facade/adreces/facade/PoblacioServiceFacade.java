/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade;

import es.zarca.covellog.application.adreces.form.AltaPoblacioForm;
import es.zarca.covellog.application.adreces.form.BaixaPoblacioForm;
import es.zarca.covellog.application.adreces.form.EditarPoblacioForm;
import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PoblacioDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PoblacioServiceFacade {

    List<PoblacioDTO> llistarPoblacions();
    
    List<PoblacioDTO> llistarPoblacions(int start, int size);
    
    int llistarPoblacionsTotalCount();
    
    List<PoblacioDTO> llistarPoblacions(Map<String, Object> filters);
    
    List<PoblacioDTO> llistarPoblacions(int start, int size, Ordre ordre, Map<String, Object> filters);
    
    int llistarPoblacionsTotalCount(Map<String, Object> filters);
    
    PoblacioDTO buscarPerId(Integer id);
    
    void altaPoblacio(AltaPoblacioForm formulari);
    
    void editarPoblacio(EditarPoblacioForm formulari);
    
    void baixaPoblacio(BaixaPoblacioForm formulari);
    
    List<ProvinciaDTO> getProvinciesPosibles(Integer idPais);
    
    List<PaisDTO> getPaisosPosibles();
    
}
