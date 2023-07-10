package es.zarca.covellog.domain.model.idiomas.idioma;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface IdiomaRepository {

    Idioma find(CodigoIdioma id);
    List<Idioma> findAll();
    List<Idioma> findAll(int start, int size);
    int findAllTotalCount();
    List<Idioma> findAll(Map<String, Object> filters);
    List<Idioma> findAll(int start, int size, Map<String, Object> filters);
    int findAllTotalCount(Map<String, Object> filters);
    void store(Idioma idioma);
    void remove(Idioma idioma);
   
}
