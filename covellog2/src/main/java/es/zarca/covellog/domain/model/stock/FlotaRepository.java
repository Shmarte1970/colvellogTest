package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface FlotaRepository {

    Flota find(String id);
    Flota findOrFail(String id);
    List<Flota> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Flota> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Flota flota);
    void remove(Flota flota);
    
}
