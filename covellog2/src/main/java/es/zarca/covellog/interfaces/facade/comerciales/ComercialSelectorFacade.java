package es.zarca.covellog.interfaces.facade.comerciales;

import es.zarca.covellog.interfaces.facade.comerciales.dto.ComercialDto;
import java.util.List;


/**
 *
 * @author francisco
 */
public interface ComercialSelectorFacade {
    List<ComercialDto> getComercialesPosibles();
    ComercialDto getComercial(Integer comercialId);
}
