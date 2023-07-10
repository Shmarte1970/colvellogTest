package es.zarca.covellog.domain.model.stock.movimientos;

import es.zarca.covellog.domain.model.app.Ordre;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Torralbo <informatica@zarca.es>
 */
public interface MovimientoRepository {

    Movimiento find(int id);
    Movimiento findOrFail(int id);
    List<Movimiento> find(int first, int pageSize, Ordre sortOrder, Map<String, Object> filters);
    List<Movimiento> findAll();
    int findTotalCount();
    int findTotalCount(Map<String, Object> filters);
    
    void store(Movimiento movimiento);
    void remove(Movimiento movimiento);
    
}