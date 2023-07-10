package es.zarca.covellog.domain.model.stock;

import es.zarca.covellog.domain.model.app.Ordre;
import es.zarca.covellog.interfaces.facade.stock.dto.IdCantidadDto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface ContactoRepository {

    Stock find(int id);
    Stock findOrFail(int id);
    List<Stock> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Stock> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    void store(Stock stock);
    void remove(Stock stock);
    List<IdCantidadDto> findLotes(String filtro);
    
}
