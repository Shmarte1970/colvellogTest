package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface AlbaranRecogidaRepository {

    AlbaranRecogida find(Integer id);
    AlbaranRecogida findOrFail(Integer id);
    List<AlbaranRecogida> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<AlbaranRecogida> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(AlbaranRecogida almacen);
    void remove(AlbaranRecogida almacen);
    List<Filtro> getFiltrosPosibles();

}