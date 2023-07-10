/*
 * Proyecto Covellog2
 * 
 * Este archivo ha sido creado para ZARCA y no se puede usar sin su consentimiento.
 */
package es.zarca.covellog.interfaces.facade.adreces.facade;

import es.zarca.covellog.application.adreces.form.AltaProvinciaForm;
import es.zarca.covellog.application.adreces.form.BaixaProvinciaForm;
import es.zarca.covellog.application.adreces.form.EditarProvinciaForm;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.PaisDTO;
import es.zarca.covellog.interfaces.facade.adreces.facade.dto.ProvinciaDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ProvinciaServiceFacade {

    List<ProvinciaDTO> llistarProvincies();
    
    List<ProvinciaDTO> llistarProvincies(int start, int size);
    
    int llistarProvinciesTotalCount();
    
    List<ProvinciaDTO> llistarProvincies(Map<String, Object> filters);
    
    List<ProvinciaDTO> llistarProvincies(int start, int size, Map<String, Object> filters);
    
    int llistarProvinciesTotalCount(Map<String, Object> filters);
    
    ProvinciaDTO buscarPerId(Integer id);
    
    void altaProvincia(AltaProvinciaForm form);
    
    void editarProvincia(EditarProvinciaForm form);
    
    void baixaProvincia(BaixaProvinciaForm form);   
    
    List<PaisDTO> getPaisosPosibles();
    
}
