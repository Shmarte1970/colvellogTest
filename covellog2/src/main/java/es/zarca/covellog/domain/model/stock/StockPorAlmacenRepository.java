package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface StockPorAlmacenRepository {

    StockPorAlmacen find(String id);
    StockPorAlmacen findOrFail(String id);
    List<StockPorAlmacen> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<StockPorAlmacen> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    
}
