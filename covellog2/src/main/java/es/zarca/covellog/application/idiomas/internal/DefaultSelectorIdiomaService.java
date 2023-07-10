package es.zarca.covellog.application.idiomas.internal;

import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import es.zarca.covellog.application.idiomas.SelectorIdiomaService;
import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.IdiomaRepository;


/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
@Stateless
public class DefaultSelectorIdiomaService implements SelectorIdiomaService {

    @Inject
    private IdiomaRepository repository;

    @Override
    public Idioma getIdioma(CodigoIdioma codigoIdioma) {
        return repository.find(codigoIdioma);
    }

    @Override
    public List<Idioma> getIdiomas() {
        return repository.findAll();
    }
    
}
