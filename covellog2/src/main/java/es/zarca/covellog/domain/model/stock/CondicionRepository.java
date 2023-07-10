package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface CondicionRepository {

    CondicionEntity find(String id);
    CondicionEntity findOrFail(String id);
    List<CondicionEntity> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<CondicionEntity> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(CondicionEntity condicion);
    void remove(CondicionEntity condicion);

}
