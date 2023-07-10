/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade;

import es.zarca.covellog.application.adreces.form.AltaPaisForm;
import es.zarca.covellog.application.adreces.form.BaixaPaisForm;
import es.zarca.covellog.application.adreces.form.EditarPaisForm;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface PaisServiceFacade {

    void altaPais(AltaPaisForm form);
    
    void editarPais(EditarPaisForm form);
    
    void baixaPais(BaixaPaisForm form);
    
    PaisDTO buscarPerId(Integer id);
    
    List<PaisDTO> llistarPaisos();
    
    List<PaisDTO> llistarPaisos(int start, int size);

    List<PaisDTO> llistarPaisos(Map<String, Object> filters);
    
    List<PaisDTO> llistarPaisos(int start, int size, Map<String, Object> filters);
    
    int llistarPaisosTotalCount();
    
    int llistarPaisosTotalCount(Map<String, Object> filters);

}
