package es.zarca.covellog.interfaces.facade.idiomas.facade.internal;

import es.zarca.covellog.interfaces.web.adreces.controller.GestioPaisController;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Inject;
import es.zarca.covellog.application.idiomas.SelectorIdiomaService;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import es.zarca.covellog.interfaces.facade.empresas.facade.dto.IdiomaDto;
import es.zarca.covellog.interfaces.facade.empresas.facade.internal.assembler.IdiomaAssembler;
import es.zarca.covellog.interfaces.facade.idiomas.facade.SelectorIdiomaServiceFacade;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
//@ApplicationScoped
@Stateless
public class DefaultSelectorIdiomaServiceFacade implements SelectorIdiomaServiceFacade, Serializable {

    private static final Logger LOGGER = Logger.getLogger(GestioPaisController.class.getName());
    private static final long serialVersionUID = 1L;
    

    @Inject
    private SelectorIdiomaService selectorIdiomaService;

    @Override
    public IdiomaDto getIdioma(String codigoIdioma) {
        return IdiomaAssembler.toDto(selectorIdiomaService.getIdioma(new CodigoIdioma(codigoIdioma)));
    }

    @Override
    public List<IdiomaDto> getIdiomas() {
        return IdiomaAssembler.toDto(selectorIdiomaService.getIdiomas());
    }

}
