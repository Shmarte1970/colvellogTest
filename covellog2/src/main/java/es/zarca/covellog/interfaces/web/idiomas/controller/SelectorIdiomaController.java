package es.zarca.covellog.interfaces.web.idiomas.controller;

import es.zarca.covellog.interfaces.facade.empresas.facade.dto.IdiomaDto;
import es.zarca.covellog.interfaces.facade.idiomas.facade.SelectorIdiomaServiceFacade;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author francisco
 */
@Named("selectorIdiomaController")
@RequestScoped
public class SelectorIdiomaController implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(SelectorIdiomaController.class.getName());

    @Inject
    private SelectorIdiomaServiceFacade facade;
    
    public IdiomaDto getIdioma(String codigoIdioma) {
        return facade.getIdioma(codigoIdioma);
    }
    
    public List<IdiomaDto> getIdiomas() {
        return facade.getIdiomas();
    }
    
}