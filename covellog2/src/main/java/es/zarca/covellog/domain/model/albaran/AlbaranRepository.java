package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface AlbaranRepository {

    Albaran find(Integer id);
    Albaran findOrFail(Integer id);
    List<Albaran> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Albaran> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Albaran almacen);
    void remove(Albaran almacen);
    List<Filtro> getFiltrosPosibles();
}
