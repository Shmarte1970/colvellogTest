package es.zarca.covellog.application.idiomas;

import es.zarca.covellog.domain.model.idiomas.idioma.CodigoIdioma;
import es.zarca.covellog.domain.model.idiomas.idioma.Idioma;
import java.util.List;

/**
 *
 * @author francisco
 */
public interface SelectorIdiomaService {
    Idioma getIdioma(CodigoIdioma codigoIdioma);
    List<Idioma> getIdiomas();
}


