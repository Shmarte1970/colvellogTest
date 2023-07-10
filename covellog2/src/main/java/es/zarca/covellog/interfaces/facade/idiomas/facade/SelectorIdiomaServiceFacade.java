package es.zarca.covellog.interfaces.facade.idiomas.facade;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.IdiomaDto;
import java.util.List;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface SelectorIdiomaServiceFacade {

    IdiomaDto getIdioma(String codigoIdioma);
    List<IdiomaDto> getIdiomas();

}
