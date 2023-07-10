package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface StockEstadoRepository {

    StockEstado find(String id);
    StockEstado findOrFail(String id);
    List<StockEstado> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<StockEstado> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(StockEstado estado);
    void remove(StockEstado estado);
    
}
