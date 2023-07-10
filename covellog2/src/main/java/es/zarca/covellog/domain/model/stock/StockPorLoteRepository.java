package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface StockPorLoteRepository {

    StockPorLote find(String id);
    StockPorLote findOrFail(String id);
    List<StockPorLote> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<StockPorLote> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    
}