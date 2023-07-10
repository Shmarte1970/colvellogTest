package es.zarca.covellog.domain.model.albaran;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.web.app.model.Filtro;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface AlbaranEntregaRepository {

    AlbaranEntrega find(Integer id);
    AlbaranEntrega findOrFail(Integer id);
    List<AlbaranEntrega> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<AlbaranEntrega> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(AlbaranEntrega almacen);
    void remove(AlbaranEntrega almacen);
    List<Filtro> getFiltrosPosibles();

}